package excecoes;

public class ArquivoPerguntasException extends Exception {

    public ArquivoPerguntasException(String mensagem) {
        super(mensagem);
    }

    public ArquivoPerguntasException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}