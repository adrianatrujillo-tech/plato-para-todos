<template>
    <section class="login-section">
        <div class="container">
            <div class="login-wrapper">
                <div class="login-info">
                    <div class="login-brand">
                        <div class="brand-icon">🍽️</div>

                        <h1>Plato Para Todos</h1>

                        <p>
                            Conectamos alimentos con organizaciones que mas los necesitan.
                        </p>
                    </div>

                    <div class="benefits">
                        <div class="benefit-item">Publica donaciones facilmente</div>
                        <div class="benefit-item">Solicita apoyo alimentario</div>
                        <div class="benefit-item">Genera impacto social real</div>
                        <div class="benefit-item">Seguimiento transparente</div>
                    </div>
                </div>

                <div class="login-card">
                    <span class="badge">ACCESO</span>

                    <h2>Iniciar Sesion</h2>

                    <p class="login-description">
                        Ingresa tus credenciales para acceder a la plataforma.
                    </p>

                    <form @submit.prevent="iniciarSesion">
                        <div class="form-group">
                            <label>Correo Electronico</label>

                            <input
                                v-model="correo"
                                type="email"
                                placeholder="correo@ejemplo.com"
                                required>
                        </div>

                        <div class="form-group">
                            <label>Contrasena</label>

                            <input
                                v-model="password"
                                type="password"
                                placeholder="********"
                                required>
                        </div>

                        <div class="login-options">
                            <label>
                                <input type="checkbox">
                                Recordarme
                            </label>

                            <a href="#">Olvidaste tu contrasena?</a>
                        </div>

                        <button
                            type="submit"
                            class="btn-login"
                            :disabled="cargando">
                            {{ cargando ? 'Ingresando...' : 'Ingresar' }}
                        </button>
                    </form>

                    <p
                        v-if="error"
                        class="login-error">
                        {{ error }}
                    </p>

                    <div class="register-link">
                        No tienes cuenta?

                        <RouterLink to="/registro">
                            Registrarse
                        </RouterLink>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'
import { api } from '@/services/api'

const router = useRouter()
const authStore = useAuthStore()

const correo = ref('')
const password = ref('')
const cargando = ref(false)
const error = ref('')

const iniciarSesion = async () => {
    cargando.value = true
    error.value = ''

    try {
        const authResponse = await api.login({
            correo: correo.value,
            password: password.value
        })

        authStore.login(authResponse)

        if (authResponse.rol === 'DONADOR') {
            router.push('/donador')
        }
        else if (authResponse.rol === 'BENEFICIARIO') {
            router.push('/beneficiario')
        }
        else if (authResponse.rol === 'ADMIN') {
            router.push('/admin')
        }
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
.login-section {
    min-height: calc(100vh - 80px);
    display: flex;
    align-items: center;
    padding: 60px 0;
}

.login-wrapper {
    display: grid;
    grid-template-columns: 1fr 500px;
    gap: 60px;
    align-items: center;
}

.login-info {
    display: flex;
    flex-direction: column;
    gap: 40px;
}

.brand-icon {
    font-size: 80px;
    margin-bottom: 20px;
}

.login-info h1 {
    font-size: clamp(48px,6vw,72px);
    line-height: 1.1;
    margin-bottom: 16px;
}

.login-info p {
    font-size: 18px;
    color: var(--text-medium);
}

.benefits {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.benefit-item {
    background: white;
    padding: 18px;
    border-radius: var(--radius-md);
    box-shadow: var(--shadow-sm);
}

.login-card {
    background: white;
    padding: 40px;
    border-radius: 24px;
    box-shadow: var(--shadow-lg);
}

.login-card h2 {
    margin: 16px 0 10px;
}

.login-description {
    color: var(--text-medium);
    margin-bottom: 30px;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
}

.login-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    font-size: 14px;
}

.login-options a {
    color: var(--primary-color);
}

.btn-login {
    width: 100%;
    height: 60px;
    border: none;
    border-radius: 16px;
    background: var(--primary-color);
    color: white;
    font-size: 18px;
    font-weight: 700;
    cursor: pointer;
    transition: .3s;
}

.btn-login:hover {
    background: var(--primary-hover);
    transform: translateY(-2px);
}

.btn-login:disabled {
    opacity: .7;
    cursor: not-allowed;
    transform: none;
}

.login-error {
    margin-top: 16px;
    color: var(--danger-color);
    text-align: center;
    font-weight: 600;
}

.register-link {
    margin-top: 24px;
    text-align: center;
}

.register-link a {
    color: var(--primary-color);
    font-weight: 600;
}

@media(max-width:992px){
    .login-wrapper{
        grid-template-columns: 1fr;
    }

    .login-info{
        text-align:center;
    }
}
</style>
