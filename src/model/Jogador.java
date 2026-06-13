package model;

import java.util.ArrayList;

public class Jogador {

    private String nome;
    private int pontuacao;
    private int acertos;
    private int erros;
    private ArrayList<String> historicoRespostas;

    public Jogador(String nome){
        this.nome = nome;
        this.pontuacao = 0;
        this.acertos = 0;
        this.erros = 0;
        this.historicoRespostas = new ArrayList<>();
    }

    public void adicionarPontuacao(int pontos){
        this.pontuacao += pontos;
    }

    public void registrarAcerto(String pergunta){
        acertos++;
        historicoRespostas.add("Acertou: " + pergunta);
    }

    public void registrarErro(String pergunta){
        erros++;
        historicoRespostas.add("Errou: " + pergunta);
    }

    public String getNome(){
        return nome;
    }

    public int getPontuacao(){
        return pontuacao;
    }

    public int getAcertos(){
        return acertos;
    }

    public int getErros(){
        return erros;
    }

    public ArrayList<String> getHistoricoRespostas(){
        return historicoRespostas;
    }

    @Override
    public String toString(){
        return "Jogador: " + nome + "\nPontuação: " + pontuacao + "\nAcertos: " + acertos + "\nErros: " + erros;
    }

}
