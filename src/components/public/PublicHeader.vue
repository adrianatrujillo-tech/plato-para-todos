<template>

    <header class="public-header">

        <div class="container header-container">

            <!-- LOGO -->

            <RouterLink
                to="/"
                class="logo">

                <span class="logo-icon">
                    🍽️
                </span>

                <div class="logo-info">

                    <span class="logo-title">
                        Plato Para Todos
                    </span>

                    <span class="logo-subtitle">
                        Donaciones Alimentarias
                    </span>

                </div>

            </RouterLink>

            <!-- MENU -->

            <nav class="nav-menu">

                <RouterLink to="/">
                    Inicio
                </RouterLink>

                <a href="#beneficios">
                    Beneficios
                </a>

                <a href="#proceso">
                    Proceso
                </a>

                <a href="#impacto">
                    Impacto
                </a>

                <a href="#testimonios">
                    Testimonios
                </a>

            </nav>

            <!-- BOTONES -->

            <div class="header-actions">

                <RouterLink
                    to="/login"
                    class="btn btn-secondary">

                    Iniciar Sesión

                </RouterLink>

                <RouterLink
                    to="/registro"
                    class="btn btn-primary">

                    Registrarse

                </RouterLink>

            </div>

        </div>

    </header>

</template>

<script setup>
</script>

<style scoped src="./PublicHeader.css"></style>
