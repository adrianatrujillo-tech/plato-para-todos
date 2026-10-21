<template>

    <main>

        <!-- HERO -->

        <section
            id="inicio"
            class="hero">

            <div class="container hero-container">

                <div class="hero-content">

                    <span class="hero-badge">
                        🍽️ Plataforma Solidaria
                    </span>

                    <h1>
                        Conectamos alimentos con quienes más los necesitan
                    </h1>

                    <p>
                        Reduce el desperdicio alimentario, ayuda a tu comunidad y genera un impacto positivo a través de una red de donadores y beneficiarios.
                    </p>

                    <div class="hero-buttons">

                        <RouterLink
                            to="/registro/donador"
                            class="btn btn-primary">

                            Donar Alimentos

                        </RouterLink>

                        <RouterLink
                            to="/registro/beneficiario"
                            class="btn btn-secondary">

                            Solicitar Apoyo

                        </RouterLink>

                    </div>

                </div>

                <div class="hero-image">

                    <img
                        src="@/assets/images/hero.png"
                        alt="Plato Para Todos">

                </div>

            </div>

        </section>

        <!-- BENEFICIOS -->

        <section
            id="beneficios"
            class="section">

            <div class="container">

                <div class="section-header">

                    <span>
                        BENEFICIOS
                    </span>

                    <h2>
                        ¿Por qué elegir
                        Plato Para Todos?
                    </h2>

                </div>

                <div class="beneficios-grid">

                    <!-- BENEFICIO 1 -->

                    <div class="beneficio-card">

                        <div class="benefit-icon">

                            ♻️

                        </div>

                        <h3>

                            Reduce desperdicios

                        </h3>

                        <p>

                            Aprovecha alimentos que aún pueden beneficiar a muchas personas en lugar de terminar desechados.

                        </p>

                    </div>

                    <!-- BENEFICIO 2 -->

                    <div class="beneficio-card">

                        <div class="benefit-icon">

                            🤝

                        </div>

                        <h3>

                            Genera impacto social

                        </h3>

                        <p>

                            Contribuye directamente al bienestar de organizaciones y comunidades vulnerables.

                        </p>

                    </div>

                    <!-- BENEFICIO 3 -->

                    <div class="beneficio-card">

                        <div class="benefit-icon">

                            🚚

                        </div>

                        <h3>

                            Gestión sencilla

                        </h3>

                        <p>

                            Coordina entregas y solicitudes desde una sola plataforma de manera rápida y segura.

                        </p>

                    </div>

                    <!-- BENEFICIO 4 -->

                    <div class="beneficio-card">

                        <div class="benefit-icon">

                            📊

                        </div>

                        <h3>

                            Seguimiento transparente

                        </h3>

                        <p>

                            Visualiza el impacto generado por cada donación y mantén un control completo.

                        </p>

                    </div>

                </div>

            </div>

        </section>

        <!-- PROCESO -->

        <section
            id="proceso"
            class="section section-light">

            <div class="container">

                <div class="section-header">

                    <span>PROCESO</span>

                    <h2>
                        Cómo Funciona
                    </h2>

                </div>

                <div class="process-container">

                    <div class="process-line"></div>

                    <!-- PASO 1 -->

                    <div class="process-step">

                        <div class="step-circle">
                            1
                        </div>

                        <h3>
                            Publica una Donación
                        </h3>

                        <p>
                            Registra los alimentos disponibles.
                        </p>

                    </div>

                    <!-- PASO 2 -->

                    <div class="process-step">

                        <div class="step-circle">
                            2
                        </div>

                        <h3>
                            Recibe Solicitudes
                        </h3>

                        <p>
                            Organizaciones interesadas solicitan la donación.
                        </p>

                    </div>

                    <!-- PASO 3 -->

                    <div class="process-step">

                        <div class="step-circle">
                            3
                        </div>

                        <h3>
                            Coordina la Entrega
                        </h3>

                        <p>
                            Define horarios y modalidad de entrega.
                        </p>

                    </div>

                    <!-- PASO 4 -->

                    <div class="process-step">

                        <div class="step-circle">
                            4
                        </div>

                        <h3>
                            Genera Impacto
                        </h3>

                        <p>
                            Los alimentos llegan a quienes más los necesitan.
                        </p>

                    </div>

                </div>

            </div>

        </section>

        <!-- PARTICIPANTES -->

        <section
            id="participantes"
            class="section">

            <div class="container">

                <div class="section-header">

                    <span>PARTICIPANTES</span>

                    <h2>
                        ¿Quiénes forman parte?
                    </h2>

                </div>

                <div class="grid grid-3">

                    <div class="card">

                        <h3>🏢 Donadores</h3>

                        <p>
                            Empresas, restaurantes, mercados y supermercados.
                        </p>

                    </div>

                    <div class="card">

                        <h3>🏠 Beneficiarios</h3>

                        <p>
                            Comedores populares, ONG y organizaciones sociales.
                        </p>

                    </div>

                    <div class="card">

                        <h3>🛡️ Administradores</h3>

                        <p>
                            Supervisan y garantizan el correcto funcionamiento.
                        </p>

                    </div>

                </div>

            </div>

        </section>

        <!-- IMPACTO -->

        <section
            id="impacto"
            class="impact-section">

            <div class="section-header">

                <span>IMPACTO</span>

                <h2>

                    Nuestro Impacto

                </h2>

            </div>

            <div class="impact-grid">

                <div class="impact-card">

                    <h3>1500+</h3>

                    <p>Familias Beneficiadas</p>

                </div>

                <div class="impact-card">

                    <h3>4500 Kg</h3>

                    <p>Alimentos Recuperados</p>

                </div>

                <div class="impact-card">

                    <h3>320+</h3>

                    <p>Donaciones Realizadas</p>

                </div>

                <div class="impact-card">

                    <h3>85+</h3>

                    <p>Aliados Estratégicos</p>

                </div>

            </div>

        </section>

        <!-- TESTIMONIOS -->

        <section
            id="testimonios"
            class="section">

            <div class="section-header">

                <span>TESTIMONIOS</span>

                <h2>

                    Historias Reales

                </h2>

            </div>

            <div class="testimonios-grid">

                <div class="testimonial-card">

                    <p>

                        "Gracias a esta iniciativa logramos apoyar a más familias cada semana."

                    </p>

                    <h4>

                        Comedor Popular San José

                    </h4>

                </div>

                <div class="testimonial-card">

                    <p>

                        "Ahora nuestros excedentes tienen un impacto positivo."

                    </p>

                    <h4>

                        Supermercado Plaza

                    </h4>

                </div>

            </div>

        </section>

        <!-- CTA FINAL -->

        <section class="cta-section">

            <div class="container">

                <h2>
                    Empieza a generar impacto hoy
                </h2>

                <p>
                    Forma parte de una red que transforma alimentos en oportunidades.
                </p>

                <div class="cta-buttons">

                    <RouterLink
                        to="/registro/donador"
                        class="btn btn-primary">

                        Donar Alimentos

                    </RouterLink>

                    <RouterLink
                        to="/registro/beneficiario"
                        class="btn btn-secondary">

                        Solicitar Apoyo

                    </RouterLink>

                </div>

            </div>

        </section>

    </main>

</template>

<script setup>
</script>

<style scoped src="@/assets/styles/HomeView.css"></style>
