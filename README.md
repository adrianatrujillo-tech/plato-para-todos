# Plato para Todos

## Demo en línea

[**Abrir Plato para Todos**](https://plato-para-todos-production.up.railway.app)

Aplicación académica para conectar donadores de alimentos con beneficiarios, desarrollada con Vue, Spring Boot y MySQL y desplegada en Railway. Puedes crear una cuenta de donador o beneficiario para probarla; utiliza datos ficticios.

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VS Code](https://code.visualstudio.com/) + [Vue (Official)](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Recommended Browser Setup

- Chromium-based browsers (Chrome, Edge, Brave, etc.):
  - [Vue.js devtools](https://chromewebstore.google.com/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd)
  - [Turn on Custom Object Formatter in Chrome DevTools](http://bit.ly/object-formatters)
- Firefox:
  - [Vue.js devtools](https://addons.mozilla.org/en-US/firefox/addon/vue-js-devtools/)
  - [Turn on Custom Object Formatter in Firefox DevTools](https://fxdx.dev/firefox-devtools-custom-object-formatters/)

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## Despliegue y acceso en Internet

Para publicar Vue, Spring Boot y MySQL en Railway, sigue [DEPLOYMENT.md](DEPLOYMENT.md). La aplicación usa un solo dominio para la interfaz y la API, variables de entorno para MySQL y un endpoint de salud.

El registro y la API requieren tokens verificados. El registro público no crea administradores y las cuentas de ejemplo están desactivadas por defecto. Usa datos ficticios en esta demostración académica. Las pantallas administrativas originales son provisionales y no constituyen un panel terminado.
