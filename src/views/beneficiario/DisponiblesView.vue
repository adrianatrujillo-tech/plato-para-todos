<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <h1>Donaciones Disponibles</h1>

                <p>
                    Explora las donaciones publicadas por los donadores y encuentra alimentos disponibles para tu organizacion.
                </p>
            </div>

            <div class="filters">
                <input
                    v-model="busqueda"
                    type="text"
                    placeholder="Buscar producto...">

                <select v-model="categoriaFiltro">
                    <option value="">Todas las categorias</option>
                    <option>Frutas</option>
                    <option>Verduras</option>
                    <option>Panaderia</option>
                    <option>Lacteos</option>
                    <option>Conservas</option>
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
                No hay donaciones disponibles.
            </p>

            <div
                v-else
                class="grid grid-2">
                <DonacionDisponibleCard
                    v-for="donacion in donacionesFiltradas"
                    :key="donacion.id"
                    :codigo="`DON-${donacion.id}`"
                    :titulo="donacion.titulo"
                    :categoria="donacion.categoria"
                    :cantidad="donacion.cantidad"
                    :unidad="donacion.unidad"
                    :fechaVencimiento="formatearFecha(donacion.fechaVencimiento)"
                    :modalidadEntrega="formatearModalidad(donacion.modalidadEntrega)"
                    :direccion="donacion.direccionRecojo || 'Por coordinar'"
                    :horario="donacion.horarioDisponible || 'Por coordinar'"
                    :donador="donacion.donadorOrganizacion || donacion.donadorNombre"
                    :descripcion="donacion.descripcion"
                    :observaciones="donacion.observaciones"
                    @solicitar="abrirModal(donacion)" />
            </div>
        </div>

        <SolicitarDonacionModal
            :mostrar="mostrarModal"
            :producto="donacionSeleccionada?.titulo"
            :disponible="donacionSeleccionada ? `${donacionSeleccionada.cantidad} ${donacionSeleccionada.unidad}` : ''"
            :maximo="donacionSeleccionada?.cantidad"
            @cerrar="cerrarModal"
            @enviar="crearSolicitud" />
    </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import DonacionDisponibleCard from '@/components/beneficiario/DonacionDisponibleCard.vue'
import SolicitarDonacionModal from '@/components/beneficiario/SolicitarDonacionModal.vue'

const authStore = useAuthStore()

const donaciones = ref([])
const mostrarModal = ref(false)
const donacionSeleccionada = ref(null)
const cargando = ref(false)
const error = ref('')
const busqueda = ref('')
const categoriaFiltro = ref('')

const donacionesFiltradas = computed(() => {
    return donaciones.value.filter((donacion) => {
        const coincideBusqueda = donacion.titulo
            .toLowerCase()
            .includes(busqueda.value.toLowerCase())

        const coincideCategoria = !categoriaFiltro.value
            || donacion.categoria === categoriaFiltro.value

        return coincideBusqueda && coincideCategoria
    })
})

const cargarDonaciones = async () => {
    cargando.value = true
    error.value = ''

    try {
        donaciones.value = await api.listarDonaciones('?disponibles=true')
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}

const abrirModal = (donacion) => {
    donacionSeleccionada.value = donacion
    mostrarModal.value = true
}

const cerrarModal = () => {
    mostrarModal.value = false
    donacionSeleccionada.value = null
}

const crearSolicitud = async (payload) => {
    if (!authStore.usuario?.id || !donacionSeleccionada.value?.id) {
        error.value = 'Debes iniciar sesion como beneficiario para solicitar una donacion.'
        return
    }

    try {
        await api.crearSolicitud({
            donacionId: donacionSeleccionada.value.id,
            beneficiarioId: authStore.usuario.id,
            cantidadSolicitada: payload.cantidadSolicitada,
            fechaPropuestaRecojo: payload.fechaPropuestaRecojo,
            motivo: payload.motivo
        })

        cerrarModal()
        await cargarDonaciones()
    }
    catch (exception) {
        error.value = exception.message
    }
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

onMounted(cargarDonaciones)
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
    .grid {
        grid-template-columns: 1fr;
    }
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
