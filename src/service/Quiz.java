package service;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeFacil;
import model.Jogador;
import model.Pergunta;
import modo.ModoJogo;

import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {

    private Jogador jogador;
    private ArrayList<Pergunta> perguntas;
    private ModoJogo modoJogo;
    private Dificuldade dificuldadeAtual;
    private int acertosSeguidos;
    private int errosSeguidos;

    public Quiz(Jogador jogador, ArrayList<Pergunta> perguntas, ModoJogo modoJogo) {
        this.jogador = jogador;
        this.perguntas = perguntas;
        this.modoJogo = modoJogo;
        this.dificuldadeAtual = modoJogo.escolherDificuldadeInicial();
        this.acertosSeguidos = 0;
        this.errosSeguidos = 0;
    }

    public void iniciar(Scanner entrada){
        System.out.println();
        System.out.println("=================================");
        System.out.println("        INÍCIO DO QUIZ");
        System.out.println("=================================");
        System.out.println("Jogador: " + jogador.getNome());
        System.out.println("Modo escolhido:  " + modoJogo.getNome());
        System.out.println("Dificuldade inicial: " + dificuldadeAtual.getNome());

        if (modoJogo.getTempoTotalDoModo() > 0){
            System.out.println("Tempo total do modo: " + modoJogo.getTempoTotalDoModo() + " segundos");
        }

        if (perguntas.isEmpty()){
            System.out.println("Nenhuma pergunta foi encontrada");
            return;
        }

        ArrayList<Pergunta> perguntasUsadas = new ArrayList<>();
        int numeroPergunta = 1;

       while (perguntasUsadas.size() < perguntas.size()){

           Pergunta pergunta = buscarPerguntaPorDificuldade(perguntasUsadas);

           if(pergunta == null){
               break;
           }

           perguntasUsadas.add(pergunta);

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Pergunta " + numeroPergunta + " de " + perguntas.size());
            System.out.println("Dificuldade atual do modo: " + dificuldadeAtual.getNome());
            System.out.println("---------------------------------");
            System.out.println(pergunta);

            System.out.print("Digite sua resposta: ");
            String respostaJogador = entrada.nextLine();

            System.out.print("Quantos segundos você demorou para responder?");
            int segundosUsados = lerInteiro(entrada);
            entrada.nextLine();

            int pontosGanhos = pergunta.calcularPontuacao(respostaJogador, segundosUsados);

            if (pontosGanhos > 0){
                jogador.adicionarPontuacao(pontosGanhos);
                jogador.registrarAcerto(pergunta.getEnunciado());

                acertosSeguidos++;
                errosSeguidos = 0;

                System.out.println("Resposta correta!");
                System.out.println("Pontos ganhos: " +  pontosGanhos);

            } else {

                jogador.registrarErro(pergunta.getEnunciado());

                errosSeguidos++;
                acertosSeguidos = 0;

                System.out.println("Resposta incorreta!");
                System.out.println("Resposta correta: " + pergunta.getRespostaCorreta());

            }

            atualizarDificuldade();

            System.out.println("Pontuação atual: " + jogador.getPontuacao());

            numeroPergunta++;

        }

        mostrarResultadoFinal();

    }

    private void atualizarDificuldade(){

        Dificuldade novaDificuldade = modoJogo.atualizarDificuldade(dificuldadeAtual, acertosSeguidos, errosSeguidos);

        if (!novaDificuldade.getNome().equals(dificuldadeAtual.getNome())){
            dificuldadeAtual = novaDificuldade;
            acertosSeguidos = 0;
            errosSeguidos = 0;

            System.out.println("A dificuldade mudou para: " + dificuldadeAtual.getNome());

        }

    }

    private void mostrarResultadoFinal(){

        System.out.println();
        System.out.println("=================================");
        System.out.println("          FIM DO QUIZ");
        System.out.println("=================================");
        System.out.println(jogador);

        System.out.println();
        System.out.println("Histórico de respostas: ");

        for (String registro : jogador.getHistoricoRespostas()){
            System.out.println("- " + registro);
        }

    }

    private int lerInteiro(Scanner entrada){
        while (!entrada.hasNextInt()){
            System.out.println("Digite apenas números: ");
            entrada.next();
        }

        return entrada.nextInt();

    }

    private Pergunta buscarPerguntaPorDificuldade(ArrayList<Pergunta> perguntasUsadas){

        for (Pergunta pergunta : perguntas){
            boolean mesmaDificuldade = pergunta.getDificuldade().getNome().equalsIgnoreCase(dificuldadeAtual.getNome());

            boolean aindaNaoUsada = !perguntasUsadas.contains(pergunta);

            if(mesmaDificuldade && aindaNaoUsada){
                return pergunta;
            }

        }

        for (Pergunta pergunta : perguntas) {
            if (!perguntasUsadas.contains(pergunta)) {
                return pergunta;
            }
        }

        return null;

    }

}
