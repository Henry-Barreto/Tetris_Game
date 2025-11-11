# 🚀 Projeto Tetris em Java (POO Avançada)

<img src="src/GamePlay.png" alt="Gameplay do Tetris em Java" width="200"/>
Este projeto é uma implementação completa do clássico jogo Tetris, desenvolvido 100% em Java. O foco principal não é apenas recriar o jogo, mas também aplicar e demonstrar conceitos avançados de engenharia de software e arquitetura limpa.

O sistema foi construído seguindo uma rigorosa separação de camadas, aplicando **Domain-Driven Design (DDD)** para as regras de negócio e **Test-Driven Development (TDD)** para garantir a robustez da lógica.

<p align="center">
  <img src="src/Resumo.png" alt="Resumo Técnico" width="500"/>
</p>

---

## ✨ Funcionalidades

* **Gameplay Clássica:** Movimentação, rotação e "hard drop" (a ser implementado) das peças.
* [cite_start]**Sistema de Pontuação:** Pontuação baseada no nível, com bônus para "Single", "Double", "Triple" e "Tetris" [cite: 178-182].
* **Sistema de Níveis:** O jogo acelera (diminuindo o `delay` do `ThreadLoop`) conforme o jogador avança de nível.
* [cite_start]**Pintura Customizada:** A interface gráfica é renderizada manualmente usando Java Swing, pintando cada bloco do tabuleiro e das peças[cite: 841].
* [cite_start]**Salvar/Carregar Jogo:** O estado completo da partida pode ser salvo em um arquivo `.dat` (via Serialização) e recarregado ao iniciar o jogo [cite: 463-469].
* [cite_start]**Gravação de Replay:** O jogo salva um histórico de "snapshots" de cada *tick* e os persiste em um arquivo de replay ao final da partida [cite: 517-519].
* [cite_start]**Persistência em Banco de Dados:** O projeto é capaz de se conectar a um banco **SQL Server** para salvar dados de jogadores e o estado de partidas (via Serialização para `VARBINARY(MAX)`)[cite: 277, 400].

---

## 🏛️ Arquitetura e Conceitos Aplicados

Este projeto foi desenhado para ser um item de portfólio robusto, demonstrando domínio sobre os pilares da engenharia de software moderna.

### Metodologias
* **Domain-Driven Design (DDD):** O núcleo do projeto está no pacote `domain`. Classes como `Partida`, `Board`, `Tetromino` e `Posicao` modelam o "domínio" do jogo. [cite_start]A `Partida` atua como um **Agregado Raiz**, orquestrando as regras de negócio[cite: 19, 229].
* **Arquitetura em Camadas:** O código é estritamente separado em camadas (pacotes) com responsabilidades únicas:
    * `domain`: Regras de negócio puras (O "Cérebro").
    * `engine`: O motor do jogo, controle de loop e threads (O "Coração").
    * `ui`: A interface gráfica (Swing).
    * `io`: Leitura/escrita de arquivos locais (Save/Replay).
    * `persistence`: Conexão e comandos para o banco de dados (JDBC).
* [cite_start]**Test-Driven Development (TDD):** O pacote `test` usa **JUnit 5** para validar as regras críticas do `domain` (ex: `BoardTest`, `SistemaPontuacaoTest`), garantindo que a lógica de eliminação de linhas e pontuação funcione corretamente [cite: 1025-1026].

### Conceitos-Chave e Padrões de Projeto
* **Programação Concorrente (Multithreading):** O projeto utiliza duas threads distintas para funcionar:
    1.  A **Thread da UI (Swing)**, que cuida de desenhar e receber inputs.
    2.  A **Thread do Jogo (`ThreadLoop`)**, que executa o `GameEngine` e chama o `partida.tick()` em intervalos regulares (controlando a queda das peças). [cite_start]Isso impede que a lógica do jogo congele a interface gráfica[cite: 577, 586].
* **Persistência Híbrida:**
    1.  **JDBC:** Conexão com **SQL Server** para salvar dados relacionais (ex: `JogadorDAO`).
    2.  [cite_start]**Serialização de Objetos:** Usada para salvar "snapshots" de estado (o objeto `Partida` inteiro) em arquivos locais (`.dat`) e no banco de dados (coluna `VARBINARY(MAX)`)[cite: 400, 467].
* **Padrões de Projeto (Design Patterns):**
    * [cite_start]**Repository (DAO):** `JogadorDAO` e `PartidaDAO` encapsulam toda a lógica SQL, isolando-a do resto da aplicação[cite: 315].
    * [cite_start]**Factory Method:** `Tetromino.gerarAleatorio()` cria e retorna uma instância de uma peça sem que o `GameEngine` precise saber a lógica de criação[cite: 78, 1188].
    * [cite_start]**Singleton:** `ConexaoSQL` garante uma única instância de conexão com o banco de dados [cite: 280-281, 1188].
    * [cite_start]**Template Method:** A classe `Tetromino` define o "esqueleto" dos métodos de rotação, forçando as subclasses (`IPiece`, `TPiece`, etc.) a implementar sua própria lógica [cite: 76-77].
* **POO Avançada:** Uso intenso de **Herança** (`Tetromino`), **Polimorfismo** (diferentes peças e suas rotações) e **Encapsulamento**.
* [cite_start]**Objetos de Valor Imutáveis:** A classe `Posicao` é imutável; métodos como `moverParaBaixo()` retornam uma *nova* instância `Posicao`, garantindo a integridade do estado[cite: 23, 26].

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Java Swing** (para a Interface Gráfica)
* **JDBC (Java Database Connectivity)**
* **SQL Server** (ou qualquer banco SQL compatível)
* **JUnit 5** (para Testes Unitários)

---

## 🚀 Como Executar

### Pré-requisitos
1.  **JDK 17** (ou superior).
2.  **Driver JDBC do SQL Server:** É preciso adicionar o `.jar` do driver (ex: `mssql-jdbc.jar`) às bibliotecas do projeto no seu IDE.
3.  **Banco de Dados:** Um servidor SQL Server rodando. As tabelas (`Jogador`, `Partida`) são criadas automaticamente pela aplicação na primeira execução dos DAOs.

### Configuração
1.  Clone este repositório:
    ```bash
    git clone [URL-DO-SEU-REPOSITÓRIO]
    ```
2.  Abra o projeto na sua IDE (IntelliJ, Eclipse, etc.).
3.  Adicione o driver JDBC do SQL Server às bibliotecas do projeto (em `File > Project Structure... > Libraries` no IntelliJ).
4.  **Importante:** Ajuste a string de conexão, usuário e senha no arquivo `src/tetris/persistence/ConexaoSQL.java` para que correspondam ao seu banco de dados:
    ```java
    private static final String URL = "jdbc:sqlserver://SEU_SERVIDOR:1433;databaseName=TetrisDB;...";
    private static final String USUARIO = "SEU_USUARIO";
    private static final String SENHA = "SUA_SENHA";
    ```

### Execução
Execute o método `main` na classe `src/tetris/ui/Main.java` para iniciar o jogo.

---

## 📂 Estrutura de Diretórios

src/
*└── tetris/1
*├── domain/ (Regras de negócio, entidades: Partida, Board) 
*│ └── pieces/ (Classes das peças: IPiece, TPiece, etc.) 
*├── engine/ (Motor do jogo: GameEngine, ThreadLoop, InputHandler) 
*├── io/ (Save/Load local: SaveManager, ReplayManager) 
*├── persistence/ (Acesso ao BD: ConexaoSQL, JogadorDAO, PartidaDAO) 
*├── ui/ (Interface gráfica: TelaPrincipal, GamePanel, Main) 
*└── test/ (Testes unitários: BoardTest, PartidaTest)
---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## Bibliotecas 

junit-jupiter-api-5.10.3.jar
junit-jupiter-engine-5.10.3.jar
junit-platform-commons-1.10.3.jar
junit-platform-engine-1.10.3.jar
junit-platform-launcher-1.10.3.jar
opentest4j-1.3.0.jar

## Música
O arquivo .mav é muito grande pra ser colocado no projeto, então quando baixar este, aonde tiver TetrisMusic, voce subistitui pelo seu
