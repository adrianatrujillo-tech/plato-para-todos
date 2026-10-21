<template>
  <div>
    <DonadorHeader />

    <main>
      <RouterView />
    </main>

    <SharedFooter />
  </div>
</template>

<script setup>
import DonadorHeader from '@/components/donador/DonadorHeader.vue'
import SharedFooter from '@/components/shared/SharedFooter.vue'
</script>
