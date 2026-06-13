package dificuldade;

public abstract class Dificuldade {
    private String nome;
    private int tempoPorPergunta;
    private int pontosPorAcerto;
    private int nivel;

    public Dificuldade(String nome, int tempoPorPergunta, int pontosPorAcerto, int nivel) {
        this.nome = nome;
        this.tempoPorPergunta = tempoPorPergunta;
        this.pontosPorAcerto = pontosPorAcerto;
        this.nivel = nivel;
    }

    public int calcularPontuacao(int segundosUsados){
        int bonus = calcularBonus(segundosUsados);
        return bonus + pontosPorAcerto;
    }

    protected abstract int calcularBonus(int segundosUsados);

    public String getNome() {
        return nome;
    }

    public int getTempoPorPergunta() {
        return tempoPorPergunta;
    }

    public int getPontosPorAcerto() {
        return pontosPorAcerto;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return "Dificuldade: " + nome + " || Tempo por pergunta: " + tempoPorPergunta + " segundos" + " || Pontos por acerto: " +  pontosPorAcerto;
    }
}
