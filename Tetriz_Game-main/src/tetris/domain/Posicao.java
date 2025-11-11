package tetris.domain;

public final class Posicao {

    private final int x;
    private final int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Posicao moverParaBaixo() {
        return new Posicao(x, y + 1);
    }

    public Posicao moverParaEsquerda() {
        return new Posicao(x - 1, y);
    }

    public Posicao moverParaDireita() {
        return new Posicao(x + 1, y);
    }

    // MÉTODO NOVO - ADICIONE ESTE BLOCO
    /**
     * Retorna uma NOVA instância de Posição movida para cima.
     * Usado no 'tick()' para desfazer um movimento inválido.
     */
    public Posicao moverParaCima() {
        return new Posicao(x, y - 1);
    }
    // FIM DO MÉTODO NOVO
}