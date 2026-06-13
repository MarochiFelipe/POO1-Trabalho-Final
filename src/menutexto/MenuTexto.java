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
import java.util.Collections;
import java.util.HashSet;
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

        String areaEscolhida = escolherArea(perguntas);
        String subareaEscolhida = escolherSubarea(perguntas, areaEscolhida);

        ArrayList<Pergunta> perguntasFiltradas = filtrarPerguntas(perguntas, areaEscolhida, subareaEscolhida);

        if (perguntasFiltradas.isEmpty()) {
            System.out.println("Não existem perguntas disponíveis para essa área e subárea.");
            pausar();
            return;
        }

        Quiz quiz = new Quiz(jogador, perguntasFiltradas, modoEscolhido, ranking, historico);
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

    private String escolherArea(ArrayList<Pergunta> perguntas) {
        ArrayList<String> areas = listarAreas(perguntas);

        System.out.println();
        System.out.println("Escolha uma área do conhecimento:");
        System.out.println("0 - Todas as áreas");

        for (int i = 0; i < areas.size(); i++) {
            System.out.println((i + 1) + " - " + areas.get(i));
        }

        System.out.print("Opção: ");
        int opcao = lerInteiro();
        entrada.nextLine();

        if (opcao == 0) {
            return "TODAS";
        }

        if (opcao >= 1 && opcao <= areas.size()) {
            return areas.get(opcao - 1);
        }

        System.out.println("Opção inválida. Todas as áreas serão usadas.");
        return "TODAS";
    }

    private String escolherSubarea(ArrayList<Pergunta> perguntas, String areaEscolhida) {
        if (areaEscolhida.equalsIgnoreCase("TODAS")) {
            return "TODAS";
        }

        ArrayList<String> subareas = listarSubareas(perguntas, areaEscolhida);

        System.out.println();
        System.out.println("Escolha uma subárea:");
        System.out.println("0 - Todas as subáreas");

        for (int i = 0; i < subareas.size(); i++) {
            System.out.println((i + 1) + " - " + subareas.get(i));
        }

        System.out.print("Opção: ");
        int opcao = lerInteiro();
        entrada.nextLine();

        if (opcao == 0) {
            return "TODAS";
        }

        if (opcao >= 1 && opcao <= subareas.size()) {
            return subareas.get(opcao - 1);
        }

        System.out.println("Opção inválida. Todas as subáreas serão usadas.");
        return "TODAS";
    }

    private ArrayList<String> listarAreas(ArrayList<Pergunta> perguntas) {
        HashSet<String> conjuntoAreas = new HashSet<>();

        for (Pergunta pergunta : perguntas) {
            conjuntoAreas.add(pergunta.getArea());
        }

        ArrayList<String> areas = new ArrayList<>(conjuntoAreas);
        Collections.sort(areas);

        return areas;
    }

    private ArrayList<String> listarSubareas(ArrayList<Pergunta> perguntas, String areaEscolhida) {
        HashSet<String> conjuntoSubareas = new HashSet<>();

        for (Pergunta pergunta : perguntas) {
            if (pergunta.getArea().equalsIgnoreCase(areaEscolhida)) {
                conjuntoSubareas.add(pergunta.getSubarea());
            }
        }

        ArrayList<String> subareas = new ArrayList<>(conjuntoSubareas);
        Collections.sort(subareas);

        return subareas;
    }

    private ArrayList<Pergunta> filtrarPerguntas(ArrayList<Pergunta> perguntas,
                                                 String areaEscolhida,
                                                 String subareaEscolhida) {

        ArrayList<Pergunta> perguntasFiltradas = new ArrayList<>();

        for (Pergunta pergunta : perguntas) {
            boolean areaCorreta = areaEscolhida.equalsIgnoreCase("TODAS") ||
                    pergunta.getArea().equalsIgnoreCase(areaEscolhida);

            boolean subareaCorreta = subareaEscolhida.equalsIgnoreCase("TODAS") ||
                    pergunta.getSubarea().equalsIgnoreCase(subareaEscolhida);

            if (areaCorreta && subareaCorreta) {
                perguntasFiltradas.add(pergunta);
            }
        }

        return perguntasFiltradas;
    }

    private void exibirRegras() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("              REGRAS");
        System.out.println("=================================");

        System.out.println();
        System.out.println("MODO PROGRESSIVO:");
        System.out.println("- O jogador começa na dificuldade Fácil.");
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
        System.out.println("- O jogador responde perguntas até errar.");
        System.out.println("- A partida termina no primeiro erro.");
        System.out.println("- O limite é de 12 perguntas.");
        System.out.println("- Não existe tempo por pergunta.");
        System.out.println("- O tempo total da partida é contado.");
        System.out.println("- Quanto mais rápido responder, maior pode ser o bônus final.");
        System.out.println("- Nenhuma pergunta se repete durante a partida.");

        System.out.println();
        System.out.println("ÁREAS E SUBÁREAS:");
        System.out.println("- Antes da partida, o jogador pode escolher uma área do conhecimento.");
        System.out.println("- Depois, pode escolher uma subárea específica.");
        System.out.println("- Também é possível jogar com todas as áreas e subáreas.");

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