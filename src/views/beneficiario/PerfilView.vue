<template>

    <section class="page">

        <div class="container">

            <!-- HEADER -->

            <div class="page-header">

                <h1>

                    Mi Perfil

                </h1>

                <p>

                    Administra la información de tu organización.

                </p>

            </div>

            <!-- ORGANIZACIÓN -->

            <div class="card profile-card">

                <h2>

                    Información de la Organización

                </h2>

                <div class="grid grid-2">

                    <div>

                        <label>Tipo de Organización</label>

                        <input
                            type="text"
                            value="Comedor Popular">

                    </div>

                    <div>

                        <label>Nombre de la Organización</label>

                        <input
                            type="text"
                            value="Comedor San José">

                    </div>

                    <div>

                        <label>Cantidad de Beneficiarios</label>

                        <input
                            type="number"
                            value="350">

                    </div>

                    <div>

                        <label>Distrito</label>

                        <input
                            type="text"
                            value="Los Olivos">

                    </div>

                    <div class="full-width">

                        <label>Dirección</label>

                        <input
                            type="text"
                            value="Av. Universitaria 1234">

                    </div>

                </div>

            </div>

            <!-- RESPONSABLE -->

            <div class="card profile-card">

                <h2>

                    Responsable

                </h2>

                <div class="grid grid-2">

                    <div>

                        <label>Nombre Completo</label>

                        <input
                            type="text"
                            value="María Fernández">

                    </div>

                    <div>

                        <label>Cargo</label>

                        <input
                            type="text"
                            value="Directora">

                    </div>

                    <div>

                        <label>Teléfono</label>

                        <input
                            type="text"
                            value="987654321">

                    </div>

                    <div>

                        <label>Correo</label>

                        <input
                            type="email"
                            value="contacto@comedor.pe">

                    </div>

                </div>

            </div>

            <!-- INFORMACIÓN SOCIAL -->

            <div class="card profile-card">

                <h2>

                    Información Social

                </h2>

                <div class="grid grid-2">

                    <div>

                        <label>Población Atendida</label>

                        <input
                            type="text"
                            value="Niños y Adultos Mayores">

                    </div>

                    <div>

                        <label>Horario de Atención</label>

                        <input
                            type="text"
                            value="Lunes a Viernes 08:00 AM - 05:00 PM">

                    </div>

                    <div class="full-width">

                        <label>Descripción</label>

                        <textarea>

Organización dedicada a brindar apoyo alimentario a familias vulnerables.

                        </textarea>

                    </div>

                </div>

            </div>

            <!-- ACCIONES -->

            <div class="actions">

                <button class="btn btn-secondary">

                    Cambiar Contraseña

                </button>

                <button class="btn btn-primary">

                    Guardar Cambios

                </button>

            </div>

        </div>

    </section>

</template>

<script setup>

</script>

<style scoped>

.page {

    padding: 40px 0;
}

/* HEADER */

.page-header {

    margin-bottom: 30px;
}

.page-header h1 {

    margin-bottom: 8px;
}

.page-header p {

    color: var(--text-medium);
}

/* CARDS */

.profile-card {

    margin-bottom: 24px;
}

.profile-card h2 {

    margin-bottom: 24px;

    font-size: 24px;

    color: var(--text-dark);
}

/* FORMULARIO */

label {

    display: block;

    margin-bottom: 8px;

    font-weight: 600;

    color: var(--text-dark);
}

input,
textarea {

    width: 100%;
}

textarea {

    min-height: 140px;
}

/* GRID */

.full-width {

    grid-column: 1 / -1;
}

/* ACCIONES */

.actions {

    display: flex;

    justify-content: flex-end;

    gap: 16px;

    margin-top: 30px;
}

/* EFECTOS */

.profile-card {

    transition: .3s;
}

.profile-card:hover {

    transform: translateY(-2px);

    box-shadow: var(--shadow-lg);
}

/* RESPONSIVE */

@media(max-width:768px){

    .actions {

        flex-direction: column;
    }

    .actions .btn {

        width: 100%;
    }

    .full-width {

        grid-column: auto;
    }

}

</style>
