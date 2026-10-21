<template>
    <section class="page">
        <div class="container">
            <div class="page-header">
                <span class="badge">DONACIONES</span>

                <h1>Registrar Nueva Donacion</h1>

                <p>
                    Registra alimentos disponibles para ser entregados a organizaciones beneficiarias.
                </p>
            </div>

            <form
                class="donacion-form"
                @submit.prevent="publicarDonacion">
                <div class="card">
                    <h3>Informacion del Producto</h3>

                    <div class="grid grid-2">
                        <div class="form-group">
                            <label>Nombre del Producto *</label>

                            <input
                                v-model="form.titulo"
                                type="text"
                                placeholder="Ej. Frutas Variadas"
                                required>
                        </div>

                        <div class="form-group">
                            <label>Categoria *</label>

                            <select
                                v-model="form.categoria"
                                required>
                                <option value="">Seleccionar categoria</option>
                                <option>Frutas</option>
                                <option>Verduras</option>
                                <option>Panaderia</option>
                                <option>Lacteos</option>
                                <option>Conservas</option>
                            </select>
                        </div>
                    </div>

                    <div class="grid grid-3">
                        <div class="form-group">
                            <label>Cantidad *</label>

                            <input
                                v-model.number="form.cantidad"
                                type="number"
                                min="1"
                                required>
                        </div>

                        <div class="form-group">
                            <label>Unidad *</label>

                            <select
                                v-model="form.unidad"
                                required>
                                <option value="">Seleccionar unidad</option>
                                <option>Kg</option>
                                <option>Unidades</option>
                                <option>Cajas</option>
                                <option>Bolsas</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Fecha de Vencimiento *</label>

                            <input
                                v-model="form.fechaVencimiento"
                                type="date"
                                required>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <h3>Informacion de Entrega</h3>

                    <div class="form-group">
                        <label>Modalidad de Entrega *</label>

                        <select
                            v-model="form.modalidadEntrega"
                            required>
                            <option value="">Seleccionar modalidad</option>
                            <option value="ENTREGA_COORDINADA">Entrega Coordinada</option>
                            <option value="RETIRO">Retiro en Punto de Recojo</option>
                            <option value="AMBAS">Ambas Opciones</option>
                        </select>
                    </div>

                    <div
                        v-if="form.modalidadEntrega === 'RETIRO' || form.modalidadEntrega === 'AMBAS'"
                        class="form-group">
                        <label>Direccion de Recojo *</label>

                        <input
                            v-model="form.direccionRecojo"
                            type="text"
                            placeholder="Direccion del punto de recojo">
                    </div>

                    <div class="form-group">
                        <label>Horario Disponible</label>

                        <input
                            v-model="form.horarioDisponible"
                            type="text"
                            placeholder="Ej. Lunes a Viernes 8:00 AM - 5:00 PM">
                    </div>
                </div>

                <div class="card">
                    <h3>Informacion Adicional</h3>

                    <div class="form-group">
                        <label>Descripcion</label>

                        <textarea
                            v-model="form.descripcion"
                            placeholder="Describe el estado y caracteristicas del producto">
                        </textarea>
                    </div>

                    <div class="form-group">
                        <label>Observaciones</label>

                        <textarea
                            v-model="form.observaciones"
                            placeholder="Indicaciones adicionales para el beneficiario">
                        </textarea>
                    </div>
                </div>

                <p
                    v-if="error"
                    class="form-message error">
                    {{ error }}
                </p>

                <p
                    v-if="mensaje"
                    class="form-message success">
                    {{ mensaje }}
                </p>

                <button
                    type="submit"
                    class="btn-submit"
                    :disabled="cargando">
                    {{ cargando ? 'Publicando...' : 'Publicar Donacion' }}
                </button>
            </form>
        </div>
    </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '@/services/api'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

const cargando = ref(false)
const error = ref('')
const mensaje = ref('')

const form = reactive({
    titulo: '',
    categoria: '',
    cantidad: 1,
    unidad: '',
    fechaVencimiento: '',
    modalidadEntrega: '',
    direccionRecojo: '',
    horarioDisponible: '',
    descripcion: '',
    observaciones: ''
})

const publicarDonacion = async () => {
    if (!authStore.usuario?.id) {
        error.value = 'Debes iniciar sesion como donador para publicar una donacion.'
        return
    }

    cargando.value = true
    error.value = ''
    mensaje.value = ''

    try {
        await api.crearDonacion({
            ...form,
            donadorId: authStore.usuario.id
        })

        mensaje.value = 'Donacion publicada correctamente.'

        setTimeout(() => {
            router.push('/donador/donaciones')
        }, 700)
    }
    catch (exception) {
        error.value = exception.message
    }
    finally {
        cargando.value = false
    }
}
</script>

<style scoped>
.page {
    padding: 40px 0;
}

.page-header {
    text-align: center;
    max-width: 800px;
    margin: 0 auto 50px;
}

.page-header p {
    margin-top: 12px;
    color: var(--text-medium);
}

.donacion-form {
    max-width: 1000px;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    gap: 30px;
}

.card {
    background: white;
    border-radius: 24px;
    padding: 32px;
    border: 1px solid rgba(0,0,0,.05);
    box-shadow: var(--shadow-md);
}

.card h3 {
    margin-bottom: 24px;
    position: relative;
    padding-bottom: 10px;
}

.card h3::after {
    content: '';
    position: absolute;
    left: 0;
    bottom: 0;
    width: 60px;
    height: 4px;
    border-radius: 999px;
    background: var(--primary-color);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
}

label {
    font-weight: 600;
}

textarea {
    min-height: 120px;
}

.form-message {
    padding: 16px;
    border-radius: 12px;
    font-weight: 600;
}

.form-message.error {
    background: #FEE2E2;
    color: #991B1B;
}

.form-message.success {
    background: #DCFCE7;
    color: #166534;
}

.btn-submit {
    width: 100%;
    height: 64px;
    border: none;
    border-radius: 18px;
    background: var(--primary-color);
    color: white;
    font-size: 18px;
    font-weight: 700;
    cursor: pointer;
    transition: .3s;
}

.btn-submit:hover {
    background: var(--primary-hover);
    transform: translateY(-2px);
}

.btn-submit:disabled {
    opacity: .7;
    cursor: not-allowed;
    transform: none;
}

@media(max-width:768px){
    .donacion-form{
        gap: 24px;
    }
}
</style>
