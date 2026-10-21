<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <h1>Historial de Donaciones</h1>
                <p>Consulta todas las solicitudes cerradas.</p>
            </div>

            <div class="grid grid-3 kpi-section">
                <div class="card kpi-card">
                    <h3>{{ contarEstado('ENTREGADA') }}</h3>
                    <p>Entregadas</p>
                </div>

                <div class="card kpi-card">
                    <h3>{{ contarEstado('CANCELADA') }}</h3>
                    <p>Canceladas</p>
                </div>

                <div class="card kpi-card">
                    <h3>{{ contarEstado('RECHAZADA') }}</h3>
                    <p>Rechazadas</p>
                </div>
            </div>

            <div class="filters">
                <input
                    v-model="busqueda"
                    type="text"
                    placeholder="Buscar donacion...">

                <select v-model="estadoFiltro">
                    <option value="">Todos</option>
                    <option value="ENTREGADA">Entregada</option>
                    <option value="CANCELADA">Cancelada</option>
                    <option value="RECHAZADA">Rechazada</option>
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
                class="card table-container">
                <table>
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Donacion</th>
                            <th>Beneficiario</th>
                            <th>Cantidad</th>
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
                            <td>{{ registro.beneficiarioNombre }}</td>
                            <td>{{ registro.cantidadSolicitada }} {{ registro.donacionUnidad }}</td>
                            <td>
                                <span
                                    class="badge"
                                    :class="formatearEstado(registro.estado).toLowerCase()">
                                    {{ formatearEstado(registro.estado) }}
                                </span>
                            </td>
                            <td>
                                <button
                                    class="btn-table"
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
            :donacion="historialSeleccionado?.donacionTitulo"
            :categoria="historialSeleccionado?.donacionCategoria"
            :cantidad="historialSeleccionado ? `${historialSeleccionado.cantidadSolicitada} ${historialSeleccionado.donacionUnidad}` : ''"
            :beneficiario="historialSeleccionado?.beneficiarioNombre"
            :responsable="historialSeleccionado?.beneficiarioNombre"
            :resultado="resultadoHistorial"
            :eventos="eventosHistorial"
            @cerrar="cerrarModal" />
    </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'
import DetalleHistorialModal from '@/components/donador/DetalleHistorialModal.vue'

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
        const texto = `${registro.donacionTitulo} ${registro.beneficiarioNombre}`.toLowerCase()
        const coincideBusqueda = texto.includes(busqueda.value.toLowerCase())

        return esCerrado && coincideEstado && coincideBusqueda
    })
})

const resultadoHistorial = computed(() => {
    if (!historialSeleccionado.value) return ''

    return `Solicitud ${formatearEstado(historialSeleccionado.value.estado).toLowerCase()}.`
})

const eventosHistorial = computed(() => {
    if (!historialSeleccionado.value) return []

    return [
        {
            fecha: formatearFecha(historialSeleccionado.value.creadoEn),
            descripcion: 'Solicitud registrada.'
        },
        {
            fecha: formatearFecha(historialSeleccionado.value.creadoEn),
            descripcion: `Estado actual: ${formatearEstado(historialSeleccionado.value.estado)}.`
        }
    ]
})

const cargarHistorial = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como donador.'
        return
    }

    cargando.value = true
    error.value = ''

    try {
        historial.value = await api.listarSolicitudes(`?donadorId=${authStore.usuario.id}`)
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

.kpi-section {
    margin-bottom: 30px;
}

.kpi-card {
    text-align: center;
    padding: 30px 20px;
}

.kpi-card h3 {
    color: var(--primary-color);
    font-size: 40px;
    margin-bottom: 8px;
}

.kpi-card p {
    color: var(--text-medium);
}

.filters {
    display: flex;
    gap: 20px;
    margin-bottom: 24px;
}

.filters input {
    flex: 1;
}

.filters select {
    width: 240px;
}

.table-container {
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
    padding: 18px;
    text-align: left;
    font-size: 14px;
    font-weight: 700;
    color: var(--text-dark);
}

td {
    padding: 18px;
    border-top: 1px solid var(--border-light);
}

.badge {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    min-width: 100px;
    padding: 8px 14px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
}

.entregada {
    background: #DCFCE7;
    color: #166534;
}

.cancelada,
.rechazada {
    background: #FEE2E2;
    color: #991B1B;
}

.btn-table {
    border: none;
    background: var(--primary-color);
    color: white;
    padding: 10px 16px;
    border-radius: 10px;
    cursor: pointer;
    font-size: 14px;
    font-weight: 600;
    transition: .3s;
}

.btn-table:hover {
    background: var(--primary-hover);
    transform: translateY(-2px);
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

    .table-container {
        overflow-x: auto;
    }

    table {
        min-width: 850px;
    }
}
</style>
