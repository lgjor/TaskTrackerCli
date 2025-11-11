package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Status;
import model.Task;
import util.FileUtils;

public class TaskService {

    // Método principal para adicionar tarefa
    public Task addTask(String description) {
        // Carrega tarefas existentes
        List<Task> tasks = loadTasks();
        
        // Gerar próximo ID
        int nextId = generateNextId(tasks);
        
        // Criar nova tarefa
        Task newTask = new Task(nextId, description, Status.TODO);
        
        // Adiciona à lista
        tasks.add(newTask);
        
        // Salvar no arquivo
        saveTasks(tasks);
        
        return newTask;
    }

    // Carregar tarefas do arquivo JSON
    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        
        // Pega o caminho do arquivo JSON
        Path path = FileUtils.getTasksFilePath();

        // Verifica se o arquivo existe
        if (!Files.exists(path)) {
            return tasks; // Retorna lista vazia se arquivo não existe
        }
        
        try {
            // Ler todo o conteúdo do arquivo
            String content = Files.readString(path);
            
            // Se arquivo vazio, retornar lista vazia
            if (content.trim().isEmpty() || content.trim().equals("[]")) {
                return tasks;
            }
            
            // Fazer parsing manual do JSON
            tasks = parseJsonToTasks(content);
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return tasks;
    }

    private List<Task> parseJsonToTasks(String json) {
        List<Task> tasks = new ArrayList<>();
        
        // Remover espaços e quebras de linha
        json = json.trim();
        
        // Verificar se começa com [ e termina com ]
        if (!json.startsWith("[") || !json.endsWith("]")) {
            return tasks;
        }
        
        // Remover colchetes externos
        json = json.substring(1, json.length() - 1).trim();
        
        if (json.isEmpty()) {
            return tasks;
        }
        
        // Dividir por objetos JSON (procurar por padrão "}, {")
        // Isso é simplificado - você pode precisar de uma abordagem mais robusta
        String[] taskStrings = json.split("\\},\\s*\\{");
        
        for (String taskStr : taskStrings) {
            // Limpar chaves se necessário
            taskStr = taskStr.trim();
            if (taskStr.startsWith("{")) {
                taskStr = taskStr.substring(1);
            }
            if (taskStr.endsWith("}")) {
                taskStr = taskStr.substring(0, taskStr.length() - 1);
            }
            
            Task task = parseTaskObject(taskStr);
            if (task != null) {
                tasks.add(task);
            }
        }
        
        return tasks;
    }

    // Parsear objeto JSON de tarefa
        
    private Task parseTaskObject(String taskJson) {
        try {
            // Extrair campos usando regex ou substring
            int id = extractIntField(taskJson, "id");
            String description = extractStringField(taskJson, "description");
            String statusStr = extractStringField(taskJson, "status");
            String createdAtStr = extractStringField(taskJson, "createdAt");
            String updatedAtStr = extractStringField(taskJson, "updatedAt");
            
            Status status = Status.fromString(statusStr);
            LocalDateTime createdAt = LocalDateTime.parse(createdAtStr);
            LocalDateTime updatedAt = LocalDateTime.parse(updatedAtStr);
            
            return new Task(id, description, createdAt, updatedAt, status);
        } catch (Exception e) {
            System.err.println("Erro ao parsear tarefa: " + e.getMessage());
            return null;
        }
    }
    
    private int extractIntField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*(\\d+)";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalArgumentException("Campo " + fieldName + " não encontrado");
    }
    
    private String extractStringField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        throw new IllegalArgumentException("Campo " + fieldName + " não encontrado");
    }
    
    // Salvar tarefas no arquivo JSON
    private void saveTasks(List<Task> tasks) {
        try {
            String json = tasksToJson(tasks);
            Files.writeString(FileUtils.getTasksFilePath(), json);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
            throw new RuntimeException("Não foi possível salvar as tarefas", e);
        }
    }

    // Converter lista de tarefas para JSON
    private String tasksToJson(List<Task> tasks) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            json.append("  {\n");
            json.append("    \"id\": ").append(task.getId()).append(",\n");
            json.append("    \"description\": \"").append(escapeJson(task.getDescription())).append("\",\n");
            json.append("    \"status\": \"").append(task.getStatus().getDisplayValue()).append("\",\n");
            json.append("    \"createdAt\": \"").append(task.getCreatedAt()).append("\",\n");
            json.append("    \"updatedAt\": \"").append(task.getUpdatedAt()).append("\"\n");
            json.append("  }");
            
            if (i < tasks.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        
        json.append("]");
        return json.toString();
    }

    // Escapar caracteres especiais do JSON
    private String escapeJson(String str) {
        // Escapar caracteres especiais do JSON
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    // Gerar próximo ID
    private int generateNextId(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return 1;
        }
        return tasks.stream()
                   .mapToInt(Task::getId)
                   .max()
                   .orElse(0) + 1;
    }

}
