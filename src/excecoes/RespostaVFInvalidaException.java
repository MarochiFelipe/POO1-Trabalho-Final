package excecoes;

public class RespostaVFInvalidaException extends Exception {

    // Construtor padrão que recebe uma mensagem customizada
    public RespostaVFInvalidaException(String mensagem) {
        super(mensagem);
    }
}