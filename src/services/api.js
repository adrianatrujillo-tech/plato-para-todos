const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

async function request(path, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        headers: {
            'Content-Type': 'application/json',
            ...options.headers
        },
        ...options
    })

    if (!response.ok) {
        const error = await response.json().catch(() => null)
        const message = error?.message || 'No se pudo completar la solicitud'

        throw new Error(Array.isArray(message) ? message.join(', ') : message)
    }

    if (response.status === 204) {
        return null
    }

    return response.json()
}

export const api = {
    login: (payload) =>
        request('/auth/login', {
            method: 'POST',
            body: JSON.stringify(payload)
        }),

    registrar: (payload) =>
        request('/auth/registro', {
            method: 'POST',
            body: JSON.stringify(payload)
        }),

    listarDonaciones: (params = '') =>
        request(`/donaciones${params}`),

    crearDonacion: (payload) =>
        request('/donaciones', {
            method: 'POST',
            body: JSON.stringify(payload)
        }),

    editarDonacion: (id, payload) =>
        request(`/donaciones/${id}`, {
            method: 'PUT',
            body: JSON.stringify(payload)
        }),

    eliminarDonacion: (id) =>
        request(`/donaciones/${id}`, {
            method: 'DELETE'
        }),

    listarSolicitudes: (params = '') =>
        request(`/solicitudes${params}`),

    obtenerSolicitud: (id) =>
        request(`/solicitudes/${id}`),

    crearSolicitud: (payload) =>
        request('/solicitudes', {
            method: 'POST',
            body: JSON.stringify(payload)
        }),

    editarSolicitud: (id, payload) =>
        request(`/solicitudes/${id}`, {
            method: 'PUT',
            body: JSON.stringify(payload)
        }),

    eliminarSolicitud: (id) =>
        request(`/solicitudes/${id}`, {
            method: 'DELETE'
        }),

    cambiarEstadoSolicitud: (id, estado) =>
        request(`/solicitudes/${id}/estado?estado=${estado}`, {
            method: 'PATCH'
        })
}
