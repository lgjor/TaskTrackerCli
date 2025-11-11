package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {

    private static final String APP_DIR_NAME = ".task-cli";
    private static final String JSON_FILE_NAME = "tasks.json";
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    /**
     * Retorna o caminho completo e portável para o arquivo JSON de tarefas.
     * Cria a pasta do aplicativo se ela não existir.
     */
    public static Path getTasksFilePath() {
        // obtém o home do usuário
        String userHome = System.getProperty("user.home");

        // Caminho do diretório e do arquivo
        Path appPath = Paths.get(userHome, APP_DIR_NAME);
        Path filePath = appPath.resolve(JSON_FILE_NAME);

        // tenta criar o diretório
        try {
            Files.createDirectories(appPath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro fatal: Não foi possível criar o diretório de dados: " + appPath, e);
            throw new RuntimeException("Falha ao configurar diretórios de dados.", e);
        }

        // 4. Retorna o caminho completo
        return filePath;
    }

    public static void main(String[] args) {
        System.out.println("Caminho do arquivo JSON: " + getTasksFilePath());
    }
}
