package util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private static final String APP_DIR_NAME = ".task-cli";
    private static final String JSON_FILE_NAME = "tasks.json";

    /**
     * Retorna o caminho completo e portável para o arquivo JSON de tarefas.
     * Cria a pasta do aplicativo se ela não existir.
     */
    public static Path getTasksFilePath() {
        // 1. Obtém o diretório Home do usuário
        String userHome = System.getProperty("user.home");

        // 2. Constrói o caminho completo (Ex: /home/user/.task-cli/tasks.json)
        Path appPath = Paths.get(userHome, APP_DIR_NAME);
        Path filePath = appPath.resolve(JSON_FILE_NAME);

        // 3. Cria o diretório do aplicativo se ele não existir
        File appDir = appPath.toFile();
        if (!appDir.exists()) {
            // Se falhar, é importante tratar essa exceção na aplicação real
            appDir.mkdirs(); 
        }

        return filePath;
    }

    public static void main(String[] args) {
        System.out.println("Caminho do arquivo JSON: " + getTasksFilePath());
    }
}
