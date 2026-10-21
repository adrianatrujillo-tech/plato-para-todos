@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Java\jdk-17.0.19"
set "PATH=%JAVA_HOME%\bin;%PATH%"

if "%MYSQL_PASSWORD%"=="" (
    set /p MYSQL_PASSWORD=Clave de MySQL para root:
)

cd /d "%~dp0"

echo Iniciando backend en http://localhost:8080
call mvnw.cmd spring-boot:run

if errorlevel 1 (
    echo.
    echo No se pudo iniciar el backend.
    pause
    exit /b 1
)

pause
