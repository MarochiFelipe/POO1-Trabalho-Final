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

    // Dois HashMaps separados para cumprir o requisito e dividir os modos
    private HashMap<String, Integer> rankingProgressivo;
    private HashMap<String, Integer> rankingRapido;

    public Ranking() {
        this.rankingProgressivo = new HashMap<>();
        this.rankingRapido = new HashMap<>();
    }

    /**
     * Adiciona o jogador no ranking correto baseado no modo de jogo jogado.
     */
    public void adicionarJogador(Jogador jogador, ModoJogo modo) {
        if (modo instanceof ModoProgressivo) {
            rankingProgressivo.put(jogador.getNome(), jogador.getPontuacao());
        } else if (modo instanceof ModoRapido) {
            rankingRapido.put(jogador.getNome(), jogador.getPontuacao());
        }
    }

    /**
     * Exibe o ranking do Modo Progressivo ordenado por pontuação.
     */
    public void exibirRankingProgressivo() {
        System.out.println("\n=================================");
        System.out.println("    RANKING - MODO PROGRESSIVO   ");
        System.out.println("=================================");
        exibirMapaOrdenado(rankingProgressivo);
    }

    /**
     * Exibe o ranking do Modo Rápido ordenado por pontuação.
     */
    public void exibirRankingRapido() {
        System.out.println("\n=================================");
        System.out.println("       RANKING - MODO RÁPIDO     ");
        System.out.println("=================================");
        exibirMapaOrdenado(rankingRapido);
    }

    /**
     * Método privado utilitário que reutiliza a lógica de ordenação para ambos os mapas.
     */
    private void exibirMapaOrdenado(HashMap<String, Integer> mapa) {
        if (mapa.isEmpty()) {
            System.out.println("O ranking deste modo ainda está vazio.");
            System.out.println("=================================");
            return;
        }

        // 1. Converte o HashMap em uma lista para podermos ordenar
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());

        // 2. Ordena de forma decrescente (maior pontuação primeiro)
        listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // 3. Imprime formatado
        int posicao = 1;
        for (Map.Entry<String, Integer> entrada : listaOrdenada) {
            System.out.println(posicao + "º Lugar: " + entrada.getKey() + " - " + entrada.getValue() + " pontos");
            posicao++;
        }
        System.out.println("=================================");
    }

    // --- MÉTODOS DE PONTE PARA O GERENCIADOR DE ARQUIVOS ---

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