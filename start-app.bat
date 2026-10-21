@echo off
setlocal

set "ROOT=%~dp0"
set "BACKEND_DIR=%ROOT%backend-plato-para-todos"

echo Iniciando backend Spring Boot en http://localhost:8080
start "Plato Para Todos - Backend" cmd /k "cd /d "%BACKEND_DIR%" && run-backend.bat"

echo Iniciando frontend Vue en http://localhost:5173
start "Plato Para Todos - Frontend" cmd /k "cd /d "%ROOT%" && npm.cmd run dev -- --host 127.0.0.1"

echo.
echo Listo. Espera unos segundos y abre:
echo http://localhost:5173
echo.
pause
