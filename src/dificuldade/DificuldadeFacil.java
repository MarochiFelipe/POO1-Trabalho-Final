package dificuldade;

public class DificuldadeFacil extends Dificuldade{
    public DificuldadeFacil() {
        super("Fácil", 40, 10, 1);
    }

    @Override
    protected int calcularBonus(int segundosUsados){
        if(segundosUsados <= 15){
            return 2;
        }

        return 0;

    }

    @Override
    public String toString(){
        return super.toString() + " || Bônus: +2 se responder em até 15 segundos;";
    }
}
