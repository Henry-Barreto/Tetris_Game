package tetris.engine;

import tetris.domain.Partida;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Implementa KeyListener para interceptar eventos de teclado
 * e traduzi-los em comandos do domínio.
 */
public class InputHandler implements KeyListener {

    private final Partida partida;

    public InputHandler(Partida partida) {
        this.partida = partida;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // A tecla 'P' deve funcionar a qualquer momento para pausar/despausar
        if (e.getKeyCode() == KeyEvent.VK_P) {
            partida.togglePausa();
            return; // Sai do método
        }

        // Ignora todos os outros inputs se o jogo estiver pausado ou acabado
        if (partida.isGameOver() || partida.isPausado()) return;

        // Se não estiver pausado, processa as teclas
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                partida.moverPecaEsquerda();
                break;

            case KeyEvent.VK_RIGHT:
                partida.moverPecaDireita();
                break;

            case KeyEvent.VK_DOWN:
                partida.getPecaAtual().moverBaixo(); // Acelera
                break;

            case KeyEvent.VK_UP:
                partida.rotacionarPeca();
                break;

            case KeyEvent.VK_SPACE:
                partida.hardDrop();
                break;

            case KeyEvent.VK_C:
                partida.holdPeca();
                break;

            // O 'case KeyEvent.VK_P:' antigo foi movido para cima
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}