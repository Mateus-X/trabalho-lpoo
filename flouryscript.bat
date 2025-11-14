@echo off
setlocal ENABLEDELAYEDEXPANSION

rem ========================================
rem Configurações
rem ========================================
set "SRC_DIR=src"
set "BIN_DIR=bin"
set "LIB_DIR=lib"
set "MAIN_CLASS=Main"

rem Nome do JAR do driver JDBC
set "JDBC_JAR=sqlite-jdbc-3.51.0.0.jar"

rem Montar o classpath (.;lib\driver.jar)
set "CLASSPATH=.;%LIB_DIR%\%JDBC_JAR%"

rem ========================================
rem Limpar compilação anterior
rem ========================================
if exist "%BIN_DIR%" (
    rmdir /s /q "%BIN_DIR%"
)
mkdir "%BIN_DIR%"

rem ========================================
rem Localizar arquivos Java
rem ========================================
set "JAVA_FILES="
for /r "%SRC_DIR%" %%f in (*.java) do (
    set "JAVA_FILES=!JAVA_FILES! "%%f""
)

if "!JAVA_FILES!"=="" (
    echo Nao foram encontrados arquivos Java em "%SRC_DIR%".
    exit /b 1
)

rem ========================================
rem Compilar arquivos Java
rem ========================================
echo Compilando arquivos Java...
javac -cp "%CLASSPATH%" -d "%BIN_DIR%" !JAVA_FILES!
if errorlevel 1 (
    echo Falha na compilacao.
    exit /b 1
)

rem ========================================
rem Executar aplicação
rem ========================================
echo.
echo Executando aplicacao...
java -cp "%BIN_DIR%;%CLASSPATH%" %MAIN_CLASS%
if errorlevel 1 (
    echo Falha ao executar a aplicacao.
    exit /b 1
)

echo.
echo Aplicacao finalizada com sucesso.
exit /b 0
