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

                        Detalle de Solicitud

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

                <span
                    class="estado"
                    :class="estado.toLowerCase()">

                    {{ estado }}

                </span>

            </div>

            <!-- BENEFICIARIO -->

            <div class="detail-section">

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

                    <div class="info-item">

                        <strong>

                            Teléfono

                        </strong>

                        <p>

                            {{ telefono }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Correo

                        </strong>

                        <p>

                            {{ correo }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- DONACION -->

            <div class="detail-section">

                <h3>

                    📦 Información de la Donación

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Producto

                        </strong>

                        <p>

                            {{ producto }}

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

                            Disponible

                        </strong>

                        <p>

                            {{ disponible }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Cantidad Solicitada

                        </strong>

                        <p>

                            {{ solicitada }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- MOTIVO -->

            <div class="detail-section">

                <h3>

                    📝 Motivo de la Solicitud

                </h3>

                <div class="observaciones">

                    {{ motivo }}

                </div>

            </div>

            <!-- RECOJO -->

            <div class="detail-section">

                <h3>

                    🚚 Información de Recojo

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Fecha Propuesta

                        </strong>

                        <p>

                            {{ fechaRecojo }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Horario

                        </strong>

                        <p>

                            {{ horario }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- ACCIONES -->

            <div
                v-if="estado === 'Pendiente'"
                class="modal-actions">

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

            </div>

            <div
                v-else
                class="modal-actions">

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

    beneficiario: String,

    responsable: String,

    telefono: String,

    correo: String,

    producto: String,

    categoria: String,

    disponible: String,

    solicitada: String,

    motivo: String,

    fechaRecojo: String,

    horario: String

})

defineEmits([

    'cerrar',

    'aprobar',

    'rechazar'

])

</script>

<style scoped>

.modal-overlay {

    position: fixed;

    inset: 0;

    background: rgba(0,0,0,.55);

    backdrop-filter: blur(4px);

    display: flex;

    justify-content: center;

    align-items: center;

    padding: 20px;

    z-index: 999;
}

.modal {

    width: 100%;

    max-width: 900px;

    background: white;

    border-radius: 24px;

    padding: 30px;

    max-height: 90vh;

    overflow-y: auto;

    box-shadow: var(--shadow-xl);
}

.modal-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-start;

    margin-bottom: 24px;
}

.codigo {

    display: inline-block;

    background: #ECFDF5;

    color: #166534;

    padding: 6px 12px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;

    margin-bottom: 10px;
}

.close-btn {

    border: none;

    background: transparent;

    font-size: 28px;

    cursor: pointer;
}

.estado-card {

    margin-bottom: 24px;
}

.estado {

    display: inline-flex;

    padding: 10px 16px;

    border-radius: 999px;

    font-size: 13px;

    font-weight: 700;
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

.detail-section {

    margin-bottom: 24px;
}

.detail-section h3 {

    margin-bottom: 16px;
}

.info-grid {

    display: grid;

    grid-template-columns: repeat(2,1fr);

    gap: 16px;
}

.info-item {

    background: #F8FAFC;

    border: 1px solid #E5E7EB;

    border-radius: 14px;

    padding: 16px;
}

.info-item strong {

    display: block;

    margin-bottom: 6px;
}

.info-item p {

    color: var(--text-medium);
}

.observaciones {

    background: #FFF7ED;

    border: 1px solid #FED7AA;

    border-radius: 14px;

    padding: 16px;

    line-height: 1.7;
}

.modal-actions {

    display: flex;

    gap: 12px;

    margin-top: 30px;
}

.btn-danger {

    background: #DC2626;

    color: white;

    border: none;
}

@media(max-width:768px){

    .modal {

        padding: 20px;
    }

    .info-grid {

        grid-template-columns: 1fr;
    }

    .modal-actions {

        flex-direction: column;
    }

    .modal-actions .btn {

        width: 100%;
    }

}

</style>
