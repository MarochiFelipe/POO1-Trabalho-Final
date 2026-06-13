TRABALHO FINAL POO 1 - QUIZ

Autores: Felipe Marochi Schmidt e Maria Clara Turkot Haçul
Disciplina: Programação Orientada a Objetos 1

Descrição do jogo:

O projeto é um jogo de Quiz interativo em formato de texto (console) desenvolvido em Java. O objetivo do jogo é testar os conhecimentos do usuário por meio de perguntas sorteadas aleatoriamente, envolvendo diferentes áreas do conhecimento.

O jogo desafia o jogador com perguntas de múltipla escolha e de verdadeiro ou falso, oferecendo duas dinâmicas de gameplay distintas:

1. Modo Progressivo: Onde o jogador começa na dificuldade Fácil, possui vidas, ganha bônus por sequências de acertos e a dificuldade se adapta dinamicamente conforme seu desempenho, podendo subir ou descer de nível.

2. Modo Rápido: Uma partida de "morte súbita", com limite de 5 perguntas, onde o jogo acaba no primeiro erro. Nesse modo, o jogador escolhe a dificuldade das perguntas ou pode optar pelo modo aleatório. Ao final, o tempo total da partida é usado para calcular um bônus extra de rapidez.

O sistema também conta com registro de histórico de partidas e um ranking competitivo separado para cada modo de jogo.

Como executar:

Pré-requisitos: Ter o Java Development Kit (JDK) instalado na máquina.

Pelo Terminal / Prompt de Comando:

1. Abra o terminal e navegue até a pasta principal do projeto:

   cd POO1-Trabalho-Final

2. Compile todos os arquivos Java utilizando o comando:

   No Linux/Mac:

   javac -encoding UTF-8 -d out $(find src -name "*.java")

   No Windows:

   dir /s /B src*.java > sources.txt
   javac -encoding UTF-8 -d out @sources.txt

3. Execute a classe principal do jogo:

   java -cp out app.Main

Pela IDE (IntelliJ, Eclipse, VS Code):

1. Importe a pasta do projeto na sua IDE.
2. Localize o arquivo Main.java, dentro do pacote app.
3. Clique com o botão direito e selecione "Run" ou "Executar".

Conceitos aplicados:

Para a construção do projeto, aplicamos fortemente os pilares da Programação Orientada a Objetos:

* Herança e Polimorfismo: Utilizados na estruturação das perguntas, dificuldades e modos de jogo. Temos a classe abstrata Pergunta, que é herdada por PerguntaMultiplaEscolha e PerguntaVerdadeiroFalso. Também temos a classe abstrata Dificuldade, herdada por DificuldadeFacil, DificuldadeMedia e DificuldadeDificil, além da classe abstrata ModoJogo, herdada por ModoProgressivo e ModoRapido. O polimorfismo ocorre quando objetos diferentes são tratados por suas classes mães, como Pergunta, Dificuldade e ModoJogo.

* Classes Abstratas e Interfaces: A interface Validavel define o contrato de validação das respostas. As classes Pergunta, Dificuldade e ModoJogo são abstratas e servem como base para suas implementações específicas.

* Encapsulamento: Os atributos principais das classes, como Jogador, Ranking, Historico, Pergunta e Quiz, são privados, sendo acessados ou modificados por meio de métodos públicos, como getters e métodos específicos de controle.

* Coleções (Collections): O projeto utiliza ArrayList para armazenar perguntas e registros do histórico, HashSet para garantir que perguntas não se repitam durante uma mesma partida, HashMap para organizar o ranking de jogadores e Stack para controlar as vidas do jogador no Modo Progressivo.

* Tratamento de Exceções: O projeto possui exceções personalizadas, como ArquivoPerguntasException, PerguntaInvalidaException, AlternativaInvalidaException e RespostaVFInvalidaException. Elas são usadas para tratar erros relacionados à leitura dos arquivos, ao formato das perguntas e às respostas digitadas pelo jogador.

* Manipulação de Arquivos: O jogo utiliza arquivos .txt para carregar as perguntas e salvar informações importantes, como ranking e histórico de partidas. Os principais arquivos utilizados são perguntas.txt, ranking_progressivo.txt, ranking_rapido.txt e historico.txt.

Detalhamento do item criativo:

O nosso grande "Item Criativo" diferencial foi a implementação de dois modos de jogo com regras independentes, pontuação própria e adaptação da experiência do jogador.

Em vez de um quiz estático comum, nós arquitetamos:

1. Sistema de Dificuldade Dinâmico (Modo Progressivo): O jogo começa na dificuldade Fácil e acompanha o desempenho do jogador. Após uma sequência de acertos, o jogador pode subir de dificuldade, aumentando a pontuação por resposta correta e reduzindo o tempo disponível para responder. Quando o jogador erra, perde pontos, perde uma vida e pode descer de dificuldade. As vidas são controladas por uma pilha, reforçando o uso de estrutura de dados no projeto.

2. Sistema de Bônus por Sequência: No Modo Progressivo, o jogador pode ganhar bônus ao manter uma sequência de acertos. Caso erre, a sequência é interrompida e o bônus deixa de ser aplicado, criando uma dinâmica de risco e recompensa.

3. Cálculo de Bônus por Rapidez (Modo Rápido): No Modo Rápido, o jogador responde até 5 perguntas e a partida termina no primeiro erro. Ao final da partida, o sistema calcula um bônus com base na quantidade de acertos e no tempo total utilizado, transformando a rapidez do jogador em pontos extras.

4. Ranking Duplo: Como as formas de pontuar são diferentes em cada modo, a classe Ranking separa os melhores jogadores do Modo Progressivo dos melhores jogadores do Modo Rápido, tornando a competição mais justa.

5. Histórico de Partidas: Além do ranking, o sistema salva um histórico das partidas jogadas, registrando o nome do jogador, o modo escolhido, a pontuação final, a quantidade de acertos e a quantidade de erros.

Arquivos utilizados:

O projeto utiliza arquivos de texto para armazenar e recuperar informações importantes:

* perguntas.txt: contém o banco de perguntas do jogo.
* ranking_progressivo.txt: armazena o ranking do Modo Progressivo.
* ranking_rapido.txt: armazena o ranking do Modo Rápido.
* historico.txt: armazena o histórico das partidas realizadas.

Estrutura geral do projeto:

O projeto foi organizado em pacotes para separar as responsabilidades das classes:

* app: contém a classe Main, responsável por iniciar o programa.
* ui: contém o MenuTexto, responsável pela interface textual com o usuário.
* model: contém as classes principais do modelo, como Jogador e Pergunta.
* modo: contém as classes relacionadas aos modos de jogo.
* dificuldade: contém as classes relacionadas aos níveis de dificuldade.
* ranking: contém a classe responsável pelo ranking.
* historico: contém a classe responsável pelo histórico de partidas.
* service: contém classes de serviço, como Quiz e GerenciadorArquivos.
* interfaces: contém a interface Validavel.
* excecoes: contém as exceções personalizadas do projeto.
