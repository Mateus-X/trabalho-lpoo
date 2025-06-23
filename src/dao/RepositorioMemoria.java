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
     * Metodos para Gerenciamento de Clientes
     */

    public void adicionarCliente(Cliente cliente) throws IllegalArgumentException {
        if (cliente != null && buscarClientePorCpf(cliente.getCPF()) == null) {
            this.clientes.add(cliente);
        } else {
            throw new IllegalArgumentException("Cliente ja cadastrado ou invalido.");
        }
    }

    public List<Cliente> listarTodosClientes() {
        return new ArrayList<>(this.clientes);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF invalido");
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public void atualizarCliente(Cliente clienteAtualizado) {
        if (clienteAtualizado == null || clienteAtualizado.getCPF() == null) {
            throw new IllegalArgumentException("Dados de atualizacao invalidos");
        }

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCPF().equals(clienteAtualizado.getCPF())) {
                clientes.set(i, clienteAtualizado);
            }
        }

    }

    public boolean excluirCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF invalido");
        } else if (buscarClientePorCpf(cpf) == null) {
            throw new IllegalArgumentException("Cliente nao encontrado");
        }

        // Requisito 1.c: Deve ser possivel excluir um cliente que nao possua veiculos
        // locados.
        for (Veiculo veiculo : veiculos) {
            Locacao locacao = veiculo.getLocacao();
            if (locacao != null && locacao.getCliente() != null && locacao.getCliente().getCPF().equals(cpf)) {
                throw new IllegalArgumentException("Cliente possui veiculos locados.");
            }
        }

        Cliente clienteParaRemover = buscarClientePorCpf(cpf);
        if (clienteParaRemover != null) {
            clientes.remove(clienteParaRemover);
            return true;
        }
        throw new IllegalArgumentException("CPF invalido");
    }

    /*
     * Metodos para Gerenciamento de Veiculos
     */

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo != null && buscarVeiculoPorPlaca(veiculo.getPlaca()) == null) {
            this.veiculos.add(veiculo);
        } else {
            throw new IllegalArgumentException("Veiculo ja cadastrado ou invalido.");
        }
    }

    public List<Veiculo> listarTodosVeiculos() {
        return new ArrayList<>(this.veiculos);
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa invalida");
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
     * Filtra veiculos por tipo, marca e/ou categoria.
     * Os parametros podem ser nulos para indicar que nao sao criterios de filtro.
     *
     * @param tipo      String ("Automovel", "Motocicleta", "Van") ou null
     * @param marca     Enum Marca ou null
     * @param categoria Enum Categoria ou null
     * @return Lista de veiculos filtrados.
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
                            tipoMatch = false; // Tipo invalido
                        }
                    }

                    boolean marcaMatch = (marca == null || veiculo.getMarca() == marca);
                    boolean categoriaMatch = (categoria == null || veiculo.getCategoria() == categoria);

                    return tipoMatch && marcaMatch && categoriaMatch;
                })
                .collect(Collectors.toList());
    }

    /**
     * Remove um veiculo da memoria (usado apos a venda, por exemplo).
     * 
     * @param placa A placa do veiculo a ser removido.
     * @return true se o veiculo foi removido, false caso contrario.
     */
    public boolean removerVeiculo(String placa) {
        Veiculo veiculoParaRemover = buscarVeiculoPorPlaca(placa);
        if (veiculoParaRemover != null && veiculoParaRemover.getEstado() == Estado.VENDIDO) {
            veiculos.remove(veiculoParaRemover);
            return true;
        } else if (veiculoParaRemover != null) {
            throw new IllegalArgumentException("Veiculo nao pode ser removido, pois nao esta vendido.");
        }

        throw new IllegalArgumentException("Veiculo nao encontrado.");
    }
}