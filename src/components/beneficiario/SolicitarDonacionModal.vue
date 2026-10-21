<template>
    <div
        v-if="mostrar"
        class="modal-overlay">
        <div class="modal">
            <div class="modal-header">
                <div>
                    <h2>Solicitar Donacion</h2>
                    <p>Completa la informacion requerida.</p>
                </div>

                <button
                    class="close-btn"
                    @click="$emit('cerrar')">
                    x
                </button>
            </div>

            <div class="producto-info">
                <div class="producto-badge">Producto Disponible</div>

                <h3>{{ producto }}</h3>

                <p>
                    Stock disponible:
                    <strong>{{ disponible }}</strong>
                </p>
            </div>

            <form @submit.prevent="enviarSolicitud">
                <div class="form-group">
                    <label>Cantidad Solicitada *</label>

                    <input
                        v-model.number="cantidadSolicitada"
                        type="number"
                        min="1"
                        :max="maximo"
                        placeholder="Ingrese cantidad"
                        required>

                    <small>Maximo disponible: {{ maximo }}</small>
                </div>

                <div class="form-group">
                    <label>Fecha Propuesta de Recojo</label>

                    <input
                        v-model="fechaPropuestaRecojo"
                        type="date">
                </div>

                <div class="form-group">
                    <label>Motivo de la Solicitud</label>

                    <textarea
                        v-model="motivo"
                        rows="4"
                        placeholder="Explique brevemente para que seran utilizados los alimentos">
                    </textarea>
                </div>

                <div class="modal-actions">
                    <button
                        type="button"
                        class="btn btn-secondary"
                        @click="$emit('cerrar')">
                        Cancelar
                    </button>

                    <button
                        type="submit"
                        class="btn btn-primary">
                        Enviar Solicitud
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
    mostrar: Boolean,
    producto: String,
    disponible: String,
    maximo: Number
})

const emit = defineEmits([
    'cerrar',
    'enviar'
])

const cantidadSolicitada = ref(1)
const fechaPropuestaRecojo = ref('')
const motivo = ref('')

watch(
    () => props.mostrar,
    (mostrar) => {
        if (mostrar) {
            cantidadSolicitada.value = 1
            fechaPropuestaRecojo.value = ''
            motivo.value = ''
        }
    }
)

const enviarSolicitud = () => {
    emit('enviar', {
        cantidadSolicitada: cantidadSolicitada.value,
        fechaPropuestaRecojo: fechaPropuestaRecojo.value || null,
        motivo: motivo.value
    })
}
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
    max-width: 550px;
    background: white;
    border-radius: 24px;
    padding: 28px;
    box-shadow: var(--shadow-xl);
    animation: aparecer .25s ease;
}

@keyframes aparecer {
    from {
        opacity: 0;
        transform: translateY(15px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 24px;
}

.modal-header h2 {
    font-size: 30px;
    margin-bottom: 4px;
}

.modal-header p {
    color: var(--text-medium);
    font-size: 14px;
}

.close-btn {
    border: none;
    background: transparent;
    font-size: 24px;
    cursor: pointer;
    color: var(--text-medium);
    transition: .3s;
}

.close-btn:hover {
    color: var(--danger-color);
}

.producto-info {
    background: linear-gradient(135deg, #EEF5F0, #F8FAF8);
    border: 1px solid #DDE8E0;
    border-radius: 16px;
    padding: 20px;
    margin-bottom: 24px;
}

.producto-badge {
    display: inline-block;
    padding: 6px 12px;
    background: rgba(46,125,90,.10);
    color: var(--primary-color);
    border-radius: 999px;
    font-size: 12px;
    font-weight: 600;
    margin-bottom: 10px;
}

.producto-info h3 {
    margin-bottom: 6px;
}

.producto-info p {
    color: var(--text-medium);
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
}

.form-group small {
    display: block;
    margin-top: 6px;
    color: var(--text-muted);
    font-size: 12px;
}

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
        font-size: 24px;
    }

    .modal-actions {
        flex-direction: column;
    }
}
</style>
