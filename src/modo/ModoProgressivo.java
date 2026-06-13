package modo;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import dificuldade.DificuldadeDificil;

public class ModoProgressivo extends ModoJogo{

    public ModoProgressivo(){
        super("Modo progressivo", "O jogador começa no fácil e sobe de nível conforme acerta perguntas.");
    }

    @Override
    public Dificuldade escolherDificuldadeInicial(){
        return new DificuldadeFacil();
    }

    @Override
    public Dificuldade atualizarDificuldade(Dificuldade dificuldadeAtual, int acertosSeguidos, int errosSeguidos){

        int nivelAtual = dificuldadeAtual.getNivel();

        if (acertosSeguidos >= 3 && nivelAtual < 3){
            return criarDificuldadePorNivel(nivelAtual + 1);
        }

        if (errosSeguidos >= 2 && nivelAtual > 1){
            return criarDificuldadePorNivel(nivelAtual - 1);
        }

        return dificuldadeAtual;

    }

    @Override
    public int getTempoTotalDoModo() {
        return 0;
    }

    private Dificuldade criarDificuldadePorNivel(int nivel){
        if (nivel == 1){
            return new DificuldadeFacil();
        } else if (nivel == 2) {
            return new DificuldadeMedia();
        } else {
            return new DificuldadeDificil();
        }
    }

    @Override
    public String toString(){
        return super.toString() + "Nesse modo, a dificuldade muda durante a partida";
    }

}
