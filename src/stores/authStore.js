import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {

    state: () => ({

        usuario: JSON.parse(localStorage.getItem('usuario') || 'null'),

        token: localStorage.getItem('token'),

        autenticado: Boolean(localStorage.getItem('token'))

    }),

    getters: {

        nombreUsuario: (state) =>
            state.usuario?.nombre || '',

        rolUsuario: (state) =>
            state.usuario?.rol || ''

    },

    actions: {

        login(authResponse) {

            this.usuario = {
                id: authResponse.id,
                nombre: authResponse.nombre,
                correo: authResponse.correo,
                rol: authResponse.rol
            }

            this.token = authResponse.token

            this.autenticado = true

            localStorage.setItem('usuario', JSON.stringify(this.usuario))

            localStorage.setItem('token', this.token)
        },

        logout() {

            this.usuario = null

            this.token = null

            this.autenticado = false

            localStorage.removeItem('usuario')

            localStorage.removeItem('token')
        }

    }

})
