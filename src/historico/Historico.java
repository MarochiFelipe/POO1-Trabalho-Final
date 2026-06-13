package historico;

import model.Jogador;
import modo.ModoJogo;

import java.util.ArrayList;

public class Historico {

    private ArrayList<String> registros;

    public Historico() {
        this.registros = new ArrayList<>();
    }

    public void adicionarPartida(Jogador jogador, ModoJogo modo) {
        String linhaRegistro = "Jogador: " + jogador.getNome() +
                " | Modo: " + modo.getNome() +
                " | Pontuação: " + jogador.getPontuacao() +
                " | Acertos: " + jogador.getAcertos() +
                " | Erros: " + jogador.getErros();

        registros.add(linhaRegistro);
    }

    public void exibirHistorico() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("      HISTÓRICO DE PARTIDAS");
        System.out.println("=================================");

        if (registros.isEmpty()) {
            System.out.println("Nenhuma partida registrada ainda.");
            System.out.println("=================================");
            return;
        }

        for (String registro : registros) {
            System.out.println(registro);
        }

        System.out.println("=================================");
    }

    public ArrayList<String> getRegistros() {
        return registros;
    }

    public void carregarDados(ArrayList<String> dadosDoArquivo) {
        this.registros.addAll(dadosDoArquivo);
    }
}