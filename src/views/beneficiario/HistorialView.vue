<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <h1>Historial</h1>
                <p>Consulta todas las solicitudes finalizadas.</p>
            </div>

            <div class="stats">
                <div class="card stat-card">
                    <h3>{{ historialFiltrado.length }}</h3>
                    <span>Total</span>
                </div>

                <div class="card stat-card">
                    <h3>{{ contarEstado('ENTREGADA') }}</h3>
                    <span>Entregadas</span>
                </div>

                <div class="card stat-card">
                    <h3>{{ contarEstado('RECHAZADA') }}</h3>
                    <span>Rechazadas</span>
                </div>

                <div class="card stat-card">
                    <h3>{{ contarEstado('CANCELADA') }}</h3>
                    <span>Canceladas</span>
                </div>
            </div>

            <div class="filters">
                <input
                    v-model="busqueda"
                    type="text"
                    placeholder="Buscar solicitud...">

                <select v-model="estadoFiltro">
                    <option value="">Todos los estados</option>
                    <option value="ENTREGADA">Entregada</option>
                    <option value="RECHAZADA">Rechazada</option>
                    <option value="CANCELADA">Cancelada</option>
                </select>
            </div>

            <p
                v-if="cargando"
                class="status-message">
                Cargando historial...
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
                            <th>Donador</th>
                            <th>Cantidad</th>
                            <th>Fecha Cierre</th>
                            <th>Estado</th>
                            <th>Accion</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr
                            v-for="registro in historialFiltrado"
                            :key="registro.id">
                            <td>SOL-{{ registro.id }}</td>
                            <td>{{ registro.donacionTitulo }}</td>
                            <td>{{ registro.donadorOrganizacion || registro.donadorNombre }}</td>
                            <td>{{ registro.cantidadSolicitada }} {{ registro.donacionUnidad }}</td>
                            <td>{{ formatearFecha(registro.creadoEn) }}</td>
                            <td>
                                <span
                                    class="badge"
                                    :class="formatearEstado(registro.estado).toLowerCase()">
                                    {{ formatearEstado(registro.estado) }}
                                </span>
                            </td>
                            <td>
                                <button
                                    class="btn-detalle"
                                    @click="abrirDetalle(registro)">
                                    Ver
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <DetalleHistorialModal
            :mostrar="mostrarModal"
            :codigo="historialSeleccionado ? `SOL-${historialSeleccionado.id}` : ''"
            :estado="formatearEstado(historialSeleccionado?.estado)"
            :fechaCierre="formatearFecha(historialSeleccionado?.creadoEn)"
            :producto="historialSeleccionado?.donacionTitulo"
            :categoria="historialSeleccionado?.donacionCategoria"
            :cantidadSolicitada="historialSeleccionado ? `${historialSeleccionado.cantidadSolicitada} ${historialSeleccionado.donacionUnidad}` : ''"
            :cantidadRecibida="historialSeleccionado?.estado === 'ENTREGADA' ? `${historialSeleccionado.cantidadSolicitada} ${historialSeleccionado.donacionUnidad}` : '0'"
            :donador="historialSeleccionado?.donadorOrganizacion || historialSeleccionado?.donadorNombre"
            :responsable="historialSeleccionado?.donadorNombre"
            telefono="-"
            :modalidad="formatearModalidad(historialSeleccionado?.modalidadEntrega)"
            :direccion="historialSeleccionado?.direccionRecojo"
            :horario="historialSeleccionado?.horarioDisponible"
            :fechaRecojo="formatearFecha(historialSeleccionado?.fechaPropuestaRecojo)"
            :observaciones="historialSeleccionado?.motivo"
            @cerrar="cerrarModal" />
    </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import DetalleHistorialModal from '@/components/beneficiario/DetalleHistorialModal.vue'

const authStore = useAuthStore()

const historial = ref([])
const mostrarModal = ref(false)
const historialSeleccionado = ref(null)
const estadoFiltro = ref('')
const busqueda = ref('')
const cargando = ref(false)
const error = ref('')

const estadosCerrados = ['RECHAZADA', 'ENTREGADA', 'CANCELADA']

const historialFiltrado = computed(() => {
    return historial.value.filter((registro) => {
        const esCerrado = estadosCerrados.includes(registro.estado)
        const coincideEstado = !estadoFiltro.value || registro.estado === estadoFiltro.value
        const texto = `${registro.donacionTitulo} ${registro.donadorOrganizacion || ''}`.toLowerCase()
        const coincideBusqueda = texto.includes(busqueda.value.toLowerCase())

        return esCerrado && coincideEstado && coincideBusqueda
    })
})

const cargarHistorial = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como beneficiario.'
        return
    }

    cargando.value = true
    error.value = ''

    try {
        historial.value = await api.listarSolicitudes(`?beneficiarioId=${authStore.usuario.id}`)
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}

const contarEstado = (estado) =>
    historial.value.filter((registro) => registro.estado === estado).length

const abrirDetalle = (registro) => {
    historialSeleccionado.value = registro
    mostrarModal.value = true
}

const cerrarModal = () => {
    mostrarModal.value = false
    historialSeleccionado.value = null
}

const formatearEstado = (estado) => {
    const estados = {
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

onMounted(cargarHistorial)
</script>

<style scoped>
.page {
    padding: 40px 0;
}

.page-header {
    margin-bottom: 30px;
}

.page-header p {
    color: var(--text-medium);
}

.stats {
    display: grid;
    grid-template-columns: repeat(auto-fit,minmax(220px,1fr));
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    text-align: center;
    padding: 24px;
}

.stat-card h3 {
    color: var(--primary-color);
    font-size: 32px;
    margin-bottom: 8px;
}

.stat-card span {
    color: var(--text-medium);
}

.filters {
    display: flex;
    gap: 16px;
    margin-bottom: 30px;
}

.filters input {
    flex: 1;
}

.filters select {
    width: 250px;
}

.table-card {
    padding: 0;
    overflow: hidden;
}

table {
    width: 100%;
    border-collapse: collapse;
}

thead {
    background: var(--background-light);
}

th {
    font-weight: 700;
    color: var(--text-dark);
}

th,
td {
    padding: 16px;
    text-align: left;
    border-bottom: 1px solid var(--border-light);
}

.badge {
    display: inline-flex;
    justify-content: center;
    align-items: center;
    min-width: 110px;
    padding: 8px 12px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

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
    padding: 8px 16px;
    border-radius: 10px;
    cursor: pointer;
    transition: .3s;
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

@media(max-width:768px){
    .filters {
        flex-direction: column;
    }

    .filters select {
        width: 100%;
    }
}
</style>
