package interfaces;

import java.util.List;
import models.Veiculo;
import models.enums.Categoria;
import models.enums.Estado;
import models.enums.Marca;

public interface IVeiculoDAO {
    void salvar(Veiculo veiculo) throws Exception;
    void atualizar(Veiculo veiculo) throws Exception;
    Veiculo buscarPorPlaca(String placa) throws Exception;
    List<Veiculo> listarTodos() throws Exception;
    List<Veiculo> listarPorEstado(Estado estado) throws Exception;
    List<Veiculo> filtrar(String tipo, Marca marca, Categoria categoria) throws Exception;
}