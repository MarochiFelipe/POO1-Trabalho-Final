package modo;

import dificuldade.Dificuldade;

public abstract class ModoJogo {

    private String nome;

    public ModoJogo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract Dificuldade escolherDificuldadeInicial();
}