package service;

import dificuldade.Dificuldade;
import model.Jogador;
import model.Pergunta;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Quiz {

    private Jogador jogador;
    private ArrayList<Pergunta> perguntas;
    private ModoJogo modoJogo;
    private Dificuldade dificuldadeAtual;
    private HashSet<Pergunta> perguntasUsadas;

    public Quiz(Jogador jogador, ArrayList<Pergunta> perguntas, ModoJogo modoJogo) {
        this.jogador = jogador;
        this.perguntas = perguntas;
        this.modoJogo = modoJogo;
        this.dificuldadeAtual = modoJogo.escolherDificuldadeInicial();
        this.perguntasUsadas = new HashSet<>();
    }

    public void iniciar(Scanner entrada) {
        if (modoJogo instanceof ModoProgressivo) {
            iniciarModoProgressivo(entrada);
        } else if (modoJogo instanceof ModoRapido) {
            iniciarModoRapido(entrada);
        }
    }

    private void iniciarModoProgressivo(Scanner entrada) {
        ModoProgressivo modoProgressivo = (ModoProgressivo) modoJogo;

        int acertosSeguidos = 0;
        int acertosParaSubir = 0;
        boolean partidaAtiva = true;

        System.out.println();
        System.out.println("=================================");
        System.out.println("        MODO PROGRESSIVO");
        System.out.println("=================================");
        System.out.println(jogador.getNome() + ", você começou na dificuldade " + dificuldadeAtual.getNome() + ".");
        System.out.println("Você possui " + modoProgressivo.getQuantidadeVidas() + " vidas.");

        while (partidaAtiva) {

            Pergunta pergunta = buscarPerguntaPorDificuldade(dificuldadeAtual);

            if (pergunta == null) {
                System.out.println();
                System.out.println("Não há mais perguntas disponíveis na dificuldade " + dificuldadeAtual.getNome() + ".");
                break;
            }

            perguntasUsadas.add(pergunta);

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Dificuldade atual: " + dificuldadeAtual.getNome());
            System.out.println("Vidas: " + modoProgressivo.getQuantidadeVidas());
            System.out.println("Pontuação atual: " + jogador.getPontuacao());
            System.out.println("---------------------------------");
            System.out.println(pergunta);

            long inicioPergunta = System.currentTimeMillis();

            System.out.print("Digite sua resposta: ");
            String resposta = entrada.nextLine();

            long fimPergunta = System.currentTimeMillis();
            long tempoUsado = (fimPergunta - inicioPergunta) / 1000;

            boolean passouDoTempo = tempoUsado > dificuldadeAtual.getTempoLimiteSegundos();

            if (passouDoTempo) {
                System.out.println();
                System.out.println("Tempo esgotado! Você respondeu em " + tempoUsado + " segundos.");
                aplicarErroProgressivo(pergunta, modoProgressivo, true);

                acertosSeguidos = 0;
                acertosParaSubir = 0;

            } else if (pergunta.validarResposta(resposta)) {
                int pontos = pergunta.calcularPontuacao(resposta);

                acertosSeguidos++;
                acertosParaSubir++;

                int bonus = modoProgressivo.calcularBonusSequencia(acertosSeguidos);
                int pontosTotais = pontos + bonus;

                jogador.adicionarPontuacao(pontosTotais);
                jogador.registrarAcerto(pergunta.getEnunciado());

                System.out.println("Resposta correta!");
                System.out.println("Tempo usado: " + tempoUsado + " segundos");
                System.out.println("Pontos ganhos: " + pontos);

                if (bonus > 0) {
                    System.out.println("Bônus de sequência: +" + bonus);
                }

                if (acertosParaSubir == 3) {
                    Dificuldade novaDificuldade = modoProgressivo.subirDificuldade(dificuldadeAtual);

                    if (!novaDificuldade.getNome().equalsIgnoreCase(dificuldadeAtual.getNome())) {
                        dificuldadeAtual = novaDificuldade;
                        System.out.println("Você subiu para a dificuldade: " + dificuldadeAtual.getNome());
                    }

                    acertosParaSubir = 0;
                }

            } else {
                System.out.println("Resposta errada!");
                System.out.println("Resposta correta: " + pergunta.getRespostaCorreta());

                aplicarErroProgressivo(pergunta, modoProgressivo, false);

                acertosSeguidos = 0;
                acertosParaSubir = 0;
            }

            if (modoProgressivo.acabouAsVidas()) {
                System.out.println();
                System.out.println("Suas vidas acabaram!");
                partidaAtiva = false;
            }
        }

        mostrarResultadoFinal();
    }

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

            if (pergunta == null) {
                System.out.println();
                System.out.println("Não há mais perguntas disponíveis para esse modo.");
                break;
            }

            perguntasUsadas.add(pergunta);

            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("Pergunta " + (acertosModoRapido + 1) + " de " + modoRapido.getLimitePerguntas());
            System.out.println("Pergunta valendo: " + pergunta.getDificuldade().getPontosPorAcerto() + " pontos");
            System.out.println("---------------------------------");
            System.out.println(pergunta);

            System.out.print("Digite sua resposta: ");
            String resposta = entrada.nextLine();

            if (pergunta.validarResposta(resposta)) {
                int pontos = pergunta.calcularPontuacao(resposta);

                jogador.adicionarPontuacao(pontos);
                jogador.registrarAcerto(pergunta.getEnunciado());
                acertosModoRapido++;

                System.out.println("Resposta correta!");
                System.out.println("Pontos ganhos: " + pontos);
                System.out.println("Acertos no modo rápido: " + acertosModoRapido);

            } else {
                jogador.registrarErro(pergunta.getEnunciado());

                System.out.println("Resposta errada!");
                System.out.println("Resposta correta: " + pergunta.getRespostaCorreta());
                System.out.println("Fim do modo rápido.");

                partidaAtiva = false;
            }
        }

        long fimPartida = System.currentTimeMillis();
        long tempoTotal = (fimPartida - inicioPartida) / 1000;

        int bonusRapidez = calcularBonusRapidez(acertosModoRapido, tempoTotal);
        jogador.adicionarPontuacao(bonusRapidez);

        System.out.println();
        System.out.println("=================================");
        System.out.println("      RESULTADO DO MODO RÁPIDO");
        System.out.println("=================================");
        System.out.println("Jogador: " + jogador.getNome());
        System.out.println("Acertos: " + acertosModoRapido);
        System.out.println("Tempo total: " + tempoTotal + " segundos");
        System.out.println("Bônus por rapidez: +" + bonusRapidez);
        System.out.println("Pontuação final: " + jogador.getPontuacao());

        mostrarResultadoFinal();
    }

    private int calcularBonusRapidez(int acertos, long tempoTotalSegundos) {
        if (acertos == 0) {
            return 0;
        }

        if (tempoTotalSegundos <= 0) {
            tempoTotalSegundos = 1;
        }

        return (int) ((acertos * 600) / tempoTotalSegundos);
    }

    private Pergunta buscarPerguntaPorDificuldade(Dificuldade dificuldade) {
        ArrayList<Pergunta> perguntasDisponiveis = new ArrayList<>();

        for (Pergunta pergunta : perguntas) {
            boolean mesmaDificuldade = pergunta.getDificuldade().getNome()
                    .equalsIgnoreCase(dificuldade.getNome());

            boolean perguntaNaoUsada = !perguntasUsadas.contains(pergunta);

            if (mesmaDificuldade && perguntaNaoUsada) {
                perguntasDisponiveis.add(pergunta);
            }
        }

        if (perguntasDisponiveis.isEmpty()) {
            return null;
        }

        Collections.shuffle(perguntasDisponiveis);

        return perguntasDisponiveis.get(0);
    }

    private Pergunta buscarPerguntaModoRapido(ModoRapido modoRapido) {
        ArrayList<Pergunta> perguntasDisponiveis = new ArrayList<>();

        for (Pergunta pergunta : perguntas) {
            boolean perguntaNaoUsada = !perguntasUsadas.contains(pergunta);

            if (!perguntaNaoUsada) {
                continue;
            }

            if (modoRapido.isAleatorio()) {
                perguntasDisponiveis.add(pergunta);
            } else {
                boolean mesmaDificuldade = pergunta.getDificuldade().getNome()
                        .equalsIgnoreCase(converterNomeDificuldade(modoRapido.getDificuldadeEscolhida()));

                if (mesmaDificuldade) {
                    perguntasDisponiveis.add(pergunta);
                }
            }
        }

        if (perguntasDisponiveis.isEmpty()) {
            return null;
        }

        Collections.shuffle(perguntasDisponiveis);

        return perguntasDisponiveis.get(0);
    }

    private String converterNomeDificuldade(String dificuldade) {
        if (dificuldade.equalsIgnoreCase("facil")) {
            return "Fácil";
        }

        if (dificuldade.equalsIgnoreCase("media")) {
            return "Média";
        }

        if (dificuldade.equalsIgnoreCase("dificil")) {
            return "Difícil";
        }

        return dificuldade;
    }

    private void mostrarResultadoFinal() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("          FIM DA PARTIDA");
        System.out.println("=================================");
        System.out.println(jogador);

        System.out.println();
        System.out.println("Histórico de respostas:");

        for (String registro : jogador.getHistoricoRespostas()) {
            System.out.println("- " + registro);
        }
    }
}