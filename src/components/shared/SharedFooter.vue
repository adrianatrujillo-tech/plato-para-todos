<template>

    <footer class="footer">

        <div class="container footer-content">

            <p>

                © 2026 Plato Para Todos. Todos los derechos reservados.

            </p>

            <div class="social-links">

                <a href="#">

                    Facebook

                </a>

                <a href="#">

                    Instagram

                </a>

                <a href="#">

                    LinkedIn

                </a>

                <a href="#">

                    WhatsApp

                </a>

            </div>

        </div>

    </footer>

</template>

<script setup>

</script>

<style scoped>

.footer {

    margin-top: 60px;

    background: #1F2937;

    padding: 28px 0;

    border-top: 4px solid var(--primary-color);
}

.footer-content {

    display: flex;

    justify-content: space-between;

    align-items: center;

    gap: 20px;
}

.footer p {

    color: #D1D5DB;

    font-size: 14px;

    margin: 0;
}

.social-links {

    display: flex;

    align-items: center;

    gap: 24px;
}

.social-links a {

    color: #D1D5DB;

    text-decoration: none;

    font-size: 14px;

    font-weight: 500;

    transition: .3s;
}

.social-links a:hover {

    color: var(--success-color);

    transform: translateY(-2px);
}

/* RESPONSIVE */

@media(max-width:768px){

    .footer {

        padding: 24px 0;
    }

    .footer-content {

        flex-direction: column;

        text-align: center;

        gap: 16px;
    }

    .social-links {

        justify-content: center;

        flex-wrap: wrap;

        gap: 16px;
    }

}

</style>
