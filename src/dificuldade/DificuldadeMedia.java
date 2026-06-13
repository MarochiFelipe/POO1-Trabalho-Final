package dificuldade;

public class DificuldadeMedia extends Dificuldade {
    public DificuldadeMedia() {
        super("Media", 30, 20, 2);
    }

    @Override
    protected int calcularBonus(int segundosUsados){
        if (segundosUsados <= 10){
            return 5;
        }

        return 0;

    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus: +5 se responder em at´10 segundos";
    }

}

