import { createRouter, createWebHistory } from 'vue-router'

// Layouts
import PublicLayout from '@/layouts/PublicLayout.vue'
import DonadorLayout from '@/layouts/DonadorLayout.vue'
import BeneficiarioLayout from '@/layouts/BeneficiarioLayout.vue'
import AdminLayout from '@/layouts/AdminLayout.vue'

// Public
import HomeView from '@/views/public/HomeView.vue'
import LoginView from '@/views/public/LoginView.vue'
import RegistroSelectorView from '@/views/public/RegistroSelectorView.vue'
import RegistroDonadorView from '@/views/public/RegistroDonadorView.vue'
import RegistroBeneficiarioView from '@/views/public/RegistroBeneficiarioView.vue'

// Donador
import DashboardDonador from '@/views/donador/DashboardView.vue'
import DonacionesView from '@/views/donador/DonacionesView.vue'
import NuevaDonacionView from '@/views/donador/NuevaDonacionView.vue'
import SolicitudesDonadorView from '@/views/donador/SolicitudesView.vue'
import HistorialDonadorView from '@/views/donador/HistorialView.vue'
import PerfilDonadorView from '@/views/donador/PerfilView.vue'

// Beneficiario
import DashboardBeneficiario from '@/views/beneficiario/DashboardView.vue'
import DisponiblesView from '@/views/beneficiario/DisponiblesView.vue'
import SolicitudesBeneficiarioView from '@/views/beneficiario/SolicitudesView.vue'
import HistorialBeneficiarioView from '@/views/beneficiario/HistorialView.vue'
import PerfilBeneficiarioView from '@/views/beneficiario/PerfilView.vue'

// Admin
import DashboardAdmin from '@/views/admin/DashboardView.vue'
import UsuariosView from '@/views/admin/UsuariosView.vue'
import DonadoresView from '@/views/admin/DonadoresView.vue'
import BeneficiariosView from '@/views/admin/BeneficiariosView.vue'
import DonacionesAdminView from '@/views/admin/DonacionesView.vue'
import ReportesView from '@/views/admin/ReportesView.vue'
import ConfiguracionView from '@/views/admin/ConfiguracionView.vue'

const router = createRouter({
  history: createWebHistory(),

  routes: [
    {
      path: '/',
      component: PublicLayout,
      children: [
        { path: '', component: HomeView },
        { path: 'login', component: LoginView },
        { path: 'registro', component: RegistroSelectorView },
        { path: 'registro/donador', component: RegistroDonadorView },
        { path: 'registro/beneficiario', component: RegistroBeneficiarioView },
      ],
    },

    {
      path: '/donador',
      component: DonadorLayout,
      children: [
        { path: '', component: DashboardDonador },
        { path: 'donaciones', component: DonacionesView },
        { path: 'nueva-donacion', component: NuevaDonacionView },
        { path: 'solicitudes', component: SolicitudesDonadorView },
        { path: 'historial', component: HistorialDonadorView },
        { path: 'perfil', component: PerfilDonadorView },
      ],
    },

    {
      path: '/beneficiario',
      component: BeneficiarioLayout,
      children: [
        { path: '', component: DashboardBeneficiario },
        { path: 'disponibles', component: DisponiblesView },
        { path: 'solicitudes', component: SolicitudesBeneficiarioView },
        { path: 'historial', component: HistorialBeneficiarioView },
        { path: 'perfil', component: PerfilBeneficiarioView },
      ],
    },

    {
      path: '/admin',
      component: AdminLayout,
      children: [
        { path: '', component: DashboardAdmin },
        { path: 'usuarios', component: UsuariosView },
        { path: 'donadores', component: DonadoresView },
        { path: 'beneficiarios', component: BeneficiariosView },
        { path: 'donaciones', component: DonacionesAdminView },
        { path: 'reportes', component: ReportesView },
        { path: 'configuracion', component: ConfiguracionView },
      ],
    },
  ],
})

export default router
