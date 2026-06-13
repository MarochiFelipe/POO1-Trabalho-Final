package dificuldade;

public class DificuldadeDificil extends Dificuldade{
    public DificuldadeDificil(){
        super("Difícil", 20, 30, 3);
    }

    @Override
    protected int calcularBonus(int segundosUsados){
        if (segundosUsados <= 7){
            return 10;
        }

        return 0;

    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus +10 se responder em até 7 segundos";
    }

}
