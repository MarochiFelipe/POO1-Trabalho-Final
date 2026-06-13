package app;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeFacil;
import model.Jogador;
import model.PerguntaMultiplaEscolha;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("          QUIZ TESTE");
        System.out.println("=================================");

        System.out.print("Digite seu nome: ");
        String nome = entrada.nextLine();

        Jogador jogador = new Jogador(nome);

        Dificuldade dificuldade = new DificuldadeFacil();

        ArrayList<String> alternativas = new ArrayList<>();
        alternativas.add("A) Acidez ou alcalinidade do solo");
        alternativas.add("B) Quantidade de areia no solo");
        alternativas.add("C) Cor do solo");
        alternativas.add("D) Profundidade do solo");

        PerguntaMultiplaEscolha pergunta = new PerguntaMultiplaEscolha(
                "O que o pH do solo indica?",
                "Agronomia",
                "Solos",
                dificuldade,
                "A",
                alternativas
        );

        System.out.println();
        System.out.println(pergunta);

        System.out.print("Digite sua resposta: ");
        String resposta = entrada.nextLine();

        System.out.print("Quantos segundos você demorou para responder? ");
        int segundosUsados = entrada.nextInt();

        int pontos = pergunta.calcularPontuacao(resposta, segundosUsados);

        if (pontos > 0) {
            jogador.adicionarPontuacao(pontos);
            jogador.registrarAcerto(pergunta.getEnunciado());

            System.out.println();
            System.out.println("Resposta correta!");
            System.out.println("Você ganhou " + pontos + " pontos.");
        } else {
            jogador.registrarErro(pergunta.getEnunciado());

            System.out.println();
            System.out.println("Resposta errada!");
            System.out.println("Resposta correta: " + pergunta.getRespostaCorreta());
        }

        System.out.println();
        System.out.println("===== RESULTADO DO JOGADOR =====");
        System.out.println(jogador);

        System.out.println();
        System.out.println("===== HISTÓRICO =====");

        for (String registro : jogador.getHistoricoRespostas()) {
            System.out.println(registro);
        }

        entrada.close();
    }
}