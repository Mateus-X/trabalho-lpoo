# 💼 Sistema de Locadora de Veículos

Projeto desenvolvido para a disciplina de **LPOO I** (Linguagem de Programação Orientada a Objetos I), com o objetivo de implementar um sistema de gerenciamento de uma locadora de veículos, utilizando **Java** com **Swing** e aplicando os conceitos de **herança**, **polimorfismo** e **encapsulamento**.

## 🧩 Funcionalidades

- **Cadastro de Clientes**
  - Incluir, editar, excluir e listar clientes.
  - Impede a exclusão de clientes com veículos locados.
  - Exibição de dados em tabela com `AbstractTableModel`.

- **Gestão de Veículos**
  - Cadastro de veículos novos (automóveis, vans e motocicletas).
  - Uso de herança com classe abstrata `Veiculo` e subclasses específicas.
  - Interface `VeiculoI` com métodos para locação, devolução e venda.

- **Locação**
  - Seleção de cliente e veículo disponível.
  - Filtros por tipo, marca e categoria.
  - Cálculo automático do valor da locação.

- **Devolução**
  - Listagem de veículos locados.
  - Devolução com atualização de estado e liberação da locação.

- **Venda**
  - Listagem de veículos disponíveis para venda.
  - Cálculo do valor de venda com base na depreciação.

## 📁 Estrutura de Pastas

```plaintext
docs/
│
├── Diagrama de Classes.asta
│
src/
│
├── locadora.model
│   ├── Cliente.java
│   ├── Locacao.java
│   ├── Veiculo.java (abstract)
│   ├── Automovel.java
│   ├── Motocicleta.java
│   ├── Van.java
│   └── enums/
│       ├── Categoria.java
│       ├── Estado.java
│       ├── Marca.java
│       ├── ModeloAutomovel.java
│       ├── ModeloMotocicleta.java
│       └── ModeloVan.java
│
├── locadora.interfaces
│   └── VeiculoI.java
│
├── locadora.dao
│   └── RepositorioMemoria.java  // lista de clientes e veículos em memória
│
├── locadora.view
│   ├── ClienteView.java
│   ├── VeiculoView.java
│   ├── LocacaoView.java
│   ├── DevolucaoView.java
│   ├── VendaView.java
│   └── components/
│       ├── ClienteTableModel.java
│       ├── VeiculoTableModel.java
│       └── Utils.java
│
├── locadora.controller
│   ├── ClienteController.java
│   ├── VeiculoController.java
│   ├── LocacaoController.java
│   ├── DevolucaoController.java
│   └── VendaController.java
│
└── Main.java
```

## 🧪 Tecnologias Utilizadas

- Java 8+
- Java Swing
- Orientação a Objetos (Herança, Polimorfismo, Encapsulamento)
- IDE: Eclipse, NetBeans e IntelliJ

## 📦 Como Executar

1. Clone este repositório:

   ```bash
   git clone https://github.com/Mateus-X/trabalho-lpoo.git
   ```
2. Importe o projeto na IDE de sua escolha (Eclipse, NetBeans ou IntelliJ).
3. Execute o arquivo Main.java.
4. Para utilizar o .jar:
    ```bash
    java -jar locadora-veiculos.jar
    ```
## 📐 Diagrama de Classes
O diagrama de classes que foi usado como base na criação das classes (models) pode ser encontrado na pasta /docs.
