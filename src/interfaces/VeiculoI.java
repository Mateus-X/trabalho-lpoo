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
     * Cria uma instância de Locacao e armazena no atributo locacao.
     * Chama o método getValorDiariaLocacao para calcular o valor da locação.
     * @param dias Quantidade de dias da locação.
     * @param data Data de início da locação.
     * @param cliente Cliente que está locando o veículo.
     */
    void locar (int dias, Calendar data, Cliente cliente);

    /**
     * Muda estado para VENDIDO e não pode mais ser alugado.
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
     * Método que calcula um valor para venda.
     * Utilizar o seguinte cálculo: valorParaVenda = valorDeCompra - idadeVeiculoEmAnos*0,15*valorDeCompra
     * Se o resultado for menor do que 10% do valorDeCompra ou negativo, então varlorParaVenda = valorDeCompra*0,1
     * @return O valor calculado para venda do veículo.
     */
    double getValorParaVenda();

    /**
     * Método que será abstrato na classe Veiculo.
     * Retorna o valor da diária de locação específico para cada tipo de veículo.
     * @return Valor da diária de locação.
     */
    double getValorDiariaLocacao();
}