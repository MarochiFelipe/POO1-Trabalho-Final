package modo;

import dificuldade.Dificuldade;
import dificuldade.DificuldadeFacil;
import dificuldade.DificuldadeMedia;
import dificuldade.DificuldadeDificil;

import java.util.Stack;

public class ModoProgressivo extends ModoJogo{

    private Stack<String> vidas;
    private int punicaoErro;
    private int punicaoTempo;
    private int bonusSequencia;

    public ModoProgressivo(){
        super("Modo progressivo", "O jogador começa no fácil, possui 3 vidas, perde pontos ao errar e ganha bônus por sequência.");

        this.vidas = new Stack<>();
        this.punicaoErro = 10;
        this.punicaoTempo = 15;
        this.bonusSequencia = 5;

        vidas.push("Vida 1");
        vidas.push("Vida 2");
        vidas.push("Vida 3");
    }

    @Override
    public Dificuldade escolherDificuldadeInicial(){
        return new DificuldadeFacil();
    }

    public void perderVida(){
        if (!vidas.isEmpty()){
            vidas.pop();
        }
    }

    public boolean acabouAsVidas(){
        return vidas.isEmpty();
    }

    public int getQuantidadeVidas(){
        return vidas.size();
    }

    public int getPunicaoErro(){
        return punicaoErro;
    }

    public int getPunicaoTempo(){
        return punicaoTempo;
    }

    public int calcularBonusSequencia(int acertosSeguidos){
        if (acertosSeguidos > 3){
            return bonusSequencia;
        }

        return 0;

    }

    public Dificuldade subirDificuldade(Dificuldade dificuldadeAtual){
        int nivel = dificuldadeAtual.getNivel();

        if(nivel == 1){
            return new DificuldadeMedia();
        }

        if(nivel == 2){
            return new DificuldadeDificil();
        }

        return dificuldadeAtual;

    }

    public Dificuldade descerDificuldade(Dificuldade dificuldadeAtual){
        int nivel = dificuldadeAtual.getNivel();

        if(nivel == 3){
            return new DificuldadeMedia();
        }

        if(nivel == 2){
            return new DificuldadeFacil();
        }

        return dificuldadeAtual;

    }

    @Override
    public String toString(){
        return super.toString() + "\nVidas iniciais: 3" + "\nPunição por erro: -" + punicaoErro + " pontos" + "\nPunição por tempo esgotado: -" + punicaoTempo + " pontos" + "\nBônus de sequência: +" + bonusSequencia + " pontos após 3 acertos seguidos";
    }

}
