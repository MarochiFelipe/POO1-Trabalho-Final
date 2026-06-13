package service;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeDificil;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import excecoes.AlternativaInvalidaException;
import excecoes.ArquivoPerguntasException;
import excecoes.PerguntaInvalidaException;
import excecoes.RespostaVFInvalidaException;
import historico.Historico;
import model.Pergunta;
import model.PerguntaMultiplaEscolha;
import model.PerguntaVerdadeiroFalso;
import ranking.Ranking;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void salvarRanking(Ranking ranking) {
        salvarMapaRanking("ranking_progressivo.txt", ranking.getRankingProgressivo());
        salvarMapaRanking("ranking_rapido.txt", ranking.getRankingRapido());
    }

    public void carregarRanking(Ranking ranking) {
        ranking.carregarRankingProgressivo(carregarMapaRanking("ranking_progressivo.txt"));
        ranking.carregarRankingRapido(carregarMapaRanking("ranking_rapido.txt"));
    }

    private void salvarMapaRanking(String nomeArquivo, HashMap<String, Integer> mapa) {
        try {
            Path pastaDados = criarPastaDados();
            Path caminhoArquivo = pastaDados.resolve(nomeArquivo);

            ArrayList<String> linhas = new ArrayList<>();

            List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());
            listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            for (Map.Entry<String, Integer> entrada : listaOrdenada) {
                linhas.add(entrada.getKey() + ";" + entrada.getValue());
            }

            Files.write(caminhoArquivo, linhas, StandardCharsets.UTF_8);

            System.out.println("Arquivo salvo em: " + caminhoArquivo.toAbsolutePath());

        } catch (IOException erro) {
            System.out.println("Erro ao salvar ranking: " + erro.getMessage());
        }
    }

    private HashMap<String, Integer> carregarMapaRanking(String nomeArquivo) {
        HashMap<String, Integer> mapa = new HashMap<>();

        Path caminho = obterPastaDados().resolve(nomeArquivo);

        if (!Files.exists(caminho)) {
            return mapa;
        }

        try {
            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                String[] partes = linha.split(";");

                if (partes.length == 2) {
                    String nome = partes[0].trim();
                    int pontuacao = Integer.parseInt(partes[1].trim());
                    mapa.put(nome, pontuacao);
                }
            }

        } catch (IOException | NumberFormatException erro) {
            System.out.println("Erro ao carregar ranking: " + erro.getMessage());
        }

        return mapa;
    }

    public void salvarHistorico(Historico historico) {
        try {
            Path pastaDados = criarPastaDados();
            Path caminhoArquivo = pastaDados.resolve("historico.txt");

            Files.write(caminhoArquivo, historico.getRegistros(), StandardCharsets.UTF_8);

            System.out.println("Arquivo salvo em: " + caminhoArquivo.toAbsolutePath());

        } catch (IOException erro) {
            System.out.println("Erro ao salvar histórico: " + erro.getMessage());
        }
    }

    public void carregarHistorico(Historico historico) {
        Path caminho = obterPastaDados().resolve("historico.txt");

        if (!Files.exists(caminho)) {
            return;
        }

        try {
            ArrayList<String> linhas = new ArrayList<>(Files.readAllLines(caminho, StandardCharsets.UTF_8));
            historico.carregarDados(linhas);
        } catch (IOException erro) {
            System.out.println("Erro ao carregar histórico: " + erro.getMessage());
        }
    }

    private Path criarPastaDados() throws IOException {
        Path pasta = obterPastaDados();

        if (!Files.exists(pasta)) {
            Files.createDirectories(pasta);
        }

        return pasta;
    }

    private Path obterPastaDados() {
        Path pastaDoProjetoInterno = Paths.get("POO1-Trabalho-Final", "dados");

        if (Files.exists(Paths.get("POO1-Trabalho-Final"))) {
            return pastaDoProjetoInterno;
        }

        return Paths.get("dados");
    }
}