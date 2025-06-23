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
                return;
            }
        }

        throw new IllegalArgumentException("Cliente nao encontrado");
    }

    public boolean excluirCliente(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF invalido");
        }
        
        Cliente cliente = buscarClientePorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao encontrado");
        }

        // Verifica se o cliente possui veiculos locados antes de excluir
        for (Veiculo veiculo : veiculos) {
            Locacao locacao = veiculo.getLocacao();
            if (locacao != null && locacao.getCliente().getCPF().equals(cpf)) {
                throw new IllegalArgumentException("Cliente possui veiculos locados.");
            }
        }

        clientes.remove(cliente);
        return true;
    }

    /*
     * Metodos para Gerenciamento de Veiculos
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

    /*
     * Filtra veiculos por tipo, marca e categoria.
     * Caso qualquer um dos filtros seja nulo, ele e ignorado.
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
                            tipoMatch = false;
                        }
                    }

                    boolean marcaMatch = (marca == null || veiculo.getMarca() == marca);
                    boolean categoriaMatch = (categoria == null || veiculo.getCategoria() == categoria);

                    return tipoMatch && marcaMatch && categoriaMatch;
                })
                .collect(Collectors.toList());
    }

    /*
     * Remove um veiculo da lista se ele estiver com estado VENDIDO.
     * Caso contrario, lanca excecao apropriada.
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
