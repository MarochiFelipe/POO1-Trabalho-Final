package excecoes;

public class AlternativaInvalidaException extends Exception {

    // Construtor padrão que recebe uma mensagem customizada
    public AlternativaInvalidaException(String mensagem) {
        super(mensagem);
    }
}