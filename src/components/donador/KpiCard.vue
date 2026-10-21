<template>

    <div class="kpi-card">

        <span class="kpi-icon">
            {{ icono }}
        </span>

        <h3>
            {{ valor }}
        </h3>

        <p>
            {{ titulo }}
        </p>

    </div>

</template>

<script setup>

defineProps({

    titulo: String,

    valor: [String, Number],

    icono: String

})

</script>

<style scoped>

.kpi-card {

    background: white;

    padding: 24px;

    border-radius: 20px;

    box-shadow: var(--shadow-md);

    transition: .3s;
}

.kpi-card:hover {

    transform: translateY(-4px);
}

.kpi-icon {

    font-size: 28px;
}

.kpi-card h3 {

    margin: 10px 0;

    color: var(--primary-color);
}

.kpi-card p {

    color: var(--text-medium);
}

</style>
