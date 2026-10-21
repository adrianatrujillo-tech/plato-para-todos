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

                <span
                    class="estado"
                    :class="estado.toLowerCase()">

                    {{ estado }}

                </span>

                <div>

                    <strong>

                        Fecha de cierre

                    </strong>

                    <p>

                        {{ fechaCierre }}

                    </p>

                </div>

            </div>

            <!-- SOLICITUD -->

            <div class="detail-section">

                <h3>

                    📦 Información de la Solicitud

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

                            Cantidad Solicitada

                        </strong>

                        <p>

                            {{ cantidadSolicitada }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Cantidad Recibida

                        </strong>

                        <p>

                            {{ cantidadRecibida }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- DONADOR -->

            <div class="detail-section">

                <h3>

                    🏢 Información del Donador

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Organización

                        </strong>

                        <p>

                            {{ donador }}

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

                            Modalidad

                        </strong>

                        <p>

                            {{ modalidad }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- ENTREGA -->

            <div
              v-if="estado === 'Entregada'"
              class="detail-section">

                <h3>

                    🚚 Información de Entrega

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Fecha Recojo

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

                    <div class="info-item">

                        <strong>

                            Dirección

                        </strong>

                        <p>

                            {{ direccion }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- OBSERVACIONES -->

            <div class="detail-section">

                <h3>

                    📝 Observaciones Finales

                </h3>

                <div class="observaciones">

                    {{ observaciones }}

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

    fechaCierre: String,

    producto: String,

    categoria: String,

    cantidadSolicitada: String,

    cantidadRecibida: String,

    donador: String,

    responsable: String,

    telefono: String,

    modalidad: String,

    direccion: String,

    horario: String,

    fechaRecojo: String,

    observaciones: String

})

defineEmits([

    'cerrar'

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

/* MODAL */

.modal {

    width: 100%;

    max-width: 850px;

    background: white;

    border-radius: 24px;

    padding: 32px;

    max-height: 85vh;

    overflow-y: auto;

    box-shadow: var(--shadow-xl);
}

/* HEADER */

.modal-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-start;

    margin-bottom: 24px;
}

.modal-header h2 {

    margin-top: 8px;

    font-size: 36px;
}

.codigo {

    display: inline-flex;

    align-items: center;

    background: #ECFDF5;

    color: #166534;

    padding: 6px 12px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;
}

.close-btn {

    border: none;

    background: transparent;

    font-size: 28px;

    cursor: pointer;

    transition: .3s;
}

.close-btn:hover {

    transform: rotate(90deg);
}

/* ESTADO */

.estado-card {

    background: #F8FAFC;

    border: 1px solid #E5E7EB;

    border-radius: 16px;

    padding: 20px;

    margin-bottom: 24px;

    display: flex;

    justify-content: space-between;

    align-items: center;
}

.estado {

    display: inline-flex;

    align-items: center;

    justify-content: center;

    padding: 10px 18px;

    border-radius: 999px;

    font-size: 13px;

    font-weight: 700;
}

.entregada {

    background: #DCFCE7;

    color: #166534;
}

.rechazada {

    background: #FEE2E2;

    color: #991B1B;
}

.cancelada {

    background: #E5E7EB;

    color: #4B5563;
}

.expirada {

    background: #FEF3C7;

    color: #92400E;
}

/* SECCIONES */

.detail-section {

    margin-bottom: 28px;
}

.detail-section h3 {

    margin-bottom: 16px;

    font-size: 22px;

    color: var(--text-dark);
}

/* GRID */

.info-grid {

    display: grid;

    grid-template-columns: repeat(2,1fr);

    gap: 16px;
}

.info-item {

    background: #F8FAFC;

    border: 1px solid #E5E7EB;

    border-radius: 14px;

    padding: 18px;
}

.info-item strong {

    display: block;

    margin-bottom: 6px;

    font-size: 14px;
}

.info-item p {

    color: var(--text-medium);

    margin: 0;
}

/* OBSERVACIONES */

.observaciones {

    background: #FFF7ED;

    border: 1px solid #FED7AA;

    border-radius: 14px;

    padding: 18px;

    color: var(--text-dark);

    line-height: 1.7;
}

/* FOOTER */

.modal-actions {

    margin-top: 32px;

    display: flex;

    justify-content: flex-end;

    gap: 12px;
}

/* RESPONSIVE */

@media(max-width:768px){

    .modal {

        padding: 20px;
    }

    .modal-header {

        flex-direction: column;

        gap: 12px;
    }

    .modal-header h2 {

        font-size: 28px;
    }

    .estado-card {

        flex-direction: column;

        align-items: flex-start;

        gap: 12px;
    }

    .info-grid {

        grid-template-columns: 1fr;
    }

    .modal-actions {

        justify-content: stretch;
    }

    .modal-actions button {

        width: 100%;
    }

}

</style>
