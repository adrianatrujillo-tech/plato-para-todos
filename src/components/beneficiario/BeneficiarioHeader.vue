<template>

    <header class="beneficiario-header">

        <div class="container header-container">

            <!-- LOGO -->

            <RouterLink
                to="/beneficiario"
                class="logo">

                <span class="logo-icon">
                    🍽️
                </span>

                <div class="logo-info">

                    <span class="logo-title">
                        Plato Para Todos
                    </span>

                    <span class="logo-subtitle">
                        Portal Beneficiario
                    </span>

                </div>

            </RouterLink>

            <!-- MENU -->

            <nav class="nav-menu">

                <RouterLink to="/beneficiario">
                    Dashboard
                </RouterLink>

                <RouterLink to="/beneficiario/disponibles">
                    Disponibles
                </RouterLink>

                <RouterLink to="/beneficiario/solicitudes">
                    Solicitudes
                </RouterLink>

                <RouterLink to="/beneficiario/historial">
                    Historial
                </RouterLink>

                <RouterLink to="/beneficiario/perfil">
                    Perfil
                </RouterLink>

            </nav>

            <!-- USUARIO -->

            <div class="user-menu">

                <span class="notification">
                    🔔
                </span>

                <div class="user-info">

                    <span class="user-name">
                        Hola {{ nombre }}
                    </span>

                    <button
                        class="logout-btn"
                        @click="cerrarSesion">

                        Salir

                    </button>

                </div>

            </div>

        </div>

    </header>

</template>

<script setup>

import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()

const authStore = useAuthStore()

const nombre = computed(() =>
    authStore.usuario?.nombre || 'Beneficiario'
)

const cerrarSesion = () => {

    authStore.logout()

    router.push('/login')
}

</script>

<style scoped>
.beneficiario-header {

    position: sticky;

    top: 0;

    z-index: 1000;

    background: white;

    border-bottom: 1px solid var(--border-light);

    box-shadow: var(--shadow-sm);
}

.header-container {

    height: 80px;

    display: flex;

    align-items: center;

    justify-content: space-between;

    gap: 24px;
}

.logo {

    display: flex;

    align-items: center;

    gap: 12px;
}

.logo-icon {

    font-size: 32px;
}

.logo-info {

    display: flex;

    flex-direction: column;
}

.logo-title {

    font-weight: 700;

    font-size: 18px;
}

.logo-subtitle {

    font-size: 12px;

    color: var(--text-medium);
}

.nav-menu {

    display: flex;

    gap: 28px;
}

.nav-menu a {

    font-weight: 500;

    transition: .3s;
}

.nav-menu a:hover {

    color: var(--primary-color);
}

.router-link-active {

    color: var(--primary-color);

    font-weight: 700;
}

.user-menu {

    display: flex;

    align-items: center;

    gap: 18px;
}

.notification {

    font-size: 22px;

    cursor: pointer;
}

.user-info {

    display: flex;

    flex-direction: column;

    align-items: flex-end;
}

.user-name {

    font-weight: 600;
}

.logout-btn {

    border: none;

    background: none;

    color: var(--danger-color);

    cursor: pointer;

    font-size: 13px;
}

.logout-btn:hover {

    text-decoration: underline;
}

@media(max-width:992px){

    .nav-menu{

        display:none;
    }

}

</style>
