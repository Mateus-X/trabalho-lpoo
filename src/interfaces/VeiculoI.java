package interfaces;

import models.Cliente;
import models.Locacao;
import models.enums.Categoria;
import models.enums.Estado;
import models.enums.Marca;

import java.util.Calendar;

public interface VeiculoI {

    /**
     * Muda estado para LOCADO.
     * Cria uma instancia de Locacao e armazena no atributo locacao.
     * Chama o metodo getValorDiariaLocacao para calcular o valor da locacao.
     * @param dias Quantidade de dias da locacao.
     * @param data Data de inicio da locacao.
     * @param cliente Cliente que esta locando o veiculo.
     */
    void locar (int dias, Calendar data, Cliente cliente);

    /**
     * Muda estado para VENDIDO e nao pode mais ser alugado.
     */
    void vender();

    /**
     * Muda estado para DISPONIVEL.
     */
    void devolver();

    Estado getEstado();

    Marca getMarca();

    Categoria getCategoria();

    Locacao getLocacao();

    String getPlaca();

    int getano();

    /**
     * Metodo que calcula um valor para venda.
     * Utilizar o seguinte calculo: valorParaVenda = valorDeCompra - idadeVeiculoEmAnos*0,15*valorDeCompra
     * Se o resultado for menor do que 10% do valorDeCompra ou negativo, entao varlorParaVenda = valorDeCompra*0,1
     * @return O valor calculado para venda do veiculo.
     */
    double getValorParaVenda();

    /**
     * Metodo que sera abstrato na classe Veiculo.
     * Retorna o valor da diaria de locacao especifico para cada tipo de veiculo.
     * @return Valor da diaria de locacao.
     */
    double getValorDiariaLocacao();
}