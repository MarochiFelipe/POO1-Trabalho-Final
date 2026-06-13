package modo;

import dificuldade.Dificuldade;

public abstract class ModoJogo {

    private String nome;
    private String descricao;

    public ModoJogo(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }

    public abstract Dificuldade escolherDificuldadeInicial();

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString(){
        return nome + " - " +  descricao;
    }

}
