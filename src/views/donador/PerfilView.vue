<template>

    <section class="page">

        <div class="container">

            <div class="page-header">

                <h1>

                    Mi Perfil

                </h1>

                <p>

                    Administra la información de tu organización.

                </p>

            </div>

            <form class="perfil-form">

                <!-- ORGANIZACION -->

                <div class="card">

                    <h2>

                        🏢 Información de la Organización

                    </h2>

                    <div class="grid grid-2">

                        <div class="form-group">

                            <label>

                                Nombre de la Empresa

                            </label>

                            <input
                                type="text"
                                value="Supermercados Perú">

                        </div>

                        <div class="form-group">

                            <label>

                                RUC

                            </label>

                            <input
                                type="text"
                                value="20123456789">

                        </div>

                    </div>

                    <div class="form-group">

                        <label>

                            Tipo de Organización

                        </label>

                        <select>

                            <option>

                                Supermercado

                            </option>

                            <option>

                                Restaurante

                            </option>

                            <option>

                                Empresa

                            </option>

                        </select>

                    </div>

                </div>

                <!-- CONTACTO -->

                <div class="card">

                    <h2>

                        👤 Información de Contacto

                    </h2>

                    <div class="grid grid-2">

                        <div class="form-group">

                            <label>

                                Responsable

                            </label>

                            <input
                                type="text"
                                value="Lizbeth">

                        </div>

                        <div class="form-group">

                            <label>

                                Correo

                            </label>

                            <input
                                type="email"
                                value="lizbeth@test.com">

                        </div>

                    </div>

                    <div class="form-group">

                        <label>

                            Teléfono

                        </label>

                        <input
                            type="text"
                            value="999999999">

                    </div>

                </div>

                <!-- UBICACION -->

                <div class="card">

                    <h2>

                        📍 Ubicación

                    </h2>

                    <div class="grid grid-2">

                        <input
                            type="text"
                            placeholder="Departamento">

                        <input
                            type="text"
                            placeholder="Provincia">

                    </div>

                    <div class="grid grid-2">

                        <input
                            type="text"
                            placeholder="Distrito">

                        <input
                            type="text"
                            placeholder="Dirección">

                    </div>

                </div>

                <!-- SEGURIDAD -->

                <div class="card">

                    <h2>

                        🔐 Seguridad

                    </h2>

                    <div class="grid grid-2">

                        <input
                            type="password"
                            placeholder="Nueva contraseña">

                        <input
                            type="password"
                            placeholder="Confirmar contraseña">

                    </div>

                </div>

                <button
                    type="submit"
                    class="btn-save">

                    Guardar Cambios

                </button>

            </form>

        </div>

    </section>

</template>

<script setup>
</script>

<style scoped>

.page {

    padding: 40px 0 80px;
}

/* HEADER */

.page-header {

    margin-bottom: 40px;
}

.page-header h1 {

    margin-bottom: 8px;
}

.page-header p {

    color: var(--text-medium);

    font-size: 17px;
}

/* FORM */

.perfil-form {

    display: flex;

    flex-direction: column;

    gap: 24px;

    max-width: 1000px;

    margin: 0 auto;
}

/* CARDS */

.card {

    background: white;

    border-radius: 24px;

    padding: 32px;

    box-shadow: var(--shadow-md);

    border: 1px solid #EEF2F7;

    transition: .3s;
}

.card:hover {

    box-shadow: var(--shadow-lg);
}

.card h2 {

    display: flex;

    align-items: center;

    gap: 12px;

    margin-bottom: 28px;

    font-size: 30px;

    color: var(--text-dark);
}

/* FORM GROUP */

.form-group {

    display: flex;

    flex-direction: column;

    gap: 8px;

    margin-bottom: 20px;
}

.form-group label {

    font-size: 14px;

    font-weight: 600;

    color: var(--text-dark);
}

/* INPUTS */

input,
select {

    height: 56px;

    border: 1px solid #DDE3EA;

    border-radius: 14px;

    padding: 0 18px;

    font-size: 15px;

    transition: .3s;

    background: white;
}

input:focus,
select:focus {

    border-color: var(--primary-color);

    box-shadow:
        0 0 0 4px rgba(46,125,90,.12);
}

/* UBICACION */

.card .grid {

    margin-bottom: 18px;
}

/* BOTON */

.btn-save {

    height: 60px;

    border: none;

    border-radius: 16px;

    background: var(--primary-color);

    color: white;

    font-size: 17px;

    font-weight: 700;

    cursor: pointer;

    transition: .3s;

    box-shadow: 0 8px 20px rgba(46,125,90,.20);
}

.btn-save:hover {

    background: var(--primary-hover);

    transform: translateY(-2px);
}

/* RESPONSIVE */

@media(max-width:768px){

    .card {

        padding: 24px;
    }

    .card h2 {

        font-size: 24px;
    }

    .btn-save {

        width: 100%;
    }

}

</style>


