package tetris.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tetris.domain.SistemaPontuacao;

/**
 * Testa as regras de cálculo do SistemaPontuacao.
 */
public class SistemaPontuacaoTest {

    private SistemaPontuacao sistema;


    @BeforeEach
    void setup() {
        sistema = new SistemaPontuacao();
    }

    @Test
    void devePontuarCorretamenteParaLinhaSimples() {
        sistema.adicionarLinhas(1);
        // Nível 1: 1 linha = 40 pontos
        assertEquals(40, sistema.getPontos());
    }

    @Test
    void devePontuarCorretamenteParaLinhaDupla() {
        sistema.adicionarLinhas(2);
        // Nível 1: 2 linhas = 100 pontos [cite: 1105-1108]
        assertEquals(100, sistema.getPontos());
    }

    @Test
    void devePontuarCorretamenteParaLinhaTripla() {
        sistema.adicionarLinhas(3);
        // Nível 1: 3 linhas = 300 pontos
        assertEquals(300, sistema.getPontos());
    }

    @Test
    void devePontuarCorretamenteParaTetris() {
        sistema.adicionarLinhas(4);
        // Nível 1: 4 linhas = 1200 pontos
        assertEquals(1200, sistema.getPontos());
    }

    @Test
    void deveAumentarNivelAoUltrapassarMilPontos() {
        assertEquals(1, sistema.getNivel(), "Nível inicial deve ser 1");

        // 4 linhas (Tetris) = 1200 pontos
        sistema.adicionarLinhas(4);

        // 1200 pontos / 1000 = 1. Nível deve subir para 2.
        assertEquals(2, sistema.getNivel(), "Nível deveria subir para 2");
    }
}