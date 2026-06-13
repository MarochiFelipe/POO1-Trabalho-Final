package app;

import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import model.Jogador;
import model.Pergunta;
import model.PerguntaMultiplaEscolha;
import model.PerguntaVerdadeiroFalso;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;
import service.Quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("        QUIZ DO CONHECIMENTO");
        System.out.println("=================================");

        System.out.print("Digite seu nome: ");
        String nome = entrada.nextLine();

        Jogador jogador = new Jogador(nome);

        System.out.println();
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Modo Progressivo");
        System.out.println("2 - Modo Rápido");
        System.out.print("Opção: ");

        int opcao = lerInteiro(entrada);
        entrada.nextLine();

        ModoJogo modoEscolhido;

        if (opcao == 2) {
            modoEscolhido = new ModoRapido();
        } else {
            modoEscolhido = new ModoProgressivo();
        }

        ArrayList<Pergunta> perguntas = new ArrayList<>();

        ArrayList<String> alternativas1 = new ArrayList<>();
        alternativas1.add("A) Acidez ou alcalinidade do solo");
        alternativas1.add("B) Quantidade de areia no solo");
        alternativas1.add("C) Cor do solo");
        alternativas1.add("D) Profundidade do solo");

        PerguntaMultiplaEscolha pergunta1 = new PerguntaMultiplaEscolha(
                "O que o pH do solo indica?",
                "Agronomia",
                "Solos",
                new DificuldadeFacil(),
                "A",
                alternativas1
        );

        PerguntaVerdadeiroFalso pergunta2 = new PerguntaVerdadeiroFalso(
                "O solo pode ter diferentes níveis de acidez.",
                "Agronomia",
                "Solos",
                new DificuldadeFacil(),
                "V"
        );

        ArrayList<String> alternativas3 = new ArrayList<>();
        alternativas3.add("A) Java");
        alternativas3.add("B) HTML");
        alternativas3.add("C) CSS");
        alternativas3.add("D) SQL");

        PerguntaMultiplaEscolha pergunta3 = new PerguntaMultiplaEscolha(
                "Qual dessas opções é uma linguagem de programação orientada a objetos?",
                "Tecnologia",
                "Programação",
                new DificuldadeMedia(),
                "A",
                alternativas3
        );

        perguntas.add(pergunta1);
        perguntas.add(pergunta2);
        perguntas.add(pergunta3);

        Quiz quiz = new Quiz(jogador, perguntas, modoEscolhido);
        quiz.iniciar(entrada);

        entrada.close();
    }

    private static int lerInteiro(Scanner entrada) {
        while (!entrada.hasNextInt()) {
            System.out.print("Digite apenas números: ");
            entrada.next();
        }

        return entrada.nextInt();
    }
}