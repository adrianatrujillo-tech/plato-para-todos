<template>
    <section class="page">
        <div class="stats">
            <div class="card stat-card">
                <h3>{{ solicitudesActivas.length }}</h3>
                <span>Total</span>
            </div>

            <div class="card stat-card">
                <h3>{{ contarEstado('PENDIENTE') }}</h3>
                <span>Pendientes</span>
            </div>

            <div class="card stat-card">
                <h3>{{ contarEstado('APROBADA') }}</h3>
                <span>Aprobadas</span>
            </div>

            <div class="card stat-card">
                <h3>{{ solicitudesCerradas.length }}</h3>
                <span>Cerradas</span>
            </div>
        </div>

        <div class="filters">
            <input
                v-model="busqueda"
                type="text"
                placeholder="Buscar solicitud...">

            <select v-model="estadoFiltro">
                <option value="">Todos los estados</option>
                <option value="PENDIENTE">Pendiente</option>
                <option value="APROBADA">Aprobada</option>
            </select>
        </div>

        <p
            v-if="cargando"
            class="status-message">
            Cargando solicitudes...
        </p>

        <p
            v-else-if="error"
            class="status-message error">
            {{ error }}
        </p>

        <div
            v-else
            class="card table-card">
            <table>
                <thead>
                    <tr>
                        <th>Codigo</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Donador</th>
                        <th>Fecha Solicitud</th>
                        <th>Estado</th>
                        <th>Accion</th>
                    </tr>
                </thead>

                <tbody>
                    <tr
                        v-for="solicitud in solicitudesFiltradas"
                        :key="solicitud.id">
                        <td>SOL-{{ solicitud.id }}</td>
                        <td>{{ solicitud.donacionTitulo }}</td>
                        <td>{{ solicitud.cantidadSolicitada }} {{ solicitud.donacionUnidad }}</td>
                        <td>{{ solicitud.donadorOrganizacion || solicitud.donadorNombre }}</td>
                        <td>{{ formatearFecha(solicitud.creadoEn) }}</td>
                        <td>
                            <span
                                class="badge"
                                :class="formatearEstado(solicitud.estado).toLowerCase()">
                                {{ formatearEstado(solicitud.estado) }}
                            </span>
                        </td>
                        <td class="actions">
                            <button
                                class="btn-detalle"
                                @click="abrirDetalle(solicitud)">
                                Ver
                            </button>

                            <button
                                v-if="solicitud.estado === 'PENDIENTE'"
                                class="btn-detalle"
                                @click="abrirEdicion(solicitud)">
                                Editar
                            </button>

                            <button
                                v-if="solicitud.estado === 'PENDIENTE'"
                                class="btn-detalle danger"
                                @click="eliminarSolicitud(solicitud.id)">
                                Eliminar
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>

    <DetalleSolicitudModal
        :mostrar="mostrarModal"
        :codigo="solicitudSeleccionada ? `SOL-${solicitudSeleccionada.id}` : ''"
        :estado="formatearEstado(solicitudSeleccionada?.estado)"
        :fechaSolicitud="formatearFecha(solicitudSeleccionada?.creadoEn)"
        :producto="solicitudSeleccionada?.donacionTitulo"
        :categoria="solicitudSeleccionada?.donacionCategoria"
        :cantidadSolicitada="solicitudSeleccionada ? `${solicitudSeleccionada.cantidadSolicitada} ${solicitudSeleccionada.donacionUnidad}` : ''"
        :cantidadAprobada="solicitudSeleccionada?.estado === 'APROBADA' ? `${solicitudSeleccionada.cantidadSolicitada} ${solicitudSeleccionada.donacionUnidad}` : '-'"
        :donador="solicitudSeleccionada?.donadorOrganizacion || solicitudSeleccionada?.donadorNombre"
        :responsable="solicitudSeleccionada?.donadorNombre"
        telefono="-"
        correo="-"
        :modalidad="formatearModalidad(solicitudSeleccionada?.modalidadEntrega)"
        :direccion="solicitudSeleccionada?.direccionRecojo"
        :horario="solicitudSeleccionada?.horarioDisponible"
        :fechaRecojo="formatearFecha(solicitudSeleccionada?.fechaPropuestaRecojo)"
        :observaciones="solicitudSeleccionada?.motivo"
        @cerrar="cerrarModal" />

    <div
        v-if="editando"
        class="modal-overlay">
        <form
            class="edit-modal"
            @submit.prevent="guardarEdicion">
            <h3>Editar solicitud</h3>

            <label>Cantidad</label>
            <input
                v-model.number="formEdicion.cantidadSolicitada"
                type="number"
                min="1"
                required>

            <label>Fecha propuesta</label>
            <input
                v-model="formEdicion.fechaPropuestaRecojo"
                type="date">

            <label>Motivo</label>
            <textarea v-model="formEdicion.motivo"></textarea>

            <div class="modal-actions">
                <button
                    type="button"
                    class="btn-detalle danger"
                    @click="cerrarEdicion">
                    Cancelar
                </button>

                <button
                    type="submit"
                    class="btn-detalle">
                    Guardar
                </button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import DetalleSolicitudModal from '@/components/beneficiario/DetalleSolicitudModal.vue'

const authStore = useAuthStore()

const solicitudes = ref([])
const mostrarModal = ref(false)
const solicitudSeleccionada = ref(null)
const estadoFiltro = ref('')
const busqueda = ref('')
const cargando = ref(false)
const error = ref('')
const editando = ref(false)
const solicitudEditadaId = ref(null)

const formEdicion = reactive({
    cantidadSolicitada: 1,
    fechaPropuestaRecojo: '',
    motivo: ''
})

const estadosActivos = ['PENDIENTE', 'APROBADA']

const solicitudesActivas = computed(() =>
    solicitudes.value.filter((solicitud) => estadosActivos.includes(solicitud.estado))
)

const solicitudesCerradas = computed(() =>
    solicitudes.value.filter((solicitud) => !estadosActivos.includes(solicitud.estado))
)

const solicitudesFiltradas = computed(() => {
    return solicitudesActivas.value.filter((solicitud) => {
        const coincideEstado = !estadoFiltro.value || solicitud.estado === estadoFiltro.value
        const texto = `${solicitud.donacionTitulo} ${solicitud.donadorOrganizacion || ''}`.toLowerCase()
        const coincideBusqueda = texto.includes(busqueda.value.toLowerCase())

        return coincideEstado && coincideBusqueda
    })
})

const cargarSolicitudes = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como beneficiario.'
        return
    }

    cargando.value = true
    error.value = ''

    try {
        solicitudes.value = await api.listarSolicitudes(`?beneficiarioId=${authStore.usuario.id}`)
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}

const eliminarSolicitud = async (id) => {
    const confirmar = window.confirm('Quieres eliminar esta solicitud pendiente?')

    if (!confirmar) return

    try {
        await api.eliminarSolicitud(id)
        await cargarSolicitudes()
    }
    catch (exception) {
        error.value = exception.message
    }
}

const abrirEdicion = (solicitud) => {
    solicitudEditadaId.value = solicitud.id
    formEdicion.cantidadSolicitada = solicitud.cantidadSolicitada
    formEdicion.fechaPropuestaRecojo = solicitud.fechaPropuestaRecojo || ''
    formEdicion.motivo = solicitud.motivo || ''
    editando.value = true
}

const cerrarEdicion = () => {
    editando.value = false
    solicitudEditadaId.value = null
}

const guardarEdicion = async () => {
    try {
        await api.editarSolicitud(solicitudEditadaId.value, {
            cantidadSolicitada: formEdicion.cantidadSolicitada,
            fechaPropuestaRecojo: formEdicion.fechaPropuestaRecojo || null,
            motivo: formEdicion.motivo
        })

        cerrarEdicion()
        await cargarSolicitudes()
    }
    catch (exception) {
        error.value = exception.message
    }
}

const contarEstado = (estado) =>
    solicitudes.value.filter((solicitud) => solicitud.estado === estado).length

const abrirDetalle = (solicitud) => {
    solicitudSeleccionada.value = solicitud
    mostrarModal.value = true
}

const cerrarModal = () => {
    mostrarModal.value = false
    solicitudSeleccionada.value = null
}

const formatearEstado = (estado) => {
    const estados = {
        PENDIENTE: 'Pendiente',
        APROBADA: 'Aprobada',
        RECHAZADA: 'Rechazada',
        ENTREGADA: 'Entregada',
        CANCELADA: 'Cancelada'
    }

    return estados[estado] || estado || ''
}

const formatearModalidad = (modalidad) => {
    const modalidades = {
        RETIRO: 'Retiro en Punto de Recojo',
        ENTREGA_COORDINADA: 'Entrega Coordinada',
        AMBAS: 'Ambas Opciones'
    }

    return modalidades[modalidad] || modalidad || ''
}

const formatearFecha = (fecha) => {
    if (!fecha) return '-'

    return new Date(fecha).toLocaleDateString('es-PE')
}

onMounted(cargarSolicitudes)
</script>

<style scoped>
.page {
    max-width: 1100px;
    margin: 0 auto;
    padding: 40px 0;
}

.stats {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    min-height: 130px;
    padding: 24px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    transition: .3s;
}

.stat-card h3 {
    font-size: 34px;
    color: var(--primary-color);
    margin-bottom: 8px;
}

.stat-card span {
    color: var(--text-medium);
    font-size: 14px;
}

.filters {
    display: flex;
    gap: 16px;
    margin-bottom: 30px;
    align-items: center;
}

.filters input {
    flex: 1;
    max-width: 500px;
}

.filters select {
    width: 220px;
}

.table-card {
    padding: 0;
    overflow: hidden;
    border-radius: 20px;
    background: white;
}

table {
    width: 100%;
    border-collapse: collapse;
}

thead {
    background: #F8FAFC;
}

th {
    padding: 18px 20px;
    text-align: left;
    font-size: 14px;
    font-weight: 700;
    color: var(--text-dark);
    border-bottom: 1px solid #E5E7EB;
}

td {
    padding: 18px 20px;
    border-bottom: 1px solid #F1F5F9;
    vertical-align: middle;
}

.actions {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
}

.badge {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    min-width: 130px;
    padding: 8px 14px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

.pendiente {
    background: #FEF3C7;
    color: #92400E;
}

.aprobada,
.entregada {
    background: #DCFCE7;
    color: #166534;
}

.rechazada,
.cancelada {
    background: #FEE2E2;
    color: #991B1B;
}

.btn-detalle {
    border: none;
    background: var(--primary-color);
    color: white;
    padding: 10px 18px;
    border-radius: 10px;
    font-weight: 600;
    cursor: pointer;
    transition: .3s;
}

.btn-detalle.danger {
    background: var(--danger-color);
}

.status-message {
    padding: 24px;
    background: white;
    border-radius: 16px;
    box-shadow: var(--shadow-sm);
    color: var(--text-medium);
}

.status-message.error {
    color: var(--danger-color);
}

.modal-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,.45);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    padding: 20px;
}

.edit-modal {
    width: min(520px, 100%);
    background: white;
    border-radius: 16px;
    padding: 24px;
    display: grid;
    gap: 12px;
    box-shadow: var(--shadow-xl);
}

.edit-modal textarea {
    min-height: 110px;
}

.modal-actions {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
}

@media(max-width:992px){
    .stats {
        grid-template-columns: repeat(2,1fr);
    }
}

@media(max-width:768px){
    .filters {
        flex-direction: column;
        align-items: stretch;
    }

    .filters input,
    .filters select {
        width: 100%;
        max-width: 100%;
    }
}
</style>
