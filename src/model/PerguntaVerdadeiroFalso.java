package model;

import dificuldade.Dificuldade;

public class PerguntaVerdadeiroFalso extends Pergunta{

    public PerguntaVerdadeiroFalso(String enunciado, String area, String subarea, Dificuldade dificuldade, String respostaCorreta) {
        super(enunciado, area, subarea, dificuldade, respostaCorreta);
    }

    @Override
    public boolean validarResposta(String respostaJogador) {
        String respostaFormatada = formatarResposta(respostaJogador);
        String respostaCertaFormatada = formatarResposta(getRespostaCorreta());

        return respostaFormatada.equals(respostaCertaFormatada);
    }

    private String formatarResposta(String resposta) {
        resposta = resposta.trim();

        if(resposta.equalsIgnoreCase("verdadeiro")){
            return "V";
        }

        if(resposta.equalsIgnoreCase("falso")){
            return "F";
        }

        return resposta.toUpperCase();

    }

    @Override
    public String toString() {
        return super.toString() + "\nDigite V para verdadeiro ou F para falso.";
    }

}
