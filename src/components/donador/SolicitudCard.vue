<template>

    <div class="solicitud-card">

        <!-- HEADER -->

        <div class="card-header">

            <div>

                <div class="solicitud-id">

                    {{ codigo }}

                </div>

                <h3>

                    {{ beneficiario }}

                </h3>

                <p class="donacion">

                    Donación: {{ donacion }}

                </p>

            </div>

            <span
                class="estado"
                :class="estado.toLowerCase()">

                {{ estado }}

            </span>

        </div>

        <!-- INFORMACION -->

        <div class="info-grid">

            <div class="info-item">

                <span>
                    📦
                </span>

                <span>

                    {{ cantidad }}

                </span>

            </div>

            <div class="info-item">

                <span>
                    📅
                </span>

                <span>

                    {{ fecha }}

                </span>

            </div>

        </div>

        <!-- ACCIONES -->

        <div class="card-actions">

            <!-- SOLICITUD PENDIENTE -->

            <template v-if="estado === 'Pendiente'">

                <button
                    class="btn btn-primary"
                    @click="$emit('aprobar')">

                    Aprobar

                </button>

                <button
                    class="btn btn-danger"
                    @click="$emit('rechazar')">

                    Rechazar

                </button>

            </template>

            <!-- SOLICITUD APROBADA -->

            <template v-else-if="estado === 'Aprobada'">

                <button
                    class="btn btn-success"
                    @click="$emit('entregar')">

                    Marcar Entregada

                </button>

                <button
                    class="btn btn-warning"
                    @click="$emit('cancelar')">

                    Cancelar Entrega

                </button>

            </template>

            <!-- SOLICITUD RECHAZADA -->

            <template v-else-if="estado === 'Rechazada'">

                <span class="estado-info">

                    Solicitud rechazada

                </span>

            </template>

            <button
                class="btn btn-primary"
                @click="$emit('verDetalle')">

                Ver Detalle

            </button>

        </div>

    </div>

</template>

<script setup>

defineEmits([
    'verDetalle',
    'aprobar',
    'rechazar',
    'entregar',
    'cancelar'
])

defineProps({

    codigo: {
        type: String,
        default: 'SOL-0001'
    },

    beneficiario: {
        type: String,
        required: true
    },

    donacion: {
        type: String,
        required: true
    },

    cantidad: {
        type: String,
        required: true
    },

    fecha: {
        type: String,
        required: true
    },

    estado: {
        type: String,
        default: 'Pendiente'
    }

})

</script>

<style scoped>

.solicitud-card {

    background: white;

    border-radius: 20px;

    padding: 24px;

    box-shadow: var(--shadow-md);

    display: flex;

    flex-direction: column;

    gap: 20px;

    transition: .3s;
}

.solicitud-card:hover {

    transform: translateY(-3px);

    box-shadow: var(--shadow-lg);
}

/* HEADER */

.card-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-start;

    gap: 20px;
}

.solicitud-id {

    display: inline-block;

    background: var(--background-light);

    color: var(--primary-color);

    padding: 6px 12px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;

    margin-bottom: 10px;
}

.donacion {

    margin-top: 6px;

    color: var(--text-medium);
}

/* INFO */

.info-grid {

    display: flex;

    gap: 24px;

    flex-wrap: wrap;
}

.info-item {

    display: flex;

    align-items: center;

    gap: 8px;

    color: var(--text-medium);
}

/* ESTADOS */

.estado {

    padding: 8px 14px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;

    white-space: nowrap;
}

.pendiente {

    background: #FEF3C7;

    color: #92400E;
}

.aprobada {

    background: #DCFCE7;

    color: #166534;
}

.rechazada {

    background: #FEE2E2;

    color: #991B1B;
}

/* ACCIONES */

.card-actions {

    display: flex;

    gap: 12px;

    flex-wrap: wrap;
}

.btn-success {

    background: var(--success-color);

    color: white;

    border: none;
}

.btn-warning {

    background: var(--warning-color);

    color: white;

    border: none;
}

.btn-danger {

    background: var(--danger-color);

    color: white;

    border: none;
}

.estado-info {

    display: flex;

    align-items: center;

    color: var(--text-medium);

    font-size: 14px;
}

/* RESPONSIVE */

@media(max-width:768px){

    .card-header {

        flex-direction: column;
    }

    .card-actions {

        flex-direction: column;
    }

    .card-actions .btn {

        width: 100%;
    }

}

</style>





