package ranking;

import model.Jogador;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ranking {

    private HashMap<String, Integer> rankingProgressivo;
    private HashMap<String, Integer> rankingRapido;

    public Ranking() {
        this.rankingProgressivo = new HashMap<>();
        this.rankingRapido = new HashMap<>();
    }

    public void adicionarJogador(Jogador jogador, ModoJogo modo) {
        if (modo instanceof ModoProgressivo) {
            adicionarMelhorPontuacao(rankingProgressivo, jogador.getNome(), jogador.getPontuacao());
        } else if (modo instanceof ModoRapido) {
            adicionarMelhorPontuacao(rankingRapido, jogador.getNome(), jogador.getPontuacao());
        }
    }

    private void adicionarMelhorPontuacao(HashMap<String, Integer> ranking, String nome, int pontuacao) {
        if (!ranking.containsKey(nome)) {
            ranking.put(nome, pontuacao);
        } else {
            int pontuacaoAntiga = ranking.get(nome);

            if (pontuacao > pontuacaoAntiga) {
                ranking.put(nome, pontuacao);
            }
        }
    }

    public void exibirRankingProgressivo() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("    RANKING - MODO PROGRESSIVO");
        System.out.println("=================================");
        exibirMapaOrdenado(rankingProgressivo);
    }

    public void exibirRankingRapido() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("       RANKING - MODO RÁPIDO");
        System.out.println("=================================");
        exibirMapaOrdenado(rankingRapido);
    }

    private void exibirMapaOrdenado(HashMap<String, Integer> mapa) {
        if (mapa.isEmpty()) {
            System.out.println("O ranking deste modo ainda está vazio.");
            System.out.println("=================================");
            return;
        }

        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());
        listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        int posicao = 1;

        for (Map.Entry<String, Integer> entrada : listaOrdenada) {
            System.out.println(posicao + "º Lugar: " + entrada.getKey() + " - " + entrada.getValue() + " pontos");
            posicao++;
        }

        System.out.println("=================================");
    }

    public HashMap<String, Integer> getRankingProgressivo() {
        return rankingProgressivo;
    }

    public HashMap<String, Integer> getRankingRapido() {
        return rankingRapido;
    }

    public void carregarRankingProgressivo(HashMap<String, Integer> dados) {
        this.rankingProgressivo.putAll(dados);
    }

    public void carregarRankingRapido(HashMap<String, Integer> dados) {
        this.rankingRapido.putAll(dados);
    }
}