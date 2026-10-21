<template>

    <div
        v-if="mostrar"
        class="modal-overlay">

        <div class="modal">

            <!-- HEADER -->

            <div class="modal-header">

                <div>

                    <span class="codigo">

                        {{ codigo }}

                    </span>

                    <h2>

                        Detalle del Historial

                    </h2>

                </div>

                <button
                    class="close-btn"
                    @click="$emit('cerrar')">

                    ✕

                </button>

            </div>

            <!-- ESTADO -->

            <div class="estado-card">

                <div>

                    <strong>

                        Estado Final

                    </strong>

                </div>

                <span
                    class="estado"
                    :class="estado.toLowerCase()">

                    {{ estado }}

                </span>

            </div>

            <!-- DONACION -->

            <div class="section">

                <h3>

                    📦 Información de la Donación

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Producto

                        </strong>

                        <p>

                            {{ donacion }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Categoría

                        </strong>

                        <p>

                            {{ categoria }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Cantidad Entregada

                        </strong>

                        <p>

                            {{ cantidad }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- BENEFICIARIO -->

            <div class="section">

                <h3>

                    🏠 Información del Beneficiario

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Organización

                        </strong>

                        <p>

                            {{ beneficiario }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Responsable

                        </strong>

                        <p>

                            {{ responsable }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- CRONOLOGIA -->

            <div class="section">

                <h3>

                    📋 Cronología

                </h3>

                <div class="timeline">

                    <div
                        v-for="evento in eventos"
                        :key="evento.fecha"
                        class="timeline-item">

                        <div class="timeline-date">

                            {{ evento.fecha }}

                        </div>

                        <div class="timeline-content">

                            {{ evento.descripcion }}

                        </div>

                    </div>

                </div>

            </div>

            <!-- RESULTADO -->

            <div class="section">

                <h3>

                    ✅ Resultado Final

                </h3>

                <div class="resultado-box">

                    {{ resultado }}

                </div>

            </div>

            <!-- FOOTER -->

            <div class="modal-actions">

                <button
                    class="btn btn-secondary"
                    @click="$emit('cerrar')">

                    Cerrar

                </button>

            </div>

        </div>

    </div>

</template>

<script setup>

defineProps({

    mostrar: Boolean,

    codigo: String,

    estado: String,

    donacion: String,

    categoria: String,

    cantidad: String,

    beneficiario: String,

    responsable: String,

    resultado: String,

    eventos: {

        type: Array,

        default: () => []

    }

})

defineEmits([

    'cerrar'

])

</script>

<style scoped>

.modal-overlay {

    position: fixed;

    inset: 0;

    background: rgba(15,23,42,.65);

    backdrop-filter: blur(6px);

    display: flex;

    justify-content: center;

    align-items: center;

    padding: 24px;

    z-index: 999;

}

.modal {

    width: 100%;

    max-width: 780px;

    background: white;

    border-radius: 24px;

    padding: 32px;

    max-height: 90vh;

    overflow-y: auto;

    box-shadow:
        0 25px 50px rgba(0,0,0,.20);

}

/* HEADER */

.modal-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-start;

    margin-bottom: 28px;

}

.modal-header h2 {

    margin-top: 8px;

    font-size: 28px;

}

.codigo {

    display: inline-flex;

    align-items: center;

    background: #ECFDF5;

    color: #166534;

    padding: 8px 14px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;

}

.close-btn {

    width: 42px;

    height: 42px;

    border: none;

    border-radius: 12px;

    background: #F3F4F6;

    cursor: pointer;

    font-size: 20px;

    transition: .3s;

}

.close-btn:hover {

    background: #E5E7EB;

}

/* ESTADO */

.estado-card {

    display: flex;

    justify-content: space-between;

    align-items: center;

    background: linear-gradient(
        135deg,
        #F8FAFC,
        #EEF5F0
    );

    border: 1px solid #E5E7EB;

    border-radius: 18px;

    padding: 22px;

    margin-bottom: 28px;

}

.estado-card strong {

    font-size: 16px;

}

.estado {

    padding: 10px 18px;

    border-radius: 999px;

    font-size: 13px;

    font-weight: 700;

}

.entregada {

    background: #DCFCE7;

    color: #166534;

}

.cancelada {

    background: #FEE2E2;

    color: #991B1B;

}

.expirada {

    background: #FEF3C7;

    color: #92400E;

}

/* SECCIONES */

.section {

    margin-bottom: 28px;

}

.section h3 {

    margin-bottom: 16px;

    font-size: 20px;

}

/* GRID */

.info-grid {

    display: grid;

    grid-template-columns: repeat(2,1fr);

    gap: 16px;

}

.info-item {

    background: #FAFAFA;

    border: 1px solid #E5E7EB;

    border-radius: 14px;

    padding: 18px;

    transition: .2s;

}

.info-item:hover {

    border-color: #D1D5DB;

}

.info-item strong {

    display: block;

    margin-bottom: 8px;

    font-size: 13px;

    color: var(--text-medium);

}

.info-item p {

    font-size: 15px;

    font-weight: 500;

}

/* TIMELINE */

.timeline {

    display: flex;

    flex-direction: column;

    gap: 20px;

}

.timeline-item {

    position: relative;

    padding-left: 30px;

}

.timeline-item::before {

    content: '';

    position: absolute;

    left: 0;

    top: 5px;

    width: 14px;

    height: 14px;

    border-radius: 50%;

    background: var(--primary-color);

}

.timeline-item::after {

    content: '';

    position: absolute;

    left: 6px;

    top: 20px;

    width: 2px;

    height: calc(100% + 10px);

    background: #D1D5DB;

}

.timeline-item:last-child::after {

    display: none;

}

.timeline-date {

    font-size: 13px;

    font-weight: 700;

    color: var(--primary-color);

    margin-bottom: 4px;

}

.timeline-content {

    color: var(--text-medium);

}

/* RESULTADO */

.resultado-box {

    background: #ECFDF5;

    border: 1px solid #BBF7D0;

    color: #166534;

    padding: 20px;

    border-radius: 14px;

    line-height: 1.7;

}

/* FOOTER */

.modal-actions {

    margin-top: 32px;

    display: flex;

    justify-content: flex-end;

}

.modal-actions .btn {

    min-width: 140px;

}

/* RESPONSIVE */

@media(max-width:768px){

    .modal {

        padding: 22px;

    }

    .info-grid {

        grid-template-columns: 1fr;

    }

    .estado-card {

        flex-direction: column;

        align-items: flex-start;

        gap: 14px;

    }

}
</style>
