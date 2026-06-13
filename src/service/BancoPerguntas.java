package service;

import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import dificuldade.DificuldadeDificil;
import model.Pergunta;
import model.PerguntaMultiplaEscolha;
import model.PerguntaVerdadeiroFalso;

import java.util.ArrayList;

public class BancoPerguntas {

    public ArrayList<Pergunta> criarPerguntas() {

        ArrayList<Pergunta> perguntas = new ArrayList<>();

        ArrayList<String> alternativas1 = new ArrayList<>();
        alternativas1.add("A) Acidez ou alcalinidade do solo");
        alternativas1.add("B) Quantidade de areia no solo");
        alternativas1.add("C) Cor do solo");
        alternativas1.add("D) Profundidade do solo");

        perguntas.add(new PerguntaMultiplaEscolha("O que o pH do solo indica?", "Agronomia", "Solos", new DificuldadeFacil(), "A", alternativas1));

        perguntas.add(new PerguntaVerdadeiroFalso("O solo pode ter diferentes níveis de acidez.", "Agronomia", "Solos", new DificuldadeFacil(), "V"));

        ArrayList<String> alternativas2 = new ArrayList<>();
        alternativas2.add("A) Java");
        alternativas2.add("B) HTML");
        alternativas2.add("C) CSS");
        alternativas2.add("D) XML");

        perguntas.add(new PerguntaMultiplaEscolha("Qual dessas opções é uma linguagem de programação orientada a obejtos?", "Tecnologia", "Programção", new DificuldadeMedia(), "A", alternativas2));

        perguntas.add(new PerguntaVerdadeiroFalso("Em Java, uma classe pode herdar de várias classes ao mesmo tempo.", "Tecnologia", "Programação", new DificuldadeMedia(), "F"));

        ArrayList<String> alternativas3 = new ArrayList<>();
        alternativas3.add("A) Encapsulamento");
        alternativas3.add("B) Fotossíntese");
        alternativas3.add("C) Evaporação");
        alternativas3.add("D) Condensação");

        perguntas.add(new PerguntaMultiplaEscolha("Qual conceito de POO protege os atributos de uma classe?", "Tecnologia", "POO", new DificuldadeDificil(), "A", alternativas3));

        perguntas.add(new PerguntaVerdadeiroFalso("Uma interface em Java pode definir métodos que serão implementados por classes.", "Tecnologia", "POO", new DificuldadeFacil(), "V"));

        return perguntas;

    };

}
