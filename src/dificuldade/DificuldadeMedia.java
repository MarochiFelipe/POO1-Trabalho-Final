package dificuldade;

public class DificuldadeMedia extends Dificuldade {
    public DificuldadeMedia() {
        super("Media", 20, 2, 30);
    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus: +5 se responder em at´10 segundos";
    }

}

