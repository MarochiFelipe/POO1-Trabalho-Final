package modo;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeDificil;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;

import java.util.Stack;

public class ModoProgressivo extends ModoJogo {

    private Stack<String> vidas;
    private int punicaoErro;
    private int punicaoTempo;
    private int bonusSequencia;

    public ModoProgressivo() {
        super("Modo Progressivo");

        this.vidas = new Stack<>();
        this.punicaoErro = 10;
        this.punicaoTempo = 15;
        this.bonusSequencia = 5;

        vidas.push("Vida 1");
        vidas.push("Vida 2");
        vidas.push("Vida 3");
    }

    @Override
    public Dificuldade escolherDificuldadeInicial() {
        return new DificuldadeFacil();
    }

    public void perderVida() {
        if (!vidas.isEmpty()) {
            vidas.pop();
        }
    }

    public boolean acabouAsVidas() {
        return vidas.isEmpty();
    }

    public int getQuantidadeVidas() {
        return vidas.size();
    }

    public int getPunicaoErro() {
        return punicaoErro;
    }

    public int getPunicaoTempo() {
        return punicaoTempo;
    }

    public int calcularBonusSequencia(int acertosSeguidos) {
        if (acertosSeguidos > 3) {
            return bonusSequencia;
        }

        return 0;
    }

    public Dificuldade subirDificuldade(Dificuldade dificuldadeAtual) {
        if (dificuldadeAtual instanceof DificuldadeFacil) {
            return new DificuldadeMedia();
        }

        if (dificuldadeAtual instanceof DificuldadeMedia) {
            return new DificuldadeDificil();
        }

        return dificuldadeAtual;
    }

    public Dificuldade descerDificuldade(Dificuldade dificuldadeAtual) {
        if (dificuldadeAtual instanceof DificuldadeDificil) {
            return new DificuldadeMedia();
        }

        if (dificuldadeAtual instanceof DificuldadeMedia) {
            return new DificuldadeFacil();
        }

        return dificuldadeAtual;
    }
}