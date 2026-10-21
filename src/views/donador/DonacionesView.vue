<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <div>
                    <h1>Mis Donaciones</h1>
                    <p>Gestiona todas tus donaciones.</p>
                </div>

                <RouterLink
                    to="/donador/nueva-donacion"
                    class="btn btn-primary">
                    Nueva Donacion
                </RouterLink>
            </div>

            <div class="filters">
                <input
                    v-model="busqueda"
                    type="text"
                    placeholder="Buscar donacion">

                <select v-model="estadoFiltro">
                    <option value="">Activas</option>
                    <option value="DISPONIBLE">Disponible</option>
                    <option value="RESERVADA">Reservada</option>
                    <option value="ENTREGADA">Entregada</option>
                    <option value="CANCELADA">Cancelada</option>
                </select>
            </div>

            <p
                v-if="cargando"
                class="status-message">
                Cargando donaciones...
            </p>

            <p
                v-else-if="error"
                class="status-message error">
                {{ error }}
            </p>

            <p
                v-else-if="donacionesFiltradas.length === 0"
                class="status-message">
                No tienes donaciones registradas.
            </p>

            <div
                v-else
                class="grid grid-3">
                <DonacionCard
                    v-for="donacion in donacionesFiltradas"
                    :key="donacion.id"
                    :titulo="donacion.titulo"
                    :categoria="donacion.categoria"
                    :cantidad="donacion.cantidad"
                    :unidad="donacion.unidad"
                    :fechaVencimiento="formatearFecha(donacion.fechaVencimiento)"
                    :modalidadEntrega="formatearModalidad(donacion.modalidadEntrega)"
                    :descripcion="donacion.descripcion"
                    :estado="formatearEstado(donacion.estado)"
                    :permite-editar="puedeGestionarse(donacion)"
                    :permite-eliminar="puedeGestionarse(donacion)"
                    @ver="abrirDetalle(donacion)"
                    @editar="abrirEdicion(donacion)"
                    @eliminar="eliminarDonacion(donacion)" />
            </div>
        </div>

        <div
            v-if="donacionDetalle"
            class="modal-backdrop"
            @click.self="cerrarModales">
            <article class="modal-panel detail-panel">
                <header class="modal-header">
                    <div>
                        <span class="modal-kicker">Detalle</span>
                        <h2>{{ donacionDetalle.titulo }}</h2>
                    </div>

                    <button
                        type="button"
                        class="icon-button"
                        @click="cerrarModales">
                        X
                    </button>
                </header>

                <dl class="detail-grid">
                    <div>
                        <dt>Categoria</dt>
                        <dd>{{ donacionDetalle.categoria }}</dd>
                    </div>
                    <div>
                        <dt>Cantidad</dt>
                        <dd>{{ donacionDetalle.cantidad }} {{ donacionDetalle.unidad }}</dd>
                    </div>
                    <div>
                        <dt>Vencimiento</dt>
                        <dd>{{ formatearFecha(donacionDetalle.fechaVencimiento) }}</dd>
                    </div>
                    <div>
                        <dt>Modalidad</dt>
                        <dd>{{ formatearModalidad(donacionDetalle.modalidadEntrega) }}</dd>
                    </div>
                    <div>
                        <dt>Direccion</dt>
                        <dd>{{ donacionDetalle.direccionRecojo || 'Por coordinar' }}</dd>
                    </div>
                    <div>
                        <dt>Horario</dt>
                        <dd>{{ donacionDetalle.horarioDisponible || 'Por coordinar' }}</dd>
                    </div>
                    <div>
                        <dt>Estado</dt>
                        <dd>{{ formatearEstado(donacionDetalle.estado) }}</dd>
                    </div>
                </dl>

                <p
                    v-if="donacionDetalle.descripcion"
                    class="detail-text">
                    {{ donacionDetalle.descripcion }}
                </p>

                <p
                    v-if="donacionDetalle.observaciones"
                    class="detail-text muted">
                    {{ donacionDetalle.observaciones }}
                </p>
            </article>
        </div>

        <div
            v-if="donacionEditando"
            class="modal-backdrop"
            @click.self="cerrarModales">
            <form
                class="modal-panel form-panel"
                @submit.prevent="guardarEdicion">
                <header class="modal-header">
                    <div>
                        <span class="modal-kicker">Editar</span>
                        <h2>Actualizar donacion</h2>
                    </div>

                    <button
                        type="button"
                        class="icon-button"
                        @click="cerrarModales">
                        X
                    </button>
                </header>

                <div class="form-grid">
                    <label>
                        Titulo
                        <input
                            v-model.trim="formEdicion.titulo"
                            required
                            type="text">
                    </label>

                    <label>
                        Categoria
                        <input
                            v-model.trim="formEdicion.categoria"
                            required
                            type="text">
                    </label>

                    <label>
                        Cantidad
                        <input
                            v-model.number="formEdicion.cantidad"
                            min="1"
                            required
                            type="number">
                    </label>

                    <label>
                        Unidad
                        <input
                            v-model.trim="formEdicion.unidad"
                            required
                            type="text">
                    </label>

                    <label>
                        Fecha de vencimiento
                        <input
                            v-model="formEdicion.fechaVencimiento"
                            required
                            type="date">
                    </label>

                    <label>
                        Modalidad
                        <select
                            v-model="formEdicion.modalidadEntrega"
                            required>
                            <option value="RETIRO">Retiro</option>
                            <option value="ENTREGA_COORDINADA">Entrega coordinada</option>
                            <option value="AMBAS">Ambas opciones</option>
                        </select>
                    </label>

                    <label>
                        Direccion de recojo
                        <input
                            v-model.trim="formEdicion.direccionRecojo"
                            type="text">
                    </label>

                    <label>
                        Horario disponible
                        <input
                            v-model.trim="formEdicion.horarioDisponible"
                            type="text">
                    </label>
                </div>

                <label>
                    Descripcion
                    <textarea
                        v-model.trim="formEdicion.descripcion"
                        rows="3" />
                </label>

                <label>
                    Observaciones
                    <textarea
                        v-model.trim="formEdicion.observaciones"
                        rows="3" />
                </label>

                <p
                    v-if="mensajeModal"
                    class="modal-message error">
                    {{ mensajeModal }}
                </p>

                <div class="modal-actions">
                    <button
                        type="button"
                        class="btn btn-secondary"
                        @click="cerrarModales">
                        Cancelar
                    </button>

                    <button
                        type="submit"
                        class="btn btn-primary"
                        :disabled="guardando">
                        {{ guardando ? 'Guardando...' : 'Guardar cambios' }}
                    </button>
                </div>
            </form>
        </div>
    </section>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import DonacionCard from '@/components/donador/DonacionCard.vue'

const authStore = useAuthStore()

const donaciones = ref([])
const cargando = ref(false)
const guardando = ref(false)
const error = ref('')
const mensajeModal = ref('')
const busqueda = ref('')
const estadoFiltro = ref('')
const donacionDetalle = ref(null)
const donacionEditando = ref(null)

const formEdicion = reactive({
    titulo: '',
    categoria: '',
    cantidad: 1,
    unidad: '',
    fechaVencimiento: '',
    modalidadEntrega: 'RETIRO',
    direccionRecojo: '',
    horarioDisponible: '',
    descripcion: '',
    observaciones: ''
})

const donacionesFiltradas = computed(() => {
    return donaciones.value.filter((donacion) => {
        const texto = `${donacion.titulo} ${donacion.categoria} ${donacion.descripcion || ''}`
            .toLowerCase()
        const coincideBusqueda = texto.includes(busqueda.value.toLowerCase())
        const coincideEstado = estadoFiltro.value
            ? donacion.estado === estadoFiltro.value
            : donacion.estado !== 'CANCELADA'

        return coincideBusqueda && coincideEstado
    })
})

const cargarDonaciones = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como donador.'
        return
    }

    cargando.value = true
    error.value = ''

    try {
        donaciones.value = await api.listarDonaciones(`?donadorId=${authStore.usuario.id}`)
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}

const abrirDetalle = (donacion) => {
    donacionDetalle.value = donacion
    donacionEditando.value = null
}

const abrirEdicion = (donacion) => {
    donacionEditando.value = donacion
    donacionDetalle.value = null
    mensajeModal.value = ''

    Object.assign(formEdicion, {
        titulo: donacion.titulo || '',
        categoria: donacion.categoria || '',
        cantidad: donacion.cantidad || 1,
        unidad: donacion.unidad || '',
        fechaVencimiento: donacion.fechaVencimiento || '',
        modalidadEntrega: donacion.modalidadEntrega || 'RETIRO',
        direccionRecojo: donacion.direccionRecojo || '',
        horarioDisponible: donacion.horarioDisponible || '',
        descripcion: donacion.descripcion || '',
        observaciones: donacion.observaciones || ''
    })
}

const cerrarModales = () => {
    donacionDetalle.value = null
    donacionEditando.value = null
    mensajeModal.value = ''
}

const guardarEdicion = async () => {
    if (!donacionEditando.value?.id) return

    guardando.value = true
    mensajeModal.value = ''

    try {
        await api.editarDonacion(donacionEditando.value.id, { ...formEdicion })
        cerrarModales()
        await cargarDonaciones()
    }
    catch (exception) {
        mensajeModal.value = exception.message
    }
    finally {
        guardando.value = false
    }
}

const eliminarDonacion = async (donacion) => {
    if (!window.confirm(`Deseas eliminar "${donacion.titulo}"?`)) return

    error.value = ''

    try {
        await api.eliminarDonacion(donacion.id)
        await cargarDonaciones()
    }
    catch (exception) {
        error.value = exception.message
    }
}

const puedeGestionarse = (donacion) => {
    return !['ENTREGADA', 'CANCELADA'].includes(donacion.estado)
}

const formatearFecha = (fecha) => {
    if (!fecha) return ''

    return new Date(`${fecha}T00:00:00`).toLocaleDateString('es-PE')
}

const formatearModalidad = (modalidad) => {
    const modalidades = {
        RETIRO: 'Retiro en Punto de Recojo',
        ENTREGA_COORDINADA: 'Entrega Coordinada',
        AMBAS: 'Ambas Opciones'
    }

    return modalidades[modalidad] || modalidad
}

const formatearEstado = (estado) => {
    const estados = {
        DISPONIBLE: 'Disponible',
        RESERVADA: 'Pendiente',
        ENTREGADA: 'Finalizada',
        VENCIDA: 'Vencida',
        CANCELADA: 'Cancelada'
    }

    return estados[estado] || estado
}

onMounted(cargarDonaciones)
</script>

<style scoped>
.page {
    padding: 40px 0;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
    margin-bottom: 30px;
}

.filters {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
}

.filters input {
    flex: 1;
}

.filters select {
    width: 220px;
}

.status-message {
    padding: 24px;
    background: white;
    border-radius: 16px;
    box-shadow: var(--shadow-sm);
    color: var(--text-medium);
}

.status-message.error,
.modal-message.error {
    color: var(--danger-color);
}

.modal-backdrop {
    position: fixed;
    inset: 0;
    z-index: 50;
    background: rgba(15, 23, 42, .55);
    display: grid;
    place-items: center;
    padding: 24px;
}

.modal-panel {
    width: min(760px, 100%);
    max-height: calc(100vh - 48px);
    overflow-y: auto;
    background: white;
    border-radius: 18px;
    box-shadow: var(--shadow-lg);
    padding: 28px;
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 16px;
    margin-bottom: 22px;
}

.modal-header h2 {
    margin: 4px 0 0;
}

.modal-kicker {
    color: var(--primary-color);
    font-size: 13px;
    font-weight: 700;
    text-transform: uppercase;
}

.icon-button {
    width: 40px;
    height: 40px;
    border-radius: 999px;
    border: none;
    background: var(--background-light);
    color: var(--text-dark);
    font-weight: 800;
    cursor: pointer;
}

.detail-grid,
.form-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 16px;
}

.detail-grid {
    margin: 0;
}

.detail-grid div {
    background: var(--background-light);
    border-radius: 12px;
    padding: 14px;
}

.detail-grid dt {
    color: var(--text-medium);
    font-size: 13px;
    font-weight: 700;
}

.detail-grid dd {
    margin: 6px 0 0;
    overflow-wrap: anywhere;
}

.detail-text {
    margin-top: 16px;
    color: var(--text-dark);
    line-height: 1.6;
}

.detail-text.muted {
    color: var(--text-medium);
}

.form-panel label {
    display: flex;
    flex-direction: column;
    gap: 8px;
    color: var(--text-dark);
    font-weight: 700;
}

.form-panel textarea {
    resize: vertical;
}

.form-panel > label,
.modal-message {
    margin-top: 16px;
}

.modal-actions {
    display: flex;
    justify-content: flex-end;
    gap: 14px;
    margin-top: 22px;
}

@media (max-width: 768px) {
    .page-header,
    .filters,
    .modal-actions {
        flex-direction: column;
        align-items: stretch;
    }

    .filters select {
        width: 100%;
    }

    .detail-grid,
    .form-grid {
        grid-template-columns: 1fr;
    }

    .modal-panel {
        padding: 22px;
    }
}

@media (max-width: 480px) {
    .page {
        padding: 24px 0;
    }

    .modal-backdrop {
        padding: 12px;
    }

    .modal-panel {
        border-radius: 14px;
        max-height: calc(100vh - 24px);
        padding: 18px;
    }
}
</style>
