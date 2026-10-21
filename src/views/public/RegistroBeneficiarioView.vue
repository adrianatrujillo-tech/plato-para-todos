<template>
    <section class="section">
        <div class="container">
            <div class="form-header">
                <span class="badge">BENEFICIARIO</span>

                <h1>Registro de Beneficiario</h1>

                <p>
                    Completa la informacion de tu organizacion para solicitar apoyo alimentario.
                </p>
            </div>

            <form
                class="registro-form"
                @submit.prevent="registrar">
                <div class="card">
                    <h3>Informacion de la Organizacion</h3>

                    <div class="grid grid-2">
                        <div class="form-group">
                            <label>Tipo de Organizacion</label>

                            <select v-model="form.tipoOrganizacion">
                                <option value="">Seleccionar</option>
                                <option>Comedor Popular</option>
                                <option>ONG</option>
                                <option>Fundacion</option>
                                <option>Asociacion</option>
                                <option>Albergue</option>
                                <option>Institucion Religiosa</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Nombre Organizacion *</label>

                            <input
                                v-model="form.organizacion"
                                type="text"
                                required>
                        </div>
                    </div>

                    <div class="grid grid-2">
                        <div class="form-group">
                            <label>Cantidad de Beneficiarios</label>

                            <input
                                v-model.number="form.cantidadBeneficiarios"
                                type="number">
                        </div>

                        <div class="form-group">
                            <label>Distrito</label>

                            <input
                                v-model="form.distrito"
                                type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Direccion</label>

                        <input
                            v-model="form.direccion"
                            type="text">
                    </div>
                </div>

                <div class="card">
                    <h3>Responsable</h3>

                    <div class="grid grid-2">
                        <input
                            v-model="form.nombre"
                            type="text"
                            placeholder="Nombre Completo"
                            required>

                        <input
                            v-model="form.cargo"
                            type="text"
                            placeholder="Cargo">
                    </div>

                    <div class="grid grid-2">
                        <input
                            v-model="form.telefono"
                            type="text"
                            placeholder="Telefono">

                        <input
                            v-model="form.correo"
                            type="email"
                            placeholder="Correo"
                            required>
                    </div>
                </div>

                <div class="card">
                    <h3>Informacion Social</h3>

                    <div class="grid grid-2">
                        <div class="form-group">
                            <label>Poblacion Atendida</label>

                            <select v-model="form.poblacionAtendida">
                                <option value="">Seleccionar</option>
                                <option>Ninos</option>
                                <option>Adultos Mayores</option>
                                <option>Familias Vulnerables</option>
                                <option>Personas con Discapacidad</option>
                                <option>Comunidad General</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Horario de Atencion</label>

                            <input
                                v-model="form.horarioAtencion"
                                type="text">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Descripcion de la Organizacion</label>

                        <textarea v-model="form.descripcion"></textarea>
                    </div>
                </div>

                <div class="card">
                    <h3>Acceso al Sistema</h3>

                    <div class="grid grid-2">
                        <input
                            v-model="form.password"
                            type="password"
                            placeholder="Contrasena"
                            required>

                        <input
                            v-model="confirmarPassword"
                            type="password"
                            placeholder="Confirmar Contrasena"
                            required>
                    </div>
                </div>

                <div class="card">
                    <h3>Confirmacion</h3>

                    <div class="checkbox-group">
                        <label class="checkbox-item">
                            <input
                                v-model="aceptaTerminos"
                                type="checkbox"
                                required>

                            <span>Acepto los Terminos y Condiciones</span>
                        </label>

                        <label class="checkbox-item">
                            <input
                                v-model="aceptaPrivacidad"
                                type="checkbox"
                                required>

                            <span>Acepto la Politica de Privacidad</span>
                        </label>
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
                    {{ cargando ? 'Creando cuenta...' : 'Crear Cuenta' }}
                </button>
            </form>
        </div>
    </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '@/services/api'

const router = useRouter()

const cargando = ref(false)
const error = ref('')
const mensaje = ref('')
const confirmarPassword = ref('')
const aceptaTerminos = ref(false)
const aceptaPrivacidad = ref(false)

const form = reactive({
    tipoOrganizacion: '',
    organizacion: '',
    cantidadBeneficiarios: null,
    distrito: '',
    direccion: '',
    nombre: '',
    cargo: '',
    telefono: '',
    correo: '',
    poblacionAtendida: '',
    horarioAtencion: '',
    descripcion: '',
    password: ''
})

const registrar = async () => {
    error.value = ''
    mensaje.value = ''

    if (form.password !== confirmarPassword.value) {
        error.value = 'Las contrasenas no coinciden.'
        return
    }

    cargando.value = true

    try {
        await api.registrar({
            nombre: form.nombre,
            correo: form.correo,
            password: form.password,
            rol: 'BENEFICIARIO',
            telefono: form.telefono,
            organizacion: form.organizacion,
            direccion: form.direccion
        })

        mensaje.value = 'Cuenta creada correctamente. Ahora inicia sesion.'

        setTimeout(() => {
            router.push('/login')
        }, 900)
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
.form-header {
    text-align: center;
    max-width: 800px;
    margin: 0 auto 60px;
}

.form-header .badge {
    background: rgba(46,125,90,.1);
    color: var(--primary-color);
}

.form-header h1 {
    margin: 16px 0;
    font-size: clamp(48px, 6vw, 72px);
    line-height: 1.1;
}

.form-header p {
    color: var(--text-medium);
}

.registro-form {
    max-width: 1000px;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    gap: 32px;
}

.card {
    background: white;
    border-radius: 24px;
    padding: 32px;
    border: 1px solid rgba(0,0,0,.04);
    box-shadow: 0 10px 30px rgba(0,0,0,.06);
    transition: .3s;
}

.card:hover {
    transform: translateY(-3px);
    box-shadow: 0 15px 40px rgba(0,0,0,.08);
}

.card h3 {
    position: relative;
    padding-bottom: 12px;
    margin-bottom: 24px;
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
    font-size: 14px;
}

input,
select {
    height: 54px;
    background: #fafafa;
    border-radius: 14px;
}

textarea {
    min-height: 140px;
}

.checkbox-group {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.checkbox-item {
    display: flex;
    align-items: center;
    gap: 12px;
    cursor: pointer;
}

.checkbox-item input {
    width: 18px;
    height: 18px;
    accent-color: var(--primary-color);
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
    box-shadow: var(--shadow-md);
}

.btn-submit:hover {
    background: var(--primary-hover);
    transform: translateY(-2px);
    box-shadow: var(--shadow-lg);
}

.btn-submit:disabled {
    opacity: .7;
    cursor: not-allowed;
    transform: none;
}

@media(max-width:768px){
    .form-header h1 {
        font-size: 42px;
    }

    .registro-form {
        gap: 24px;
    }
}
</style>
