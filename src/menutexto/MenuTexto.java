package menutexto;

import excecoes.ArquivoPerguntasException;
import historico.Historico;
import model.Jogador;
import model.Pergunta;
import modo.ModoJogo;
import modo.ModoProgressivo;
import modo.ModoRapido;
import ranking.Ranking;
import service.GerenciadorArquivos;
import service.Quiz;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuTexto {

    private Scanner entrada;
    private GerenciadorArquivos gerenciadorArquivos;
    private Ranking ranking;
    private Historico historico;

    public MenuTexto(Scanner entrada, GerenciadorArquivos gerenciadorArquivos,
                     Ranking ranking, Historico historico) {

        this.entrada = entrada;
        this.gerenciadorArquivos = gerenciadorArquivos;
        this.ranking = ranking;
        this.historico = historico;
    }

    public void iniciar() {
        int opcao;

        do {
            exibirMenuPrincipal();
            opcao = lerInteiro();
            entrada.nextLine();

            if (opcao == 1) {
                iniciarJogo();
            } else if (opcao == 2) {
                ranking.exibirRankingProgressivo();
                pausar();
            } else if (opcao == 3) {
                ranking.exibirRankingRapido();
                pausar();
            } else if (opcao == 4) {
                historico.exibirHistorico();
                pausar();
            } else if (opcao == 5) {
                exibirRegras();
                pausar();
            } else if (opcao == 0) {
                gerenciadorArquivos.salvarRanking(ranking);
                gerenciadorArquivos.salvarHistorico(historico);
                System.out.println("Saindo do jogo...");
            } else {
                System.out.println("Opção inválida.");
                pausar();
            }

        } while (opcao != 0);
    }

    private void exibirMenuPrincipal() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("        QUIZ DO CONHECIMENTO");
        System.out.println("=================================");
        System.out.println("1 - Jogar");
        System.out.println("2 - Ver ranking do Modo Progressivo");
        System.out.println("3 - Ver ranking do Modo Rápido");
        System.out.println("4 - Ver histórico de partidas");
        System.out.println("5 - Ver regras");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void iniciarJogo() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("          NOVA PARTIDA");
        System.out.println("=================================");

        System.out.print("Digite seu nome: ");
        String nome = entrada.nextLine();

        Jogador jogador = new Jogador(nome);

        ModoJogo modoEscolhido = escolherModoJogo();

        ArrayList<Pergunta> perguntas;

        try {
            perguntas = gerenciadorArquivos.carregarPerguntas();
        } catch (ArquivoPerguntasException erro) {
            System.out.println("Erro ao carregar perguntas: " + erro.getMessage());
            pausar();
            return;
        }

        Quiz quiz = new Quiz(jogador, perguntas, modoEscolhido, ranking, historico);
        quiz.iniciar(entrada);

        gerenciadorArquivos.salvarRanking(ranking);
        gerenciadorArquivos.salvarHistorico(historico);

        pausar();
    }

    private ModoJogo escolherModoJogo() {
        System.out.println();
        System.out.println("Escolha o modo de jogo:");
        System.out.println("1 - Modo Progressivo");
        System.out.println("2 - Modo Rápido");
        System.out.print("Opção: ");

        int opcaoModo = lerInteiro();
        entrada.nextLine();

        if (opcaoModo == 2) {
            String dificuldadeRapida = escolherDificuldadeModoRapido();
            return new ModoRapido(dificuldadeRapida);
        }

        return new ModoProgressivo();
    }

    private String escolherDificuldadeModoRapido() {
        System.out.println();
        System.out.println("Escolha a dificuldade do Modo Rápido:");
        System.out.println("1 - Fácil");
        System.out.println("2 - Média");
        System.out.println("3 - Difícil");
        System.out.println("4 - Aleatório");
        System.out.print("Opção: ");

        int opcaoDificuldade = lerInteiro();
        entrada.nextLine();

        if (opcaoDificuldade == 1) {
            return "facil";
        } else if (opcaoDificuldade == 2) {
            return "media";
        } else if (opcaoDificuldade == 3) {
            return "dificil";
        } else {
            return "aleatorio";
        }
    }

    private void exibirRegras() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("              REGRAS");
        System.out.println("=================================");

        System.out.println();
        System.out.println("MODO PROGRESSIVO:");
        System.out.println("- O jogador começa na dificuldade Fácil.");
        System.out.println("- As perguntas são sorteadas aleatoriamente.");
        System.out.println("- Cada acerto soma pontos de acordo com a dificuldade.");
        System.out.println("- Após 3 acertos seguidos, o jogador passa a ganhar bônus de sequência.");
        System.out.println("- Se errar, o bônus é perdido.");
        System.out.println("- Cada erro comum tira 10 pontos.");
        System.out.println("- Se o tempo da pergunta acabar, perde 15 pontos.");
        System.out.println("- Ao errar, o jogador desce uma dificuldade.");
        System.out.println("- O jogador possui 3 vidas.");
        System.out.println("- As vidas são controladas por pilha.");
        System.out.println("- Quando as vidas acabam, a partida termina.");
        System.out.println("- Nenhuma pergunta se repete durante a partida.");

        System.out.println();
        System.out.println("MODO RÁPIDO:");
        System.out.println("- O jogador escolhe uma dificuldade: Fácil, Média, Difícil ou Aleatória.");
        System.out.println("- As perguntas são sorteadas aleatoriamente.");
        System.out.println("- O jogador responde perguntas até errar.");
        System.out.println("- A partida termina no primeiro erro.");
        System.out.println("- O limite é de 12 perguntas.");
        System.out.println("- Não existe tempo por pergunta.");
        System.out.println("- O tempo total da partida é contado.");
        System.out.println("- Quanto mais rápido responder, maior pode ser o bônus final.");
        System.out.println("- Nenhuma pergunta se repete durante a partida.");

        System.out.println();
        System.out.println("PONTUAÇÃO:");
        System.out.println("- Fácil: 10 pontos por acerto.");
        System.out.println("- Média: 20 pontos por acerto.");
        System.out.println("- Difícil: 30 pontos por acerto.");
        System.out.println("=================================");
    }

    private int lerInteiro() {
        while (!entrada.hasNextInt()) {
            System.out.print("Digite apenas números: ");
            entrada.next();
        }

        return entrada.nextInt();
    }

    private void pausar() {
        System.out.println();
        System.out.println("Pressione ENTER para continuar...");
        entrada.nextLine();
    }
}