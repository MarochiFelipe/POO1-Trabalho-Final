package service;

import dificuldade.Dificuldade;
import model.Jogador;
import model.Pergunta;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;

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

    private void aplicarErroProgressivo(Pergunta pergunta, ModoProgressivo modoProgressivo, boolean erroPorTempo) {
        jogador.registrarErro(pergunta.getEnunciado());

        int pontosPerdidos;

        if (erroPorTempo) {
            pontosPerdidos = modoProgressivo.getPunicaoTempo();
        } else {
            pontosPerdidos = modoProgressivo.getPunicaoErro();
        }

        jogador.perderPontuacao(pontosPerdidos);
        modoProgressivo.perderVida();

        Dificuldade novaDificuldade = modoProgressivo.descerDificuldade(dificuldadeAtual);

        if (!novaDificuldade.getNome().equalsIgnoreCase(dificuldadeAtual.getNome())) {
            dificuldadeAtual = novaDificuldade;
            System.out.println("Você desceu para a dificuldade: " + dificuldadeAtual.getNome());
        } else {
            System.out.println("Você continua na dificuldade: " + dificuldadeAtual.getNome());
        }

        System.out.println("Punição: -" + pontosPerdidos + " pontos");
        System.out.println("Vidas restantes: " + modoProgressivo.getQuantidadeVidas());
    }

    private void iniciarModoRapido(Scanner entrada) {
        ModoRapido modoRapido = (ModoRapido) modoJogo;

        int acertosModoRapido = 0;
        boolean partidaAtiva = true;

        System.out.println();
        System.out.println("=================================");
        System.out.println("          MODO RÁPIDO");
        System.out.println("=================================");
        System.out.println("Dificuldade escolhida: " + modoRapido.getDificuldadeEscolhida());
        System.out.println("A partida termina no primeiro erro.");
        System.out.println("Limite de perguntas: " + modoRapido.getLimitePerguntas());

        long inicioPartida = System.currentTimeMillis();

        while (partidaAtiva && perguntasUsadas.size() < modoRapido.getLimitePerguntas()) {

            Pergunta pergunta = buscarPerguntaModoRapido(modoRapido);

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
