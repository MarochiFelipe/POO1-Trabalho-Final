package dificuldade;

public class DificuldadeDificil extends Dificuldade{
    public DificuldadeDificil(){
        super("Difícil", 20, 30, 3);
    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus +10 se responder em até 7 segundos";
    }

}
