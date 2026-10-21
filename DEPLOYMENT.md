# Publicar Plato para Todos en Railway

El repositorio incluye un Dockerfile con tres etapas. Primero compila Vue, después incorpora los archivos estáticos al JAR de Spring Boot y finalmente ejecuta Java 17. La web y `/api` comparten un mismo dominio. MySQL vive en un segundo servicio con almacenamiento persistente.

## Crear el proyecto

1. En Railway, crea un proyecto vacío (`New` → `Empty Project`).
2. Añade una base de datos MySQL (`New` → `Database` → `MySQL`). Mantén el volumen persistente del servicio.
3. Añade el repositorio GitHub `adrianatrujillo-tech/plato-para-todos` como otro servicio. El directorio raíz debe ser la raíz del repositorio, **no** `backend-plato-para-todos`. Railway usará el Dockerfile y `railway.json`.
4. En las variables del servicio de la aplicación añade estas referencias. Si la base tiene otro nombre, sustituye `MySQL` por el nombre real del servicio:

| Variable | Valor |
| --- | --- |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?serverTimezone=America/Lima` |
| `SPRING_DATASOURCE_USERNAME` | `${{MySQL.MYSQLUSER}}` |
| `SPRING_DATASOURCE_PASSWORD` | `${{MySQL.MYSQLPASSWORD}}` |
| `APP_SEED_ENABLED` | `false` |

Usa las referencias privadas de MySQL dentro del mismo proyecto; no copies las contraseñas al código ni expongas la base con un dominio público. `PORT` lo proporciona Railway y el backend lo utiliza automáticamente.

5. Aplica las variables y despliega/reinicia la aplicación. El health check `/api/health` verifica también la conexión a la base de datos.
6. Cuando el despliegue esté saludable, abre `Settings` → `Networking` → `Generate Domain` en el servicio de la aplicación. Abre ese dominio para probar la web.

No hace falta un servicio separado para el frontend. No establezcas `VITE_API_URL` a `localhost` en la nube: el Dockerfile usa `/api`.

## Comprobar el resultado

- Abre el dominio y entra directamente en `/login`; la ruta debe cargar sin un error 404.
- Registra una cuenta de donador con datos ficticios y una contraseña de al menos 8 caracteres.
- Publica una donación de prueba.
- Registra otra cuenta como beneficiario y solicita esa donación.
- Vuelve a la cuenta del donador y gestiona la solicitud.
- Comprueba que las cuentas distintas no pueden modificar las donaciones ajenas.
- Cierra sesión y verifica que el token anterior ya no permite acceder a la API.

## Datos, sesiones y cuentas de prueba

La nube crea sus propias tablas; no copia la base de tu laptop. La carga de usuarios de ejemplo está desactivada por defecto para evitar que las credenciales públicas del código den acceso a un administrador en Internet. El registro público permite donadores y beneficiarios, nunca administradores.

Las contraseñas de usuario se guardan con BCrypt. Al iniciar se convierten las contraseñas antiguas locales que aún estén en texto; evita importar cuentas de ejemplo con contraseñas conocidas a una base pública.

Los tokens son aleatorios, caducan a las 12 horas y se guardan en memoria del backend. Un reinicio obliga a iniciar sesión de nuevo. Mantén una sola réplica mientras se use este mecanismo. MySQL conserva cuentas, donaciones y solicitudes independientemente de los reinicios de la aplicación.

Para pruebas locales de datos iniciales puedes activar `APP_SEED_ENABLED=true` únicamente en tu equipo. No actives ese ajuste en la nube.

## Costos

Railway ofrece una prueba con crédito y duración limitados. Revisa los límites y el consumo en tu cuenta antes de contratar un plan. Este repositorio no contrata planes ni configura una tarjeta de pago.

## Validaciones

Desde la raíz:

```powershell
npm ci
npm run build
```

Desde `backend-plato-para-todos`:

```powershell
.\mvnw.cmd test
```

Las pruebas usan H2 en modo MySQL y no modifican tu base local ni la base desplegada. La publicación definitiva se verifica cuando Railway termina el build, pasa `/api/health` y sirve el dominio.
