package tetris.io;

import java.io.*; // Importa todas as classes de Input/Output
import tetris.domain.Partida;

/**
 * Gerencia o salvamento (serialização) e carregamento (desserialização)
 * do estado da partida em um arquivo local.
 */
public class SaveManager {

    // Nome do arquivo de save
    private static final String SAVE_FILE = "partida_salva.dat";

    /**
     * Salva o estado completo da partida em um arquivo binário.
     * Usa ObjectOutputStream para serializar o objeto.
     */
    public static void salvar(Partida partida) {
        // Try-with-resources fecha o 'oos' e 'fos' automaticamente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {

            oos.writeObject(partida); // Serializa e escreve o objeto
            System.out.println("[SAVE] Jogo salvo com sucesso em " + SAVE_FILE);

        } catch (IOException e) {
            System.err.println("[SAVE] Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    /**
     * Carrega o estado da partida do arquivo de save.
     * Usa ObjectInputStream para desserializar o objeto.
     */
    public static Partida carregar() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("[LOAD] Nenhum save encontrado.");
            return null; // Retorna nulo se o arquivo não existe
        }

        // Try-with-resources fecha o 'ois' e 'fis' automaticamente
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

            Partida partidaCarregada = (Partida) ois.readObject(); // Lê e desserializa

            if (partidaCarregada.getPecaAtual() != null) {
                partidaCarregada.getPecaAtual().readjustColor();
            }
            System.out.println("[LOAD] Jogo carregado com sucesso.");
            return partidaCarregada;

        } catch (IOException | ClassNotFoundException e) {
            // Captura dois tipos de exceção:
            // IOException: Erro ao ler o arquivo
            // ClassNotFoundException: O arquivo salvo é de uma versão antiga da classe
            System.err.println("[LOAD] Erro ao carregar o jogo: " + e.getMessage());
            return null;
        }
    }

    /**
     * (Opcional, mas útil) Remove o arquivo de save existente.
     */
    public static void limparSave() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
            System.out.println("[SAVE] Arquivo de save removido.");
        }
    }
}