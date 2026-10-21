<template>

    <section class="section">

        <div class="container">

            <div class="registro-header">

                <span class="badge">

                    REGISTRO
                </span>

                <h1>

                    ¿Cómo deseas participar?

                </h1>

                <p>

                    Selecciona el tipo de cuenta que mejor represente tu organización.

                </p>

            </div>

            <div class="registro-grid">

                <!-- DONADOR -->

                <RouterLink
                    to="/registro/donador"
                    class="registro-card">

                    <span class="role-tag">
                        Dona alimentos
                    </span>

                    <div class="icon">

                        🏢

                    </div>

                    <h2>

                        Donador

                    </h2>

                    <p>

                        Empresas, supermercados,
                        restaurantes y negocios que desean donar alimentos.

                    </p>

                    <span class="btn btn-primary">

                        Registrarme como Donador

                    </span>

                </RouterLink>

                <!-- BENEFICIARIO -->

                <RouterLink
                    to="/registro/beneficiario"
                    class="registro-card">

                    <span class="role-tag">
                        Recibe apoyo
                    </span>

                    <div class="icon">

                        🏠

                    </div>

                    <h2>

                        Beneficiario

                    </h2>

                    <p>

                        Organizaciones sociales,
                        comedores populares y ONG que reciben apoyo.

                    </p>

                    <span class="btn btn-primary">

                        Registrarme como Beneficiario

                    </span>

                </RouterLink>

            </div>

        </div>

    </section>

</template>

<script setup>
</script>

<style scoped>

.registro-header {

    text-align: center;

    max-width: 700px;

    margin: 0 auto 40px;
}

.registro-header h1 {

    font-size: clamp(40px, 5vw, 60px);

    line-height: 1.1;

    margin: 12px 0;
}

.registro-header p{

    color:var(--text-medium);

    margin-top:12px;
}

.registro-grid{

    display:grid;

    grid-template-columns:repeat(2,1fr);

    gap:30px;
}

.registro-card {

    background: white;

    padding: 32px;

    border-radius: var(--radius-lg);

    box-shadow: var(--shadow-md);

    display: flex;

    flex-direction: column;

    align-items: center;

    text-align: center;

    transition: .3s;

    min-height: 380px;
}

.registro-card:hover {

    transform: translateY(-10px);

    box-shadow: var(--shadow-xl);

    border: 2px solid var(--primary-color);
}

.icon {

    font-size: 80px;

    margin-bottom: 24px;
}

.registro-card p{

    color:var(--text-medium);

    margin:20px 0 30px;
}

.section {

    background:
        linear-gradient(
            180deg,
            #f8faf8 0%,
            #eef5f0 100%
        );

    min-height: calc(100vh - 80px);

    display: flex;

    align-items: center;
}

.role-tag {

    background: rgba(46,125,90,.1);

    color: var(--primary-color);

    padding: 6px 14px;

    border-radius: 999px;

    font-size: 13px;

    font-weight: 600;

    margin-bottom: 20px;
}

@media(max-width:768px){

    .registro-grid{

        grid-template-columns:1fr;
    }

}
</style>
