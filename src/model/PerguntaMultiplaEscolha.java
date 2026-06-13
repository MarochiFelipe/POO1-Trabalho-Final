package model;

import dificuldade.Dificuldade;
import java.util.ArrayList;

public class PerguntaMultiplaEscolha extends Pergunta{

    private ArrayList<String> alternativas;

    public PerguntaMultiplaEscolha(String enunciado, String area, String subarea, Dificuldade dificuldade, String respostaCorreta, ArrayList<String> alternativas){
        super(enunciado, area, subarea, dificuldade, respostaCorreta);
        this.alternativas = alternativas;
    }

    @Override
    public boolean validarResposta(String respostaJogador) {
        return respostaJogador.equalsIgnoreCase(getRespostaCorreta());
    }

    public ArrayList<String> getAlternativas(){
        return alternativas;
    }

    @Override
    public String toString(){
        String texto = super.toString() + "\n";

        for(String alternativa: alternativas){
            texto += alternativa + "\n";
        }

        return texto;

    }

}
