<template>

    <section class="page">

        <div class="container">

            <!-- BIENVENIDA -->

            <div class="welcome-card">

                <h1>

                    Bienvenido

                </h1>

                <p>

                    Gestiona tus solicitudes y consulta las donaciones disponibles.

                </p>

            </div>

            <!-- KPIS -->

            <div class="stats">

                <div class="card stat-card">

                    <h3>

                        24

                    </h3>

                    <span>

                        Disponibles

                    </span>

                </div>

                <div class="card stat-card">

                    <h3>

                        4

                    </h3>

                    <span>

                        Pendientes

                    </span>

                </div>

                <div class="card stat-card">

                    <h3>

                        8

                    </h3>

                    <span>

                        Aprobadas

                    </span>

                </div>

                <div class="card stat-card">

                    <h3>

                        18

                    </h3>

                    <span>

                        Entregadas

                    </span>

                </div>

            </div>

            <!-- GRID -->

            <div class="dashboard-grid">

                <!-- DONACIONES -->

                <div class="card">

                    <h2>

                        Donaciones Recientes

                    </h2>

                    <ul class="list">

                        <li>

                            Frutas Variadas

                        </li>

                        <li>

                            Verduras Frescas

                        </li>

                        <li>

                            Pan Integral

                        </li>

                    </ul>

                </div>

                <!-- SOLICITUDES -->

                <div class="card">

                    <h2>

                        Solicitudes Recientes

                    </h2>

                    <ul class="list">

                        <li>

                            SOL-001 · Pendiente

                        </li>

                        <li>

                            SOL-002 · Aprobada

                        </li>

                        <li>

                            SOL-003 · Lista para Recojo

                        </li>

                    </ul>

                </div>

            </div>

            <!-- ACCIONES -->

            <div class="actions">

                <RouterLink
                    to="/beneficiario/disponibles"
                    class="btn btn-primary">

                    Ver Donaciones

                </RouterLink>

                <RouterLink
                    to="/beneficiario/solicitudes"
                    class="btn btn-secondary">

                    Mis Solicitudes

                </RouterLink>

            </div>

        </div>

    </section>

</template>

<style scoped>

.page {

    padding: 40px 0;
}

/* BIENVENIDA */

.welcome-card {

    background: linear-gradient(
        135deg,
        var(--primary-color),
        #3FA271
    );

    color: white;

    padding: 40px;

    border-radius: 24px;

    margin-bottom: 30px;

    box-shadow: var(--shadow-lg);
}

.welcome-card h1 {

    font-size: 40px;

    margin-bottom: 10px;
}

.welcome-card p {

    opacity: .95;

    font-size: 16px;
}

/* KPIS */

.stats {

    display: grid;

    grid-template-columns:
        repeat(auto-fit,minmax(220px,1fr));

    gap: 20px;

    margin-bottom: 30px;
}

.stat-card {

    text-align: center;

    transition: .3s;
}

.stat-card:hover {

    transform: translateY(-4px);
}

.stat-card h3 {

    font-size: 36px;

    color: var(--primary-color);

    margin-bottom: 8px;
}

.stat-card span {

    color: var(--text-medium);

    font-weight: 500;
}

/* GRID */

.dashboard-grid {

    display: grid;

    grid-template-columns: repeat(2,1fr);

    gap: 24px;

    margin-bottom: 30px;
}

.dashboard-grid .card {

    padding: 24px;
}

.dashboard-grid h2 {

    margin-bottom: 20px;

    font-size: 22px;

    color: var(--text-dark);
}

/* LISTAS */

.list {

    display: flex;

    flex-direction: column;

    gap: 12px;
}

.list li {

    padding: 14px 16px;

    background: var(--background-light);

    border-radius: 12px;

    color: var(--text-dark);

    transition: .3s;
}

.list li:hover {

    transform: translateX(4px);
}

/* ACCIONES */

.actions {

    display: flex;

    gap: 16px;

    justify-content: center;
}

.actions .btn {

    min-width: 220px;
}

/* RESPONSIVE */

@media(max-width:992px){

    .dashboard-grid {

        grid-template-columns: 1fr;
    }

}

@media(max-width:768px){

    .welcome-card {

        padding: 24px;
    }

    .welcome-card h1 {

        font-size: 30px;
    }

    .actions {

        flex-direction: column;
    }

    .actions .btn {

        width: 100%;
    }

}

</style>
