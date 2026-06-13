package app;

import historico.Historico;
import ranking.Ranking;
import service.GerenciadorArquivos;
import menutexto.MenuTexto;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        GerenciadorArquivos gerenciadorArquivos = new GerenciadorArquivos();
        Ranking ranking = new Ranking();
        Historico historico = new Historico();

        gerenciadorArquivos.carregarRanking(ranking);
        gerenciadorArquivos.carregarHistorico(historico);

        MenuTexto menuTexto = new MenuTexto(entrada, gerenciadorArquivos, ranking, historico);
        menuTexto.iniciar();

        entrada.close();
    }
}