package dificuldade;

public abstract class Dificuldade {
    private String nome;
    private int pontosPorAcerto;
    private int nivel;
    private int tempoLimiteSegundos;

    public Dificuldade(String nome, int pontosPorAcerto, int nivel, int tempoLimiteSegundos) {
        this.nome = nome;
        this.pontosPorAcerto = pontosPorAcerto;
        this.nivel = nivel;
        this.tempoLimiteSegundos = tempoLimiteSegundos;
    }

    public int calcularPontuacao() {
        return pontosPorAcerto;
    }

    public String getNome() {
        return nome;
    }

    public int getPontosPorAcerto() {
        return pontosPorAcerto;
    }

    public int getNivel() {
        return nivel;
    }

    public int getTempoLimiteSegundos() {
        return tempoLimiteSegundos;
    }

    @Override
    public String toString() {
        return "Dificuldade: " + nome + " | Pontos por acerto: " + pontosPorAcerto + " | Tempo limite: " + tempoLimiteSegundos + " segundos";
    }
}
