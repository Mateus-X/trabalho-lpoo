package interfaces;

import models.Locacao;

public interface ILocacaoDAO {
    void salvar(Locacao locacao, String placaVeiculo) throws Exception;
    void concluir(String placaVeiculo) throws Exception;
    Locacao buscarLocacaoAtivaPorVeiculo(String placaVeiculo) throws Exception;
    Locacao buscarLocacaoAtivaPorCliente(String cpfCliente) throws Exception;
}