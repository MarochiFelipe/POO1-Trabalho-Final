package historico;

import model.Jogador;
import modo.ModoJogo;
import java.util.ArrayList;

public class Historico {

    // Estrutura de dados que armazenará o registro em texto de cada partida
    private ArrayList<String> registros;

    public Historico() {
        this.registros = new ArrayList<>();
    }

    /**
     * Método chamado sempre que uma partida terminar para gerar e guardar o log.
     * @param jogador O objeto do jogador com os dados da partida atual
     * @param modo O modo de jogo que foi jogado
     */
    public void adicionarPartida(Jogador jogador, ModoJogo modo) {
        // Cria uma linha de texto padronizada usando o String.format
        String linhaRegistro = String.format(
                "Jogador: %s | Modo: %s | Pontuação: %d",
                jogador.getNome(),
                modo.getNome(),
                jogador.getPontuacao()
        );

        // Guarda a linha na lista dentro da memória
        registros.add(linhaRegistro);
    }

    /**
     * Percorre a lista e exibe todas as partidas gravadas em ordem cronológica.
     */
    public void exibirHistorico() {
        System.out.println("\n=================================");
        System.out.println("      HISTÓRICO DE PARTIDAS      ");
        System.out.println("=================================");

        if (registros.isEmpty()) {
            System.out.println("Nenhuma partida registrada ainda.");
            System.out.println("=================================");
            return;
        }

        // Laço para imprimir linha por linha do histórico
        for (String registro : registros) {
            System.out.println(registro);
        }
        System.out.println("=================================");
    }

    // --- MÉTODOS DE PONTE PARA O GERENCIADOR DE ARQUIVOS ---

    // O Gerenciador de Arquivos chamará isto para obter as linhas e salvar no historico.txt
    public ArrayList<String> getRegistros() {
        return registros;
    }

    // O Gerenciador de Arquivos chamará isto para injetar o que leu do arquivo txt na memória
    public void carregarDados(ArrayList<String> dadosDoArquivo) {
        this.registros.addAll(dadosDoArquivo);
    }
}

