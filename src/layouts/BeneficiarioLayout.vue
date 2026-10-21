<template>
  <div>
    <BeneficiarioHeader />

    <main>
      <RouterView />
    </main>

    <SharedFooter />
  </div>
</template>

<script setup>
import BeneficiarioHeader from '@/components/beneficiario/BeneficiarioHeader.vue'
import SharedFooter from '@/components/shared/SharedFooter.vue'
</script>
