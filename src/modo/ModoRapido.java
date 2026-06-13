package modo;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeMedia;

public class ModoRapido extends ModoJogo{

    public ModoRapido(){
        super("Modo Rápido", "O jogador tenta responder o máximo de perguntas em um tempo limitado");
    }

    @Override
    public Dificuldade escolherDificuldadeInicial(){
        return new DificuldadeMedia();
    }

    @Override
    public Dificuldade atualizarDificuldade(Dificuldade dificuldadeAtual, int acertosSeguidos, int errosSeguidos) {
        return dificuldadeAtual;
    }

    @Override
    public int getTempoTotalDoModo() {
        return 90;
    }

    @Override
    public String toString() {
        return super.toString() + " Tempo total: " + getTempoTotalDoModo() + " segundos.";
    }

}
