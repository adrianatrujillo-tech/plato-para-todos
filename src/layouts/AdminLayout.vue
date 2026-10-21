<template>
  <div class="admin-layout">
    <AdminSidebar />

    <main class="admin-content">
      <AdminTopbar />

      <RouterView />
    </main>

     <SharedFooter />
  </div>
</template>

<script setup>
import AdminSidebar from '@/components/admin/AdminSidebar.vue'
import SharedFooter from '@/components/shared/SharedFooter.vue'
</script>

<style scoped>

.admin-content {

    margin-left: 280px;

    padding: 30px;
}

</style>
r
