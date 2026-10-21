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

                <div>

                    <span
                        class="estado"
                        :class="estado.toLowerCase().replaceAll(' ','-')">

                        {{ estado }}

                    </span>

                </div>

                <div>

                    <strong>

                        Fecha Solicitud

                    </strong>

                    <p>

                        {{ fechaSolicitud }}

                    </p>

                </div>

            </div>

            <!-- INFORMACION SOLICITUD -->

            <div class="section">

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

                            Cantidad Aprobada

                        </strong>

                        <p>

                            {{ cantidadAprobada }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- DONADOR -->

            <div class="section">

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

                            Correo

                        </strong>

                        <p>

                            {{ correo }}

                        </p>

                    </div>

                </div>

            </div>

            <!-- RECOJO -->

            <div
                v-if="estado !== 'Pendiente' && estado !== 'Rechazada'"
                class="section">

                <h3>

                    🚚 Información de Recojo

                </h3>

                <div class="info-grid">

                    <div class="info-item">

                        <strong>

                            Modalidad

                        </strong>

                        <p>

                            {{ modalidad }}

                        </p>

                    </div>

                    <div class="info-item">

                        <strong>

                            Fecha de Recojo

                        </strong>

                        <p>

                            {{ fechaRecojo }}

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

            <!-- OBSERVACIONES -->

            <div
                v-if="observaciones"
                class="section">

                <h3>

                    📝 Observaciones

                </h3>

                <div class="observaciones">

                    {{ observaciones }}

                </div>

            </div>

            <!-- ACCIONES -->

            <div class="modal-actions">

                <button
                    v-if="estado === 'Pendiente'"
                    class="btn btn-secondary">

                    Cancelar Solicitud

                </button>

                <button
                    v-if="estado === 'Aprobada' || estado === 'Lista para Recojo'"
                    class="btn btn-primary">

                    Ir a Recojos

                </button>

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

    fechaSolicitud: String,

    producto: String,

    categoria: String,

    cantidadSolicitada: String,

    cantidadAprobada: String,

    donador: String,

    responsable: String,

    telefono: String,

    correo: String,

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

    backdrop-filter: blur(5px);

    display: flex;

    justify-content: center;

    align-items: center;

    padding: 20px;

    z-index: 999;
}

.modal {

    width: 100%;

    max-width: 950px;

    background: white;

    border-radius: 28px;

    padding: 32px;

    max-height: 90vh;

    overflow-y: auto;

    box-shadow:
        0 20px 40px rgba(0,0,0,.15);
}

/* HEADER */

.modal-header {

    display: flex;

    justify-content: space-between;

    align-items: center;

    padding-bottom: 20px;

    border-bottom: 1px solid #E5E7EB;

    margin-bottom: 24px;
}

.modal-header h2 {

    font-size: 42px;

    margin-top: 8px;
}

.codigo {

    display: inline-block;

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

    color: var(--text-medium);
}

/* ESTADO */

.estado-card {

    display: flex;

    justify-content: space-between;

    align-items: center;

    background: #EEF5F0;

    border: 1px solid #DDE8E0;

    border-radius: 18px;

    padding: 20px;

    margin-bottom: 24px;
}

.estado {

    display: inline-block;

    padding: 8px 16px;

    border-radius: 999px;

    font-size: 12px;

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

.lista-para-recojo {

    background: #DBEAFE;

    color: #1E40AF;
}

.rechazada {

    background: #FEE2E2;

    color: #991B1B;
}

/* SECCIONES */

.section {

    background: #F8FAFC;

    border: 1px solid #E5E7EB;

    border-radius: 18px;

    padding: 24px;

    margin-bottom: 20px;
}

.section h3 {

    margin-bottom: 20px;

    font-size: 20px;
}

/* GRID */

.info-grid {

    display: grid;

    grid-template-columns: repeat(2,1fr);

    gap: 18px;
}

.info-item {

    background: white;

    border: 1px solid #ECEFF3;

    border-radius: 14px;

    padding: 16px;
}

.info-item strong {

    display: block;

    font-size: 13px;

    color: var(--text-medium);

    margin-bottom: 8px;
}

.info-item p {

    margin: 0;

    font-size: 16px;

    font-weight: 600;

    color: var(--text-dark);
}

/* OBSERVACIONES */

.observaciones {

    background: #FFF7ED;

    border-left: 5px solid #F59E0B;

    padding: 18px;

    border-radius: 12px;

    line-height: 1.8;
}

/* BOTONES */

.modal-actions {

    display: flex;

    gap: 12px;

    margin-top: 30px;
}

.modal-actions .btn {

    flex: 1;
}

@media(max-width:768px){

    .modal {

        padding: 20px;
    }

    .modal-header h2 {

        font-size: 30px;
    }

    .estado-card {

        flex-direction: column;

        gap: 12px;

        align-items: flex-start;
    }

    .info-grid {

        grid-template-columns: 1fr;
    }

    .modal-actions {

        flex-direction: column;
    }

}
</style>



