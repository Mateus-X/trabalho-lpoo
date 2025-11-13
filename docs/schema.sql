CREATE TABLE veiculos (
    placa TEXT PRIMARY KEY,
    marca TEXT NOT NULL,
    categoria TEXT NOT NULL,
    valor_compra REAL NOT NULL,
    ano INTEGER NOT NULL,
    estado TEXT NOT NULL,
    tipo_veiculo TEXT NOT NULL, -- 'A', 'M', ou 'V'
    modelo_automovel TEXT,
    modelo_motocicleta TEXT,
    modelo_van TEXT
);

CREATE TABLE clientes (
    cpf TEXT PRIMARY KEY,
    nome TEXT NOT NULL,
    sobrenome TEXT NOT NULL,
    rg TEXT NOT NULL,
    endereco TEXT NOT NULL
);

CREATE TABLE locacoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    veiculo_placa TEXT NOT NULL,
    cliente_cpf TEXT NOT NULL,
    data_locacao DATE NOT NULL,
    dias INTEGER NOT NULL,
    valor REAL NOT NULL,
    concluida BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY (veiculo_placa) REFERENCES veiculos (placa),
    FOREIGN KEY (cliente_cpf) REFERENCES clientes (cpf)
);