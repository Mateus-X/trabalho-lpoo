package requests;

public class DevolucaoRequest {

    public static void validarPlaca(String placa) throws IllegalArgumentException {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser vazia.");
        }
        
        if (!placa.matches("[A-Z]{3}-[0-9]{4}")) {
            throw new IllegalArgumentException("Placa deve estar no formato AAA-0000.");
        }
    }
}
