package requests;

import models.Veiculo;

public class VeiculoRequest {
    public static Veiculo validar(Veiculo veiculo) throws IllegalArgumentException {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia.");
        }
        if (veiculo.getMarca() == null) {
            throw new IllegalArgumentException("Marca não pode ser nula.");
        }

        if (veiculo.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria não pode ser nula.");
        }

        if (veiculo.getano() <= 1900 || veiculo.getano() > 2100) {
            throw new IllegalArgumentException("Ano inválido.");
        }

        if (veiculo.getValorParaVenda() <= 0) {
            throw new IllegalArgumentException("Valor para venda deve ser maior que zero.");
        }

        return veiculo;
    }


    public static void validarPlaca(String placa) throws IllegalArgumentException {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia.");
        }
        
        if (!placa.matches("[A-Z]{3}-[0-9]{4}")) {
            throw new IllegalArgumentException("Placa deve estar no formato AAA-0000.");
        }
    }
}