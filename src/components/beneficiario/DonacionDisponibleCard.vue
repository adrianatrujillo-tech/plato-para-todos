<template>

    <div class="donacion-card">

        <!-- CABECERA -->

        <div class="card-header">

            <div>

                <h3>

                    {{ titulo }}

                </h3>

                <span class="categoria">

                    {{ categoria }}

                </span>

            </div>

            <span class="estado">

                Disponible

            </span>

        </div>

        <!-- PRODUCTO -->

        <div class="info-section">

            <div class="info-item">

                📦

                <span>

                    <strong>Cantidad:</strong>
                    {{ cantidad }} {{ unidad }}

                </span>

            </div>

            <div class="info-item">

                📅

                <span>

                    <strong>Vencimiento:</strong>
                    {{ fechaVencimiento }}

                </span>

            </div>

        </div>

        <!-- ENTREGA -->

        <div class="info-section">

            <div class="info-item">

                🚚

                <span>

                    <strong>Modalidad:</strong>
                    {{ modalidadEntrega }}

                </span>

            </div>

            <div class="info-item">

                📍

                <span>

                    <strong>Dirección:</strong>
                    {{ direccion }}

                </span>

            </div>

            <div class="info-item">

                🕒

                <span>

                    <strong>Horario:</strong>
                    {{ horario }}

                </span>

            </div>

        </div>

        <!-- DONADOR -->

        <div class="info-section">

            <div class="info-item">

                🏢

                <span>

                    <strong>Donador:</strong>
                    {{ donador }}

                </span>

            </div>

        </div>

        <!-- DESCRIPCIÓN -->

        <div class="descripcion">

            <strong>

                📝 Descripción

            </strong>

            <p>

                {{ descripcion }}

            </p>

        </div>

        <!-- OBSERVACIONES -->

        <div
            v-if="observaciones"
            class="observaciones">

            <strong>

                ℹ️ Observaciones

            </strong>

            <p>

                {{ observaciones }}

            </p>

        </div>

        <!-- ACCIÓN -->

        <button
            class="btn btn-primary btn-solicitar"
            @click="$emit('solicitar')">

            Solicitar Donación

        </button>

    </div>

</template>

<script setup>

defineProps({

    codigo: String,

    titulo: String,

    categoria: String,

    cantidad: [String, Number],

    unidad: String,

    fechaVencimiento: String,

    modalidadEntrega: String,

    direccion: String,

    horario: String,

    donador: String,

    descripcion: String,

    observaciones: String

})

defineEmits([

    'solicitar'

])

</script>

<style scoped>

.donacion-card {

    background: white;

    border-radius: 20px;

    padding: 24px;

    box-shadow: var(--shadow-md);

    display: flex;

    flex-direction: column;

    gap: 18px;

    transition: .3s;
}

.donacion-card:hover {

    transform: translateY(-4px);

    box-shadow: var(--shadow-lg);
}

/* HEADER */

.card-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-start;

    gap: 20px;
}

.card-header h3 {

    margin-bottom: 8px;
}

.estado {

    background: #DCFCE7;

    color: #166534;

    padding: 6px 14px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;
}

.categoria {

    display: inline-block;

    background: rgba(46,125,90,.1);

    color: var(--primary-color);

    padding: 6px 12px;

    border-radius: 999px;

    font-size: 13px;

    font-weight: 600;
}

/* INFO */

.info-section {

    display: flex;

    flex-direction: column;

    gap: 10px;
}

.info-item {

    display: flex;

    align-items: flex-start;

    gap: 10px;

    color: var(--text-medium);

    line-height: 1.5;
}

/* DESCRIPCIÓN */

.descripcion {

    background: var(--background-light);

    padding: 16px;

    border-radius: 12px;
}

.descripcion p {

    margin-top: 8px;

    color: var(--text-medium);
}

/* OBSERVACIONES */

.observaciones {

    background: #FFF7ED;

    padding: 16px;

    border-radius: 12px;

    border-left: 4px solid var(--secondary-color);
}

.observaciones p {

    margin-top: 8px;

    color: var(--text-medium);
}

/* BOTÓN */

.btn-solicitar {

    width: 100%;

    margin-top: auto;
}

/* RESPONSIVE */

@media(max-width:768px){

    .card-header {

        flex-direction: column;
    }

}

</style>
