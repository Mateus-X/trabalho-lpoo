package requests;

import models.Cliente;

public class ClienteRequest {
    public static Cliente Validar(Cliente cliente) throws IllegalArgumentException {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (cliente.getSobrenome() == null || cliente.getSobrenome().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome não pode ser vazio");
        }
        
        if (cliente.getRG() == null || cliente.getRG().trim().isEmpty()) {
            throw new IllegalArgumentException("RG não pode ser vazio");
        }

        if (cliente.getCPF() == null || cliente.getCPF().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        
        if (cliente.getEndereco() == null || cliente.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio");
        }

        cliente.setNome(cliente.getNome().trim());
        cliente.setSobrenome(cliente.getSobrenome().trim());
        cliente.setRG(cliente.getRG().trim());
        cliente.setCPF(cliente.getCPF().trim());
        cliente.setEndereco(cliente.getEndereco().trim());

        return cliente;
    }
}
