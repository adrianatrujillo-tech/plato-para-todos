<template>

    <aside class="sidebar">

        <!-- LOGO -->

        <div class="sidebar-logo">

            <span class="logo-icon">
                🍽️
            </span>

            <div>

                <h3>
                    Plato Para Todos
                </h3>

                <span>
                    Administrador
                </span>

            </div>

        </div>

        <!-- MENU -->

        <nav class="sidebar-menu">

            <RouterLink to="/admin">

                📊 Dashboard

            </RouterLink>

            <RouterLink to="/admin/usuarios">

                👥 Usuarios

            </RouterLink>

            <RouterLink to="/admin/donadores">

                🏢 Donadores

            </RouterLink>

            <RouterLink to="/admin/beneficiarios">

                🏠 Beneficiarios

            </RouterLink>

            <RouterLink to="/admin/donaciones">

                🍎 Donaciones

            </RouterLink>

            <RouterLink to="/admin/reportes">

                📈 Reportes

            </RouterLink>

            <RouterLink to="/admin/configuracion">

                ⚙️ Configuración

            </RouterLink>

        </nav>

        <!-- FOOTER -->

        <div class="sidebar-footer">

            <p>

                {{ nombre }}

            </p>

            <button
                class="logout-btn"
                @click="cerrarSesion">

                Cerrar Sesión

            </button>

        </div>

    </aside>

</template>

<script setup>

import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()

const authStore = useAuthStore()

const nombre = computed(() =>
    authStore.usuario?.nombre || 'Administrador'
)

const cerrarSesion = () => {

    authStore.logout()

    router.push('/login')
}

</script>

<style scoped>
.sidebar {

    width: 280px;

    height: 100vh;

    position: fixed;

    left: 0;
    top: 0;

    background: white;

    border-right: 1px solid var(--border-light);

    display: flex;

    flex-direction: column;

    justify-content: space-between;

    padding: 24px;

    box-shadow: var(--shadow-md);
}

/* LOGO */

.sidebar-logo {

    display: flex;

    align-items: center;

    gap: 12px;

    margin-bottom: 40px;
}

.logo-icon {

    font-size: 36px;
}

.sidebar-logo h3 {

    font-size: 18px;
}

.sidebar-logo span {

    font-size: 12px;

    color: var(--text-medium);
}

/* MENU */

.sidebar-menu {

    display: flex;

    flex-direction: column;

    gap: 8px;
}

.sidebar-menu a {

    padding: 14px 18px;

    border-radius: 12px;

    transition: .3s;

    font-weight: 500;
}

.sidebar-menu a:hover {

    background: var(--background-light);

    color: var(--primary-color);
}

.sidebar-menu .router-link-active {

    background: var(--primary-color);

    color: white;
}

/* FOOTER */

.sidebar-footer {

    border-top: 1px solid var(--border-light);

    padding-top: 20px;
}

.sidebar-footer p {

    font-weight: 600;

    margin-bottom: 10px;
}

.logout-btn {

    width: 100%;

    border: none;

    padding: 12px;

    border-radius: 12px;

    background: var(--danger-color);

    color: white;

    cursor: pointer;
}

.logout-btn:hover {

    opacity: .9;
}

</style>
