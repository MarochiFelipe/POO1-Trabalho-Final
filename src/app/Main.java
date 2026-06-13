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
import service.BancoPerguntas;

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

        BancoPerguntas bancoPerguntas = new BancoPerguntas();
        ArrayList<Pergunta> perguntas = bancoPerguntas.criarPerguntas();

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