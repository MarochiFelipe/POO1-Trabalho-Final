package modo;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import dificuldade.DificuldadeDificil;

public class ModoRapido extends ModoJogo{

    private String dificuldadeEscolhida;
    private int limitePerguntas;

    public ModoRapido(){
        super("Modo Rápido", "O jogador escolhe uma dificuldade e responde até errar uma pergunta.");
        this.dificuldadeEscolhida = dificuldadeEscolhida;
        this.limitePerguntas = 5;
    }

    @Override
    public Dificuldade escolherDificuldadeInicial(){
        if(dificuldadeEscolhida.equalsIgnoreCase("facil")){
            return new DificuldadeFacil();
        }

        if (dificuldadeEscolhida.equalsIgnoreCase("media")){
            return new DificuldadeMedia();
        }

        if (dificuldadeEscolhida.equalsIgnoreCase("dificil")){
            return new DificuldadeDificil();
        }

        return new DificuldadeFacil();

    }

    public String getDificuldadeEscolhida(){
        return dificuldadeEscolhida;
    }

    public int getLimitePerguntas(){
        return limitePerguntas;
    }

    public boolean isAleatorio(){
        return dificuldadeEscolhida.equalsIgnoreCase("aleatorio");
    }

    @Override
    public String toString() {
        return super.toString() + "\nDificuldade escolhida: " + dificuldadeEscolhida + "\nLimite de perguntas: " + limitePerguntas;
    }

}
