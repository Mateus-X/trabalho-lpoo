import dao.RepositorioMemoria;
import models.Cliente;
import models.Automovel;
import models.Motocicleta;
import models.Van;
import models.Veiculo;

import models.enums.Categoria;
import models.enums.Marca;
import models.enums.ModeloAutomovel;
import models.enums.ModeloMotocicleta;
import models.enums.ModeloVan;

import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o Sistema de Locadora de Veículos...\n");

        // 1. Inicializa o Repositório de Dados em Memória (o DAO)
        RepositorioMemoria repositorio = new RepositorioMemoria();

        // --- Demonstração de Clientes ---
        System.out.println("--- Testando Clientes ---");

        Cliente cliente1 = new Cliente();
        cliente1.setNome("Ana");
        cliente1.setSobrenome("Silva");
        cliente1.setCPF("111.111.111-11");
        cliente1.setRG("11.111.111-1");
        cliente1.setEndereco("Rua das Flores, 100");
        repositorio.adicionarCliente(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Bruno");
        cliente2.setSobrenome("Costa");
        cliente2.setCPF("222.222.222-22");
        cliente2.setRG("22.222.222-2");
        cliente2.setEndereco("Av. Central, 200");
        repositorio.adicionarCliente(cliente2);

        System.out.println("\nClientes Cadastrados:");
        for (Cliente c : repositorio.listarTodosClientes()) {
            System.out.println("  - " + c.getNome() + " " + c.getSobrenome() + " (CPF: " + c.getCPF() + ")");
        }

        // Testando buscar cliente
        Cliente clienteBuscado = repositorio.buscarClientePorCpf("111.111.111-11");
        if (clienteBuscado != null) {
            System.out.println("\nCliente buscado por CPF: " + clienteBuscado.getNome());
        }

        // Testando atualizar cliente
        Cliente cliente1Atualizado = repositorio.buscarClientePorCpf("111.111.111-11");
        if (cliente1Atualizado != null) {
            cliente1Atualizado.setEndereco("Rua Nova, 300");
            if (repositorio.atualizarCliente(cliente1Atualizado)) {
                System.out.println("Endereço do cliente Ana atualizado para: " + repositorio.buscarClientePorCpf("111.111.111-11").getEndereco());
            }
        }


        // --- Demonstração de Veículos ---
        System.out.println("\n--- Testando Veículos ---");

        Automovel automovel1 = new Automovel(Marca.FIAT, Categoria.POPULAR, 35000.00, "ABC-1234", 2020, ModeloAutomovel.PALIO);
        repositorio.adicionarVeiculo(automovel1);

        Motocicleta motocicleta1 = new Motocicleta(Marca.HONDA, Categoria.INTERMEDIARIO, 15000.00, "DEF-5678", 2022, ModeloMotocicleta.CBR500);
        repositorio.adicionarVeiculo(motocicleta1);

        Van van1 = new Van(Marca.MERCEDES, Categoria.LUXO, 120000.00, "GHI-9012", 2021, ModeloVan.SPRINTER);
        repositorio.adicionarVeiculo(van1);

        Automovel automovel2 = new Automovel(Marca.GM, Categoria.LUXO, 90000.00, "JKL-3456", 2023, ModeloAutomovel.CIVIC);
        repositorio.adicionarVeiculo(automovel2);


        System.out.println("\nTodos os Veículos Cadastrados:");
        for (Veiculo v : repositorio.listarTodosVeiculos()) {
            String modeloNome = "";
            if (v instanceof Automovel) modeloNome = ((Automovel)v).getModelo().name();
            else if (v instanceof Motocicleta) modeloNome = ((Motocicleta)v).getModelo().name();
            else if (v instanceof Van) modeloNome = ((Van)v).getModelo().name();

            System.out.printf("  - Placa: %s, Marca: %s, Modelo: %s, Ano: %d, Categoria: %s, Estado: %s, Diária: R$%.2f\n",
                    v.getPlaca(), v.getMarca(), modeloNome, v.getano(), v.getCategoria(), v.getEstado(), v.getValorDiariaLocacao());
        }

        // Testando locação
        System.out.println("\n--- Testando Locação e Devolução ---");
        System.out.println("Estado inicial do Automóvel ABC-1234: " + automovel1.getEstado());
        automovel1.locar(5, Calendar.getInstance(), cliente1); // Loca o automovel1 para o cliente1
        System.out.println("Estado do Automóvel ABC-1234 após locar: " + automovel1.getEstado());
        System.out.println("Locação do Automóvel ABC-1234 (valor): R$" + automovel1.getLocacao().getValor());

        System.out.println("\nVeículos Disponíveis para Locação (antes da devolução):");
        for (Veiculo v : repositorio.listarVeiculosDisponiveis()) {
            System.out.println("  - " + v.getPlaca() + " (" + v.getEstado() + ")");
        }

        System.out.println("\nVeículos Locados:");
        for (Veiculo v : repositorio.listarVeiculosLocados()) {
            System.out.println("  - " + v.getPlaca() + " (" + v.getEstado() + ") para " + v.getLocacao().getCliente().getNome());
        }

        // Tentativa de excluir cliente com veículo locado (deve falhar)
        System.out.println("\nTentando excluir Cliente 1 (com veículo locado)...");
        if (!repositorio.excluirCliente(cliente1.getCPF())) {
            System.out.println("Resultado: Falha esperada, cliente possui veículo locado.");
        }

        // Devolução
        System.out.println("\nDevolvendo Automóvel ABC-1234...");
        automovel1.devolver();
        System.out.println("Estado do Automóvel ABC-1234 após devolução: " + automovel1.getEstado());

        System.out.println("\nVeículos Disponíveis para Locação (após devolução):");
        for (Veiculo v : repositorio.listarVeiculosDisponiveis()) {
            System.out.println("  - " + v.getPlaca() + " (" + v.getEstado() + ")");
        }

        // Tentativa de excluir cliente 1 (agora deve funcionar)
        System.out.println("\nTentando excluir Cliente 1 (sem veículo locado)...");
        if (repositorio.excluirCliente(cliente1.getCPF())) {
            System.out.println("Resultado: Sucesso, cliente excluído.");
        } else {
            System.out.println("Resultado: Falha inesperada ao excluir cliente.");
        }
        System.out.println("Clientes após exclusão:");
        for (Cliente c : repositorio.listarTodosClientes()) {
            System.out.println("  - " + c.getNome() + " " + c.getSobrenome());
        }


        // --- Testando Venda ---
        System.out.println("\n--- Testando Venda ---");
        System.out.println("Valor para Venda do Automóvel JKL-3456: R$" + automovel2.getValorParaVenda());
        System.out.println("Estado inicial do Automóvel JKL-3456: " + automovel2.getEstado());
        automovel2.vender();
        System.out.println("Estado do Automóvel JKL-3456 após venda: " + automovel2.getEstado());

        System.out.println("\nVeículos Disponíveis para Venda:");
        for (Veiculo v : repositorio.listarVeiculosParaVenda()) {
            System.out.println("  - " + v.getPlaca() + " (" + v.getEstado() + ")");
        }

        // Removendo veículo vendido do repositório
        System.out.println("\nRemovendo Automóvel JKL-3456 do repositório (após venda)...");
        if (repositorio.removerVeiculo(automovel2.getPlaca())) {
            System.out.println("Veículo removido.");
        } else {
            System.out.println("Falha ao remover veículo.");
        }
        System.out.println("Todos os Veículos Cadastrados (após remover JKL-3456):");
        for (Veiculo v : repositorio.listarTodosVeiculos()) {
            System.out.println("  - " + v.getPlaca() + " (" + v.getEstado() + ")");
        }


        // --- Testando Filtros ---
        System.out.println("\n--- Testando Filtros de Veículos ---");
        // Filtrar por tipo (Motocicleta)
        List<Veiculo> motosFiltradas = repositorio.filtrarVeiculos("Motocicleta", null, null);
        System.out.println("Motocicletas filtradas:");
        for (Veiculo v : motosFiltradas) {
            System.out.println("  - " + v.getPlaca() + " (Modelo: " + ((Motocicleta)v).getModelo() + ")");
        }

        // Filtrar por Categoria (LUXO)
        List<Veiculo> luxoFiltrados = repositorio.filtrarVeiculos(null, null, Categoria.LUXO);
        System.out.println("\nVeículos de Luxo filtrados:");
        for (Veiculo v : luxoFiltrados) {
            String modeloNome = "";
            if (v instanceof Automovel) modeloNome = ((Automovel)v).getModelo().name();
            else if (v instanceof Van) modeloNome = ((Van)v).getModelo().name(); // Vans de luxo

            System.out.println("  - " + v.getPlaca() + " (Categoria: " + v.getCategoria() + ", Modelo: " + modeloNome + ")");
        }
    }
}