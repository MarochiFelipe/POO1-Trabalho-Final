package app;

import excecoes.ArquivoPerguntasException;
import model.Jogador;
import model.Pergunta;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;
import service.GerenciadorArquivos;
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

        int opcaoModo = lerInteiro(entrada);
        entrada.nextLine();

        ModoJogo modoEscolhido;

        if (opcaoModo == 2) {
            System.out.println();
            System.out.println("Escolha a dificuldade do Modo Rápido:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Média");
            System.out.println("3 - Difícil");
            System.out.println("4 - Aleatório");
            System.out.print("Opção: ");

            int opcaoDificuldade = lerInteiro(entrada);
            entrada.nextLine();

            String dificuldadeRapida;

            if (opcaoDificuldade == 1) {
                dificuldadeRapida = "facil";
            } else if (opcaoDificuldade == 2) {
                dificuldadeRapida = "media";
            } else if (opcaoDificuldade == 3) {
                dificuldadeRapida = "dificil";
            } else {
                dificuldadeRapida = "aleatorio";
            }

            modoEscolhido = new ModoRapido(dificuldadeRapida);
        } else {
            modoEscolhido = new ModoProgressivo();
        }

        GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
        ArrayList<Pergunta> perguntas;

        try {
            perguntas = gerenciadorArquivos.carregarPerguntas();
        } catch (ArquivoPerguntasException erro) {
            System.out.println("Erro ao carregar perguntas: " + erro.getMessage());
            entrada.close();
            return;
        }

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