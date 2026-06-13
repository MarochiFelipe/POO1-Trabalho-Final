package model;

import dificuldade.Dificuldade;
import excecoes.RespostaVFInvalidaException;

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
    public void validarFormatoResposta(String respostaJogador) throws RespostaVFInvalidaException {
        String resp = respostaJogador.trim().toUpperCase();
        // Se não for V, F, VERDADEIRO ou FALSO, lança o erro!
        if (!resp.equals("V") && !resp.equals("F") && !resp.equals("VERDADEIRO") && !resp.equals("FALSO")) {
            throw new RespostaVFInvalidaException("Resposta inválida! Digite apenas 'V' para Verdadeiro ou 'F' para Falso.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nDigite V para verdadeiro ou F para falso.";
    }

}
