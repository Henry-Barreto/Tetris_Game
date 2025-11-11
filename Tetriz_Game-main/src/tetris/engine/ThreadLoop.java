package tetris.engine;

/**
 * Encapsula a lógica de execução periódica (o "pulso" do jogo).
 * Estende Thread e executa uma Runnable em intervalos regulares.
 */
public class ThreadLoop extends Thread {

    private final Runnable tarefa; // A tarefa a ser executada (será o GameEngine)

    // 'volatile' garante que a mudança em 'rodando' seja vista
    // por todas as threads imediatamente. Essencial para parar a thread.
    private volatile boolean rodando = true;

    private int delay = 200; // 500ms = 2 "frames" por segundo.

    public ThreadLoop(Runnable tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public void run() {
        while (rodando) {
            try {
                // 1. Executa a tarefa principal
                tarefa.run();

                // 2. Dorme pelo tempo do 'delay'
                Thread.sleep(delay);

            } catch (InterruptedException e) {
                // Se a thread for interrompida, para o loop
                rodando = false;
                break;
            }
        }
    }

    /**
     * Sinaliza para a thread parar de forma segura.
     */
    public void parar() {
        this.rodando = false;
        // (Opcional) Você pode chamar 'interrupt()' aqui se quiser
        // que o Thread.sleep() pare imediatamente.
    }

    /**
     * Permite alterar a velocidade do jogo (aumentando o nível).
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}