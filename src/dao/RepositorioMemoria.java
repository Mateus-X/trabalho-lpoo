package dao;

import models.Cliente;
import models.Locacao;
import models.Veiculo;
import models.Automovel;
import models.Motocicleta;
import models.Van;

import models.enums.Categoria;
import models.enums.Estado;
import models.enums.Marca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioMemoria {

    private List<Cliente> clientes;
    private List<Veiculo> veiculos;

    public RepositorioMemoria() {
        this.clientes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
    }

    /*
     * Métodos para Gerenciamento de Clientes
     */

    public void adicionarCliente(Cliente cliente) {
        if (cliente != null && buscarClientePorCpf(cliente.getCPF()) == null) {
            this.clientes.add(cliente);
        }
    }

    public List<Cliente> listarTodosClientes() {
        return new ArrayList<>(this.clientes);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public boolean atualizarCliente(Cliente clienteAtualizado) {
        if (clienteAtualizado == null || clienteAtualizado.getCPF() == null) {
            return false;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCPF().equals(clienteAtualizado.getCPF())) {
                clientes.set(i, clienteAtualizado);
                return true;
            }
        }
        return false;
    }

    public boolean excluirCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        // Requisito 1.c: Deve ser possível excluir um cliente que não possua veículos locados. 
        for (Veiculo veiculo : veiculos) {
            Locacao locacao = veiculo.getLocacao();
            if (locacao != null && locacao.getCliente() != null && locacao.getCliente().getCPF().equals(cpf)) {
                return false; // Cliente possui veículo locado, não pode ser excluído 
            }
        }

        Cliente clienteParaRemover = buscarClientePorCpf(cpf);
        if (clienteParaRemover != null) {
            clientes.remove(clienteParaRemover);
            return true;
        }
        return false;
    }

    /*
     * Métodos para Gerenciamento de Veículos
     */

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo != null && buscarVeiculoPorPlaca(veiculo.getPlaca()) == null) {
            this.veiculos.add(veiculo);
        }
    }

    public List<Veiculo> listarTodosVeiculos() {
        return new ArrayList<>(this.veiculos);
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return null;
        }
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equals(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.DISPONIVEL)
                .collect(Collectors.toList());
    }

    public List<Veiculo> listarVeiculosLocados() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.LOCADO)
                .collect(Collectors.toList());
    }

    public List<Veiculo> listarVeiculosParaVenda() {
        return veiculos.stream()
                .filter(v -> v.getEstado() == Estado.DISPONIVEL)
                .collect(Collectors.toList());
    }

    /**
     * Filtra veículos por tipo, marca e/ou categoria. 
     * Os parâmetros podem ser nulos para indicar que não são critérios de filtro.
     *
     * @param tipo String ("Automovel", "Motocicleta", "Van") ou null
     * @param marca Enum Marca ou null
     * @param categoria Enum Categoria ou null
     * @return Lista de veículos filtrados.
     */
    public List<Veiculo> filtrarVeiculos(String tipo, Marca marca, Categoria categoria) {
        return veiculos.stream()
                .filter(veiculo -> {
                    boolean tipoMatch = true;
                    if (tipo != null && !tipo.trim().isEmpty()) {
                        if (tipo.equalsIgnoreCase("Automovel")) {
                            tipoMatch = veiculo instanceof Automovel;
                        } else if (tipo.equalsIgnoreCase("Motocicleta")) {
                            tipoMatch = veiculo instanceof Motocicleta;
                        } else if (tipo.equalsIgnoreCase("Van")) {
                            tipoMatch = veiculo instanceof Van;
                        } else {
                            tipoMatch = false; // Tipo inválido
                        }
                    }

                    boolean marcaMatch = (marca == null || veiculo.getMarca() == marca);
                    boolean categoriaMatch = (categoria == null || veiculo.getCategoria() == categoria);

                    return tipoMatch && marcaMatch && categoriaMatch;
                })
                .collect(Collectors.toList());
    }

    /**
     * Remove um veículo da memória (usado após a venda, por exemplo).
     * @param placa A placa do veículo a ser removido.
     * @return true se o veículo foi removido, false caso contrário.
     */
    public boolean removerVeiculo(String placa) {
        Veiculo veiculoParaRemover = buscarVeiculoPorPlaca(placa);
        if (veiculoParaRemover != null && veiculoParaRemover.getEstado() == Estado.VENDIDO) {
            veiculos.remove(veiculoParaRemover);
            return true;
        } else if (veiculoParaRemover != null) {
            // Veículo existe, mas não está no estado VENDIDO, não pode ser removido
            return false;
        }
        return false; // Veículo não encontrado
    }
}