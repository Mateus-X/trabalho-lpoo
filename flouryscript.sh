#!/usr/bin/env bash
set -euo pipefail

# ========================================
# Configurações
# ========================================
SRC_DIR="src"
BIN_DIR="bin"
LIB_DIR="lib"
MAIN_CLASS="Main"

# Nome do JAR do driver JDBC
JDBC_JAR="sqlite-jdbc-3.46.0.0.jar"

# Montar o classpath (.:lib/driver.jar)
CLASSPATH=".:$LIB_DIR/$JDBC_JAR"

# ========================================
# Limpar compilação anterior
# ========================================
if [ -d "$BIN_DIR" ]; then
    rm -rf "$BIN_DIR"
fi
mkdir -p "$BIN_DIR"

# ========================================
# Localizar arquivos Java
# ========================================
JAVA_FILES=$(find "$SRC_DIR" -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "Não foram encontrados arquivos Java em '$SRC_DIR'."
    exit 1
fi

# ========================================
# Compilar arquivos Java
# ========================================
echo "Compilando arquivos Java..."
javac -cp "$CLASSPATH" -d "$BIN_DIR" $JAVA_FILES || {
    echo "Falha na compilação."
    exit 1
}

# ========================================
# Executar aplicação
# ========================================
echo
echo "Executando aplicação..."
java -cp "$BIN_DIR:$CLASSPATH" "$MAIN_CLASS" || {
    echo "Falha ao executar a aplicação."
    exit 1
}

echo
echo "Aplicação finalizada com sucesso."
