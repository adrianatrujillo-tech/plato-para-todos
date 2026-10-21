<template>
    <article class="donacion-card">
        <div class="card-header">
            <div class="card-title">
                <h3 class="titulo">
                    {{ titulo }}
                </h3>

                <span class="categoria">
                    {{ categoria }}
                </span>
            </div>

            <span
                class="estado"
                :class="estadoClase">
                {{ estado }}
            </span>
        </div>

        <div class="info-list">
            <div class="info-item">
                <span class="icon">Cant.</span>
                <span>{{ cantidad }} {{ unidad }}</span>
            </div>

            <div class="info-item">
                <span class="icon">Vence</span>
                <span>{{ fechaVencimiento }}</span>
            </div>

            <div class="info-item">
                <span class="icon">Modo</span>
                <span>{{ modalidadEntrega }}</span>
            </div>
        </div>

        <p
            v-if="descripcion"
            class="descripcion">
            {{ descripcion }}
        </p>

        <div class="card-footer">
            <button
                type="button"
                class="btn btn-secondary"
                @click="$emit('ver')">
                Ver
            </button>

            <button
                type="button"
                class="btn btn-primary"
                :disabled="!permiteEditar"
                @click="$emit('editar')">
                Editar
            </button>

            <button
                type="button"
                class="btn btn-danger"
                :disabled="!permiteEliminar"
                @click="$emit('eliminar')">
                Eliminar
            </button>
        </div>
    </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    titulo: {
        type: String,
        required: true
    },
    categoria: {
        type: String,
        required: true
    },
    cantidad: {
        type: [String, Number],
        required: true
    },
    unidad: {
        type: String,
        required: true
    },
    fechaVencimiento: {
        type: String,
        required: true
    },
    modalidadEntrega: {
        type: String,
        required: true
    },
    descripcion: {
        type: String,
        default: ''
    },
    estado: {
        type: String,
        default: 'Disponible'
    },
    permiteEditar: {
        type: Boolean,
        default: true
    },
    permiteEliminar: {
        type: Boolean,
        default: true
    }
})

defineEmits(['ver', 'editar', 'eliminar'])

const estadoClase = computed(() => {
    return props.estado
        .toLowerCase()
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
})
</script>

<style scoped>
.donacion-card {
    background: white;
    border-radius: 16px;
    padding: 24px;
    box-shadow: var(--shadow-md);
    transition: .3s;
    display: flex;
    flex-direction: column;
    gap: 20px;
    min-width: 0;
}

.donacion-card:hover {
    transform: translateY(-4px);
    box-shadow: var(--shadow-lg);
}

.card-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 16px;
}

.card-title {
    min-width: 0;
}

.titulo {
    margin-bottom: 10px;
    overflow-wrap: anywhere;
}

.categoria {
    display: inline-block;
    background: var(--background-light);
    color: var(--primary-color);
    padding: 6px 12px;
    border-radius: 999px;
    font-size: 13px;
    font-weight: 600;
}

.estado {
    padding: 8px 14px;
    border-radius: 999px;
    font-size: 12px;
    font-weight: 700;
    white-space: nowrap;
}

.disponible {
    background: #DCFCE7;
    color: #166534;
}

.pendiente,
.reservada {
    background: #FEF3C7;
    color: #92400E;
}

.asignada {
    background: #E0E7FF;
    color: #3730A3;
}

.finalizada,
.entregada {
    background: #DBEAFE;
    color: #1E40AF;
}

.cancelada,
.vencida {
    background: #FEE2E2;
    color: #991B1B;
}

.info-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.info-item {
    display: flex;
    align-items: center;
    gap: 12px;
    color: var(--text-medium);
}

.icon {
    width: 48px;
    flex: 0 0 48px;
    color: var(--primary-color);
    font-size: 12px;
    font-weight: 700;
}

.descripcion {
    color: var(--text-medium);
    line-height: 1.6;
    overflow-wrap: anywhere;
}

.card-footer {
    display: flex;
    gap: 12px;
    margin-top: auto;
}

.card-footer .btn {
    flex: 1;
}

.btn-danger {
    background: var(--danger-color);
    color: white;
    border: none;
}

.btn-danger:hover {
    opacity: .9;
}

@media (max-width: 640px) {
    .donacion-card {
        padding: 18px;
        border-radius: 14px;
    }

    .card-header,
    .card-footer {
        flex-direction: column;
        align-items: stretch;
    }

    .estado {
        width: max-content;
    }
}
</style>
