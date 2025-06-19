package requests;

import models.Locacao;

public class LocacaoRequest {
    public static void validar(Locacao locacao) throws IllegalArgumentException {
        if (locacao.getdias() <= 0) {
            throw new IllegalArgumentException("Dias deve ser maior que zero");
        }

        if (locacao.getValor() <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        if (locacao.getData() == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }

        if (locacao.getCliente() == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
    }
    
}
