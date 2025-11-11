package tetris.domain;

import java.io.Serializable;

/**
 * Agregado raiz do domínio. Integra todos os elementos do jogo.
 */
public class Partida implements Serializable {

    // Atributos principais
    private final Jogador jogador;
    private final Board board;
    private final SistemaPontuacao sistemaPontuacao;
    private Tetromino pecaAtual;
    private boolean gameOver = false;
    private boolean pausado = false;

    // Atributos das novas features
    private Tetromino pecaProxima;
    private Posicao posPecaFantasma;
    private Tetromino pecaHold;
    private boolean podeTrocar = true; // Trava para o "Hold"

    public Partida(Jogador jogador) {
        this.jogador = jogador;
        this.board = new Board();
        this.sistemaPontuacao = new SistemaPontuacao();

        // Inicializa as duas primeiras peças
        this.pecaAtual = Tetromino.gerarAleatorio();
        this.pecaProxima = Tetromino.gerarAleatorio();

        // Calcula a posição do "fantasma" inicial
        atualizarFantasma();
    }

    /**
     * Representa um ciclo de atualização (um "passo") do jogo.
     */
    public void tick() {
        if (gameOver) return;

        pecaAtual.moverBaixo();

        if (!board.posicaoValida(pecaAtual)) {
            pecaAtual.setPosicao(pecaAtual.getPosicao().moverParaCima());
            board.fixarPeca(pecaAtual);

            int linhas = board.eliminarLinhasCompletas();
            if (linhas > 0) {
                sistemaPontuacao.adicionarLinhas(linhas);
            }

            // Pega a próxima peça
            pecaAtual = pecaProxima;
            pecaAtual.setPosicao(new Posicao(4, 0)); // Reseta a posição
            pecaProxima = Tetromino.gerarAleatorio(); // Gera uma nova "próxima"

            atualizarFantasma(); // Recalcula o fantasma
            podeTrocar = true; // Libera o "Hold"

            if (!board.posicaoValida(pecaAtual)) {
                gameOver = true;
            }
        }
    }

    /**
     * Tenta rotacionar a peça atual e recalcula o fantasma.
     */
    public void rotacionarPeca() {
        if (pecaAtual == null || isGameOver()) return;
        pecaAtual.rotacionar();
        if (!board.posicaoValida(pecaAtual)) {
            pecaAtual.rotacionarReverso();
        }
        atualizarFantasma(); // Recalcula o fantasma após girar
    }

    /**
     * Move a peça para a esquerda, valida e recalcula o fantasma.
     */
    public void moverPecaEsquerda() {
        if (pecaAtual == null || isGameOver()) return;
        pecaAtual.moverEsquerda();
        if (!board.posicaoValida(pecaAtual)) {
            pecaAtual.moverDireita(); // Desfaz
        }
        atualizarFantasma();
    }

    /**
     * Move a peça para a direita, valida e recalcula o fantasma.
     */
    public void moverPecaDireita() {
        if (pecaAtual == null || isGameOver()) return;
        pecaAtual.moverDireita();
        if (!board.posicaoValida(pecaAtual)) {
            pecaAtual.moverEsquerda(); // Desfaz
        }
        atualizarFantasma();
    }

    /**
     * Calcula a posição final de queda da peça (Ghost Piece).
     */
    private void atualizarFantasma() {
        if (pecaAtual == null) return;
        Posicao posOriginal = pecaAtual.getPosicao();
        Posicao posTeste = posOriginal;

        while (true) {
            Posicao proxima = posTeste.moverParaBaixo();
            pecaAtual.setPosicao(proxima); // Move temporariamente
            if (!board.posicaoValida(pecaAtual)) {
                pecaAtual.setPosicao(posOriginal); // Restaura posição original!
                this.posPecaFantasma = posTeste;  // Salva a última posição válida
                return;
            }
            posTeste = proxima;
        }
    }

    /**
     * Executa o "Hard Drop", movendo a peça para a posição do fantasma.
     */
    public void hardDrop() {
        if (pecaAtual == null || isGameOver()) return;

        pecaAtual.setPosicao(posPecaFantasma);
        tick(); // Força um tick para colidir e fixar imediatamente
    }

    /**
     * Troca a peça atual pela peça em "Hold".
     */
    public void holdPeca() {
        if (!podeTrocar || isGameOver()) return; // Só pode trocar uma vez por peça

        if (pecaHold == null) {
            pecaHold = pecaAtual;
            pecaAtual = pecaProxima;
            pecaProxima = Tetromino.gerarAleatorio();
        } else {
            Tetromino temp = pecaAtual;
            pecaAtual = pecaHold;
            pecaHold = temp;
        }

        pecaAtual.setPosicao(new Posicao(4, 0));
        pecaHold.setPosicao(new Posicao(4, 0));
        pecaHold.readjustColor(); // Corrige a cor (bug de serialização)

        atualizarFantasma();
        podeTrocar = false; // Trava a troca
    }

    public void togglePausa() {
        this.pausado = !this.pausado;
    }

    /**
     * Verifica se o jogo está pausado.
     */
    public boolean isPausado() {
        return this.pausado;
    }

    // --- GETTERS ---
    // Todos os getters devem estar AQUI, dentro da classe.

    public Board getBoard() { return board; }
    public Tetromino getPecaAtual() { return pecaAtual; }
    public SistemaPontuacao getSistemaPontuacao() { return sistemaPontuacao; }
    public boolean isGameOver() { return gameOver; }
    public Jogador getJogador() { return jogador; }

    // Getters das novas features
    public Tetromino getPecaProxima() { return pecaProxima; }
    public Posicao getPosPecaFantasma() { return posPecaFantasma; }
    public Tetromino getPecaHold() { return pecaHold; }

}