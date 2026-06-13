package service;

import dificuldade.DificuldadeDificil;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import model.Pergunta;
import model.PerguntaMultiplaEscolha;
import model.PerguntaVerdadeiroFalso;

import java.util.ArrayList;
import java.util.Collections;

public class BancoPerguntas {

    public ArrayList<Pergunta> criarPerguntas() {

        ArrayList<Pergunta> perguntas = new ArrayList<>();

        ArrayList<String> alternativas1 = new ArrayList<>();
        alternativas1.add("A) Acidez ou alcalinidade do solo");
        alternativas1.add("B) Quantidade de areia no solo");
        alternativas1.add("C) Cor do solo");
        alternativas1.add("D) Profundidade do solo");

        perguntas.add(new PerguntaMultiplaEscolha(
                "O que o pH do solo indica?",
                "Agronomia",
                "Solos",
                new DificuldadeFacil(),
                "A",
                alternativas1
        ));

        perguntas.add(new PerguntaVerdadeiroFalso(
                "As plantas realizam fotossíntese.",
                "Ciências",
                "Biologia",
                new DificuldadeFacil(),
                "V"
        ));

        ArrayList<String> alternativas2 = new ArrayList<>();
        alternativas2.add("A) 12");
        alternativas2.add("B) 15");
        alternativas2.add("C) 18");
        alternativas2.add("D) 20");

        perguntas.add(new PerguntaMultiplaEscolha(
                "Quanto é 8 + 7?",
                "Matemática",
                "Operações básicas",
                new DificuldadeFacil(),
                "B",
                alternativas2
        ));

        perguntas.add(new PerguntaVerdadeiroFalso(
                "Java é uma linguagem que pode ser usada com Programação Orientada a Objetos.",
                "Tecnologia",
                "Programação",
                new DificuldadeFacil(),
                "V"
        ));

        ArrayList<String> alternativas3 = new ArrayList<>();
        alternativas3.add("A) 1500");
        alternativas3.add("B) 1822");
        alternativas3.add("C) 1889");
        alternativas3.add("D) 1930");

        perguntas.add(new PerguntaMultiplaEscolha(
                "Em que ano ocorreu a Independência do Brasil?",
                "Humanas",
                "História do Brasil",
                new DificuldadeMedia(),
                "B",
                alternativas3
        ));

        perguntas.add(new PerguntaVerdadeiroFalso(
                "A latitude pode influenciar o clima de uma região.",
                "Geografia",
                "Climatologia",
                new DificuldadeMedia(),
                "V"
        ));

        ArrayList<String> alternativas4 = new ArrayList<>();
        alternativas4.add("A) Sugam a seiva das plantas");
        alternativas4.add("B) Fazem fotossíntese");
        alternativas4.add("C) Produzem sementes");
        alternativas4.add("D) Corrigem o pH do solo");

        perguntas.add(new PerguntaMultiplaEscolha(
                "Qual é uma característica comum dos pulgões nas plantas?",
                "Agronomia",
                "Entomologia",
                new DificuldadeMedia(),
                "A",
                alternativas4
        ));

        perguntas.add(new PerguntaVerdadeiroFalso(
                "Uma interface em Java pode ser instanciada diretamente com new.",
                "Tecnologia",
                "POO",
                new DificuldadeMedia(),
                "F"
        ));

        ArrayList<String> alternativas5 = new ArrayList<>();
        alternativas5.add("A) Direção angular medida a partir do norte");
        alternativas5.add("B) Tipo de adubo orgânico");
        alternativas5.add("C) Medida de acidez do solo");
        alternativas5.add("D) Tipo de inseto agrícola");

        perguntas.add(new PerguntaMultiplaEscolha(
                "Em topografia, o que é azimute?",
                "Agronomia",
                "Topografia",
                new DificuldadeDificil(),
                "A",
                alternativas5
        ));

        perguntas.add(new PerguntaVerdadeiroFalso(
                "Uma solução com pH abaixo de 7 é considerada ácida.",
                "Química",
                "Soluções",
                new DificuldadeDificil(),
                "V"
        ));

        ArrayList<String> alternativas6 = new ArrayList<>();
        alternativas6.add("A) A = b x h");
        alternativas6.add("B) A = lado x lado");
        alternativas6.add("C) A = π x r²");
        alternativas6.add("D) A = 2 x π x r");

        perguntas.add(new PerguntaMultiplaEscolha(
                "Qual é a fórmula da área do círculo?",
                "Matemática",
                "Geometria",
                new DificuldadeDificil(),
                "C",
                alternativas6
        ));

        ArrayList<String> alternativas7 = new ArrayList<>();
        alternativas7.add("A) Esconder arquivos do projeto");
        alternativas7.add("B) Criar apenas métodos privados");
        alternativas7.add("C) Apagar atributos automaticamente");
        alternativas7.add("D) Permitir que objetos diferentes respondam ao mesmo método de formas diferentes");

        perguntas.add(new PerguntaMultiplaEscolha(
                "O que é polimorfismo em Programação Orientada a Objetos?",
                "Tecnologia",
                "POO",
                new DificuldadeDificil(),
                "D",
                alternativas7
        ));

        Collections.shuffle(perguntas);

        return perguntas;
    }
}