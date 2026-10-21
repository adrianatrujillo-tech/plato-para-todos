<template>

    <PublicHeader />

    <main>

        <RouterView />

    </main>

    <PublicFooter />

</template>

<script setup>

import PublicHeader from '@/components/public/PublicHeader.vue'
import PublicFooter from '@/components/public/PublicFooter.vue'

</script>
