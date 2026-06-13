package dificuldade;

public class DificuldadeFacil extends Dificuldade{
    public DificuldadeFacil() {
        super("Fácil", 10, 1, 40);
    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus: +2 se responder em até 15 segundos;";
    }
}
