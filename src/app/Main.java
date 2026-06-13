package app;

import dificuldade.Dificuldade;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("       TESTE DO QUIZ");
        System.out.println("=================================");

        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Modo Progressivo");
        System.out.println("2 - Modo Rápido");
        System.out.print("Opção: ");

        int opcao = lerInteiro(entrada);

        ModoJogo modoEscolhido;

        if (opcao == 2) {
            modoEscolhido = new ModoRapido();
        } else {
            modoEscolhido = new ModoProgressivo();
        }

        Dificuldade dificuldadeAtual = modoEscolhido.escolherDificuldadeInicial();

        int pontuacaoTotal = 0;
        int acertosSeguidos = 0;
        int errosSeguidos = 0;

        System.out.println();
        System.out.println("Modo escolhido:");
        System.out.println(modoEscolhido);

        System.out.println();
        System.out.println("Dificuldade inicial:");
        System.out.println(dificuldadeAtual);

        for (int rodada = 1; rodada <= 5; rodada++) {

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Rodada " + rodada);
            System.out.println(dificuldadeAtual);

            System.out.print("O jogador acertou? Digite 1 para SIM ou 0 para NÃO: ");
            int acertou = lerInteiro(entrada);

            if (acertou == 1) {
                System.out.print("Quantos segundos o jogador demorou para responder? ");
                int segundosUsados = lerInteiro(entrada);

                int pontosGanhos = dificuldadeAtual.calcularPontuacao(segundosUsados);

                pontuacaoTotal += pontosGanhos;
                acertosSeguidos++;
                errosSeguidos = 0;

                System.out.println("Resposta correta!");
                System.out.println("Pontos ganhos: " + pontosGanhos);

            } else {
                errosSeguidos++;
                acertosSeguidos = 0;

                System.out.println("Resposta errada!");
                System.out.println("Nenhum ponto foi somado.");
            }

            Dificuldade novaDificuldade = modoEscolhido.atualizarDificuldade(
                    dificuldadeAtual,
                    acertosSeguidos,
                    errosSeguidos
            );

            if (!novaDificuldade.getNome().equals(dificuldadeAtual.getNome())) {
                System.out.println("A dificuldade mudou para:");
                System.out.println(novaDificuldade);
            }

            dificuldadeAtual = novaDificuldade;

            System.out.println("Pontuação atual: " + pontuacaoTotal);
        }

        System.out.println();
        System.out.println("=================================");
        System.out.println("Fim do teste!");
        System.out.println("Pontuação final: " + pontuacaoTotal);
        System.out.println("=================================");

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