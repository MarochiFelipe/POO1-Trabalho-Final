package model;

import dificuldade.Dificuldade;
import interfaces.Validavel;

public abstract class Pergunta implements Validavel {

    private String enunciado;
    private String area;
    private String subarea;
    private Dificuldade dificuldade;
    private String respostaCorreta;

    public Pergunta (String enunciado, String area, String subarea, Dificuldade dificuldade, String respostaCorreta) {
        this.enunciado = enunciado;
        this.area = area;
        this.subarea = subarea;
        this.dificuldade = dificuldade;
        this.respostaCorreta = respostaCorreta;
    }

    public int calcularPontuacao(String respostaJogador){
        if(validarResposta(respostaJogador)){
            return dificuldade.calcularPontuacao();
        }

        return 0;

    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getArea() {
        return area;
    }

    public String getSubarea() {
        return subarea;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    @Override
    public String toString(){
        return "Área: " + area + "\nSubárea: " + subarea + "\nDificuldade: " + dificuldade.getNome() + "\nTempo Limite: " + dificuldade.getTempoLimiteSegundos() + "\nPergunta: " + enunciado;
    }

}
