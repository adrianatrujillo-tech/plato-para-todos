@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Java\jdk-17.0.19"
set "PATH=%JAVA_HOME%\bin;%PATH%"

cd /d "%~dp0"

call mvnw.cmd test

if errorlevel 1 (
    echo.
    echo El backend tiene errores o Maven no pudo descargar dependencias.
    pause
    exit /b 1
)

pause
