<template>

    <section class="dashboard">

        <div class="container">

            <!-- HEADER -->

            <div class="page-header">

                <h1>

                    Dashboard

                </h1>

                <p>

                    Bienvenido al portal de donaciones.

                </p>

            </div>

            <!-- KPIS -->

            <div class="grid grid-4">

                <KpiCard
                    titulo="Donaciones Activas"
                    valor="12"
                    icono="📦"
                />

                <KpiCard
                    titulo="Solicitudes Pendientes"
                    valor="8"
                    icono="📩"
                />

                <KpiCard
                    titulo="Entregadas"
                    valor="25"
                    icono="🚚"
                />

                <KpiCard
                    titulo="Kg Donados"
                    valor="320"
                    icono="⚖️"
                />

            </div>

            <!-- SOLICITUDES -->

            <div class="dashboard-section">

                <div class="section-header">

                    <h2>

                        Solicitudes Pendientes

                    </h2>

                    <RouterLink
                        to="/donador/solicitudes"
                        class="link-action">

                        Ver todas →

                    </RouterLink>

                </div>

                <div class="card">

                    <table>

                        <thead>

                            <tr>

                                <th>Beneficiario</th>
                                <th>Donación</th>
                                <th>Cantidad</th>
                                <th>Estado</th>

                            </tr>

                        </thead>

                        <tbody>

                            <tr>

                                <td>Comedor San José</td>
                                <td>Frutas Variadas</td>
                                <td>20 Kg</td>

                                <td>

                                    <span class="badge pendiente">

                                        Pendiente

                                    </span>

                                </td>

                            </tr>

                            <tr>

                                <td>Fundación Esperanza</td>
                                <td>Verduras Frescas</td>
                                <td>35 Kg</td>

                                <td>

                                    <span class="badge pendiente">

                                        Pendiente

                                    </span>

                                </td>

                            </tr>

                        </tbody>

                    </table>

                </div>

            </div>

            <!-- DONACIONES ACTIVAS -->

            <div class="dashboard-section">

                <div class="section-header">

                    <h2>

                        Donaciones Activas

                    </h2>

                    <RouterLink
                        to="/donador/donaciones"
                        class="link-action">

                        Gestionar →

                    </RouterLink>

                </div>

                <div class="grid grid-2">

                    <div class="card donation-card">

                        <h3>

                            Frutas Variadas

                        </h3>

                        <p>

                            Disponible: 50 Kg

                        </p>

                        <span>

                            3 solicitudes recibidas

                        </span>

                    </div>

                    <div class="card donation-card">

                        <h3>

                            Verduras Frescas

                        </h3>

                        <p>

                            Disponible: 80 Kg

                        </p>

                        <span>

                            2 solicitudes recibidas

                        </span>

                    </div>

                </div>

            </div>

            <!-- ACTIVIDAD -->

            <div class="dashboard-section">

                <h2>

                    Actividad Reciente

                </h2>

                <div class="card">

                    <div class="activity-item">

                        <div class="activity-icon">

                            📩

                        </div>

                        <div>

                            <strong>

                                Nueva solicitud recibida

                            </strong>

                            <p>

                                Comedor San José solicitó 20 Kg de frutas.

                            </p>

                        </div>

                    </div>

                    <div class="activity-item">

                        <div class="activity-icon">

                            ✅

                        </div>

                        <div>

                            <strong>

                                Solicitud aprobada

                            </strong>

                            <p>

                                Fundación Esperanza fue aprobada.

                            </p>

                        </div>

                    </div>

                    <div class="activity-item">

                        <div class="activity-icon">

                            🚚

                        </div>

                        <div>

                            <strong>

                                Donación entregada

                            </strong>

                            <p>

                                Se completó una entrega de 35 Kg.

                            </p>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </section>

</template>

<script setup>

import KpiCard from '@/components/donador/KpiCard.vue'

</script>

<style scoped>

.dashboard {

    padding: 40px 0;

}

.page-header {

    margin-bottom: 40px;

}

.page-header p {

    color: var(--text-medium);

}

/* SECCIONES */

.dashboard-section {

    margin-top: 30px;

}

.section-header {

    display: flex;

    justify-content: space-between;

    align-items: center;

    margin-bottom: 18px;

}

.link-action {

    color: var(--primary-color);

    font-weight: 600;

}

/* TABLA */

table {

    width: 100%;

    border-collapse: collapse;

}

th {

    background: var(--background-light);

    text-align: left;

    padding: 16px;

}

td {

    padding: 16px;

    border-bottom: 1px solid var(--border-light);

}

/* BADGES */

.badge {

    padding: 8px 12px;

    border-radius: 999px;

    font-size: 12px;

    font-weight: 700;

}

.pendiente {

    background: #FEF3C7;

    color: #92400E;

}

/* DONACIONES */

.donation-card h3 {

    margin-bottom: 10px;

}

.donation-card p {

    margin-bottom: 8px;

}

.donation-card span {

    color: var(--text-medium);

}

/* ACTIVIDAD */

.activity-item {

    display: flex;

    gap: 16px;

    padding: 16px 0;

    border-bottom: 1px solid var(--border-light);

}

.activity-item:last-child {

    border-bottom: none;

}

.activity-icon {

    width: 45px;

    height: 45px;

    border-radius: 12px;

    background: var(--background-light);

    display: flex;

    justify-content: center;

    align-items: center;

    font-size: 20px;

}

.activity-item p {

    color: var(--text-medium);

    margin-top: 4px;

}

@media(max-width:768px){

    .section-header {

        flex-direction: column;

        align-items: flex-start;

        gap: 10px;

    }

}

</style>
