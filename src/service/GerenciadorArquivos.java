package service;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeDificil;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import excecoes.AlternativaInvalidaException;
import excecoes.ArquivoPerguntasException;
import excecoes.PerguntaInvalidaException;
import excecoes.RespostaVFInvalidaException;
import model.Pergunta;
import model.PerguntaMultiplaEscolha;
import model.PerguntaVerdadeiroFalso;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class GerenciadorArquivos {

    public ArrayList<Pergunta> carregarPerguntas() throws ArquivoPerguntasException {
        String[] caminhosPossiveis = {
                "dados/perguntas.txt",
                "POO1-Trabalho-Final/dados/perguntas.txt",
                "src/dados/perguntas.txt"
        };

        for (String caminho : caminhosPossiveis) {
            Path caminhoArquivo = Paths.get(caminho);

            if (Files.exists(caminhoArquivo)) {
                return carregarPerguntas(caminho);
            }
        }

        throw new ArquivoPerguntasException("Arquivo perguntas.txt não encontrado. Verifique se ele está na pasta dados.");
    }

    public ArrayList<Pergunta> carregarPerguntas(String caminhoArquivo) throws ArquivoPerguntasException {
        ArrayList<Pergunta> perguntas = new ArrayList<>();

        try (BufferedReader leitor = Files.newBufferedReader(Paths.get(caminhoArquivo), StandardCharsets.UTF_8)) {

            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;

                linha = linha.trim();

                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                try {
                    Pergunta pergunta = criarPergunta(linha, numeroLinha);
                    perguntas.add(pergunta);
                } catch (PerguntaInvalidaException | AlternativaInvalidaException | RespostaVFInvalidaException erro) {
                    System.out.println("Pergunta ignorada: " + erro.getMessage());
                }
            }

        } catch (IOException erro) {
            throw new ArquivoPerguntasException("Erro ao ler o arquivo de perguntas: " + caminhoArquivo, erro);
        }

        if (perguntas.isEmpty()) {
            throw new ArquivoPerguntasException("Nenhuma pergunta válida foi carregada.");
        }

        Collections.shuffle(perguntas);
        return perguntas;
    }

    private Pergunta criarPergunta(String linha, int numeroLinha)
            throws PerguntaInvalidaException, AlternativaInvalidaException, RespostaVFInvalidaException {

        String[] partes = linha.split(";", -1);

        if (partes.length != 7) {
            throw new PerguntaInvalidaException("Linha " + numeroLinha + " está fora do formato correto.");
        }

        String tipo = partes[0].trim();
        String area = partes[1].trim();
        String subarea = partes[2].trim();
        String dificuldadeTexto = partes[3].trim();
        String enunciado = partes[4].trim();
        String alternativasTexto = partes[5].trim();
        String respostaCorreta = partes[6].trim();

        if (tipo.isEmpty() || area.isEmpty() || subarea.isEmpty() ||
                dificuldadeTexto.isEmpty() || enunciado.isEmpty() || respostaCorreta.isEmpty()) {
            throw new PerguntaInvalidaException("Linha " + numeroLinha + " possui campo vazio obrigatório.");
        }

        Dificuldade dificuldade = criarDificuldade(dificuldadeTexto);

        if (tipo.equalsIgnoreCase("MULTIPLA")) {
            return criarPerguntaMultipla(
                    numeroLinha,
                    enunciado,
                    area,
                    subarea,
                    dificuldade,
                    respostaCorreta,
                    alternativasTexto
            );
        }

        if (tipo.equalsIgnoreCase("VF")) {
            return criarPerguntaVerdadeiroFalso(
                    numeroLinha,
                    enunciado,
                    area,
                    subarea,
                    dificuldade,
                    respostaCorreta
            );
        }

        throw new PerguntaInvalidaException("Linha " + numeroLinha + " possui tipo inválido: " + tipo);
    }

    private PerguntaMultiplaEscolha criarPerguntaMultipla(int numeroLinha,
                                                          String enunciado,
                                                          String area,
                                                          String subarea,
                                                          Dificuldade dificuldade,
                                                          String respostaCorreta,
                                                          String alternativasTexto)
            throws AlternativaInvalidaException {

        String[] alternativasSeparadas = alternativasTexto.split("\\|");

        if (alternativasTexto.isEmpty()) {
            throw new AlternativaInvalidaException("Linha " + numeroLinha + " não possui alternativas.");
        }

        if (alternativasSeparadas.length != 4) {
            throw new AlternativaInvalidaException("Linha " + numeroLinha + " deve ter exatamente 4 alternativas.");
        }

        if (!respostaCorreta.equalsIgnoreCase("A") &&
                !respostaCorreta.equalsIgnoreCase("B") &&
                !respostaCorreta.equalsIgnoreCase("C") &&
                !respostaCorreta.equalsIgnoreCase("D")) {
            throw new AlternativaInvalidaException("Linha " + numeroLinha + " possui resposta inválida. Use A, B, C ou D.");
        }

        ArrayList<String> alternativas = new ArrayList<>();

        for (String alternativa : alternativasSeparadas) {
            alternativas.add(alternativa.trim());
        }

        return new PerguntaMultiplaEscolha(
                enunciado,
                area,
                subarea,
                dificuldade,
                respostaCorreta,
                alternativas
        );
    }

    private PerguntaVerdadeiroFalso criarPerguntaVerdadeiroFalso(int numeroLinha,
                                                                 String enunciado,
                                                                 String area,
                                                                 String subarea,
                                                                 Dificuldade dificuldade,
                                                                 String respostaCorreta)
            throws RespostaVFInvalidaException {

        if (!respostaCorreta.equalsIgnoreCase("V") &&
                !respostaCorreta.equalsIgnoreCase("F")) {
            throw new RespostaVFInvalidaException("Linha " + numeroLinha + " possui resposta inválida. Use V ou F.");
        }

        return new PerguntaVerdadeiroFalso(
                enunciado,
                area,
                subarea,
                dificuldade,
                respostaCorreta
        );
    }

    private Dificuldade criarDificuldade(String dificuldadeTexto) throws PerguntaInvalidaException {
        if (dificuldadeTexto.equalsIgnoreCase("FACIL")) {
            return new DificuldadeFacil();
        }

        if (dificuldadeTexto.equalsIgnoreCase("MEDIA")) {
            return new DificuldadeMedia();
        }

        if (dificuldadeTexto.equalsIgnoreCase("DIFICIL")) {
            return new DificuldadeDificil();
        }

        throw new PerguntaInvalidaException("Dificuldade inválida: " + dificuldadeTexto);
    }
}