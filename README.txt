
TRABALHO FINAL POO 1 - QUIZ

Autores: Felipe Marochi Schmidt e Maria Clara Turkot Haçul
Disciplina: Programação Orientada a Objetos 1

Descrição do jogo:

O projeto é um jogo de Quiz interativo em formato de texto (console) desenvolvido em Java. O objetivo do jogo é testar os conhecimentos do usuário em diversas áreas, como Agronomia, Tecnologia, Matemática, Ciências e História. 

O jogo desafia o jogador com perguntas de múltipla escolha e de verdadeiro ou falso, oferecendo duas dinâmicas de gameplay distintas: 
1. Modo Progressivo: Onde o jogador tem vidas, ganha bônus por sequências de acertos e a dificuldade se adapta dinamicamente (subindo ou descendo) conforme seu desempenho.
2. Modo Rápido: Uma partida de "morte súbita" contra o tempo, onde o jogo acaba no primeiro erro e a velocidade da resposta rende bônus extras na pontuação.

O sistema também conta com um registro de histórico de partidas e um Ranking competitivo isolado para cada modo de jogo.


Como executar:

Pré-requisitos: Ter o Java Development Kit (JDK) instalado na máquina.

Pelo Terminal / Prompt de Comando:
1. Abra o terminal e navegue até a pasta raiz do código-fonte do projeto (geralmente a pasta 'src').
2. Compile todos os arquivos Java utilizando o comando:
   javac $(find . -name "*.java")    (No Linux/Mac)
   dir /s /B *.java > sources.txt & javac @sources.txt    (No Windows)
3. Execute a classe principal do jogo (substitua 'Main' caso sua classe principal tenha outro nome):
   java Main

Pela IDE (IntelliJ, Eclipse, VS Code):
1. Importe a pasta do projeto na sua IDE.
2. Localize o arquivo que contém o método `public static void main(String[] args)`.
3. Clique com o botão direito e selecione "Run" (Executar).


Conceitos aplicados:

Para a construção do projeto, aplicamos fortemente os pilares da Programação Orientada a Objetos:

* Herança e Polimorfismo: Utilizados na estruturação das perguntas. Temos uma classe mãe abstrata `Pergunta` que é herdada por `PerguntaMultiplaEscolha` e `PerguntaVerdadeiroFalso`. O polimorfismo ocorre na hora de validar as respostas e formatar os textos, onde cada classe filha responde da sua própria maneira ao mesmo método.
* Classes Abstratas e Interfaces: A interface `Validavel` dita o contrato de validação, e classes como `ModoJogo` e `Pergunta` são abstratas para servirem de molde para suas implementações específicas.
* Encapsulamento: Todos os atributos do modelo (como em `Jogador`, `Ranking`, etc.) são privados (private), sendo acessados ou modificados de forma segura apenas via construtores e métodos públicos (getters).
* Coleções (Collections): Uso extensivo de `ArrayList` para gerenciar o banco de perguntas e o histórico, `HashSet` para garantir que perguntas não se repitam na mesma partida, e `HashMap` para estruturar a tabela de Chave-Valor do Ranking.
* Tratamento de Exceções: Criação de duas exceções personalizadas (`AlternativaInvalidaException` e `RespostaVFInvalidaException`) em blocos try-catch para proteger as entradas do usuário e evitar que o jogo feche (crash) em caso de digitação errada.


Detalhamento do item criativo:

O nosso grande "Item Criativo" diferencial foi a implementação do sistema avançado de Modos de Jogo com regras independentes e adaptação dinâmica.

Em vez de um quiz estático comum, nós arquitetamos:
1. Um Sistema de Dificuldade Dinâmico (Modo Progressivo): O jogo rastreia os acertos seguidos e erros. Se o jogador acerta 3 vezes seguidas, ele sobe de nível (Fácil -> Médio -> Difícil), o tempo limite diminui e as punições ficam mais severas. Se ele erra, o jogo é punitivo, retirando vidas e rebaixando o nível de dificuldade do jogador.
2. Cálculo de Bônus Matemático (Modo Rápido): Criamos um cálculo que transforma a agilidade mental do jogador em pontos, dividindo um multiplicador pelo tempo total em segundos que ele levou para responder a bateria de perguntas.
3. Ranking Duplo: Como as formas de pontuar mudam drasticamente dependendo do modo escolhido, a classe Ranking foi modelada para separar os melhores jogadores do Modo Progressivo dos melhores do Modo Rápido, tornando a competição mais justa.