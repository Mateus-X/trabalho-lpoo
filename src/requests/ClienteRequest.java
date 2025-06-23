package requests;

import models.Cliente;

public class ClienteRequest {
    public static void validar(Cliente cliente) throws IllegalArgumentException {
        validarCpf(cliente.getCPF());

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio");
        }

        if (cliente.getSobrenome() == null || cliente.getSobrenome().trim().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome nao pode ser vazio");
        }
        
        if (cliente.getRG() == null || cliente.getRG().trim().isEmpty()) {
            throw new IllegalArgumentException("RG nao pode ser vazio");
        }

        if (cliente.getCPF() == null || cliente.getCPF().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF nao pode ser vazio");
        }
        
        if (cliente.getEndereco() == null || cliente.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereco nao pode ser vazio");
        }
    }

    public static void validarCpf(String cpf) throws IllegalArgumentException {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF nao pode ser vazio");
        }
        
        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 digitos numericos");
        }
    }
}
