<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <h1>Solicitudes Recibidas</h1>
                <p>Gestiona las solicitudes realizadas por los beneficiarios.</p>
            </div>

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

            <p
                v-else-if="solicitudesFiltradas.length === 0"
                class="status-message">
                No tienes solicitudes activas.
            </p>

            <div
                v-else
                class="grid grid-2">
                <SolicitudCard
                    v-for="solicitud in solicitudesFiltradas"
                    :key="solicitud.id"
                    :codigo="`SOL-${solicitud.id}`"
                    :beneficiario="solicitud.beneficiarioNombre"
                    :donacion="solicitud.donacionTitulo"
                    :cantidad="`${solicitud.cantidadSolicitada} ${solicitud.donacionUnidad}`"
                    :fecha="formatearFecha(solicitud.creadoEn)"
                    :estado="formatearEstado(solicitud.estado)"
                    @aprobar="cambiarEstado(solicitud.id, 'APROBADA')"
                    @rechazar="cambiarEstado(solicitud.id, 'RECHAZADA')"
                    @entregar="cambiarEstado(solicitud.id, 'ENTREGADA')"
                    @cancelar="cambiarEstado(solicitud.id, 'CANCELADA')"
                    @verDetalle="abrirDetalle(solicitud)" />
            </div>
        </div>

        <DetalleSolicitudModal
            :mostrar="mostrarModal"
            :codigo="solicitudSeleccionada ? `SOL-${solicitudSeleccionada.id}` : ''"
            :estado="formatearEstado(solicitudSeleccionada?.estado)"
            :beneficiario="solicitudSeleccionada?.beneficiarioNombre"
            :responsable="solicitudSeleccionada?.beneficiarioNombre"
            telefono="-"
            correo="-"
            :producto="solicitudSeleccionada?.donacionTitulo"
            :categoria="solicitudSeleccionada?.donacionCategoria"
            :disponible="solicitudSeleccionada ? `${solicitudSeleccionada.donacionCantidad} ${solicitudSeleccionada.donacionUnidad}` : ''"
            :solicitada="solicitudSeleccionada ? `${solicitudSeleccionada.cantidadSolicitada} ${solicitudSeleccionada.donacionUnidad}` : ''"
            :motivo="solicitudSeleccionada?.motivo"
            :fechaRecojo="formatearFecha(solicitudSeleccionada?.fechaPropuestaRecojo)"
            :horario="solicitudSeleccionada?.horarioDisponible"
            @cerrar="cerrarModal" />
    </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import SolicitudCard from '@/components/donador/SolicitudCard.vue'
import DetalleSolicitudModal from '@/components/donador/DetalleSolicitudModal.vue'

const authStore = useAuthStore()

const solicitudes = ref([])
const mostrarModal = ref(false)
const solicitudSeleccionada = ref(null)
const estadoFiltro = ref('')
const busqueda = ref('')
const cargando = ref(false)
const error = ref('')

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
        const texto = `${solicitud.beneficiarioNombre} ${solicitud.donacionTitulo}`.toLowerCase()
        const coincideBusqueda = texto.includes(busqueda.value.toLowerCase())

        return coincideEstado && coincideBusqueda
    })
})

const cargarSolicitudes = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como donador.'
        return
    }

    cargando.value = true
    error.value = ''

    try {
        solicitudes.value = await api.listarSolicitudes(`?donadorId=${authStore.usuario.id}`)
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}

const cambiarEstado = async (id, estado) => {
    try {
        await api.cambiarEstadoSolicitud(id, estado)
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

const formatearFecha = (fecha) => {
    if (!fecha) return '-'

    return new Date(fecha).toLocaleDateString('es-PE')
}

onMounted(cargarSolicitudes)
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
    margin-top: 8px;
}

.stats {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    margin-bottom: 30px;
}

.stat-card {
    text-align: center;
    padding: 30px 20px;
    transition: .3s;
}

.stat-card h3 {
    font-size: 40px;
    color: var(--primary-color);
    margin-bottom: 8px;
}

.stat-card span {
    color: var(--text-medium);
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
    width: 250px;
}

.grid.grid-2 {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 24px;
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

@media(max-width:992px){
    .stats {
        grid-template-columns: repeat(2,1fr);
    }

    .grid.grid-2 {
        grid-template-columns: 1fr;
    }
}

@media(max-width:768px){
    .page {
        padding: 24px 0;
    }

    .stats {
        grid-template-columns: 1fr;
    }

    .filters {
        flex-direction: column;
    }

    .filters select {
        width: 100%;
    }
}
</style>
