package app;

import services.TaskService;

public class TaskCli {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Task Tracker CLI");
            System.out.println("Uso: task-cli <comando> [argumentos]");
            System.out.println("\nComandos disponíveis:");
            System.out.println("  add <descrição>");
            System.out.println("  update <id> <descrição>");
            System.out.println("  delete <id>");
            System.out.println("  mark-in-progress <id>");
            System.out.println("  mark-done <id>");
            System.out.println("  list [status]");
            return;
        }
        
        String comando = args[0];
        
        switch (comando) {
            case "add":
                if (args.length < 2) {
                    System.err.println("Erro: descrição não fornecida");
                    return;
                }
                String descricao = args[1];
                System.out.println("Adicionando: " + descricao);
                TaskService taskService = new TaskService();
                taskService.addTask(descricao);
                break;
                
            case "update":
                if (args.length < 3) {
                    System.err.println("Erro: id e descrição são obrigatórios");
                    return;
                }
                try {
                    int id = Integer.parseInt(args[1]);
                    String novaDescricao = args[2];
                    System.out.println("Atualizando tarefa " + id + ": " + novaDescricao);
                    // Implementar lógica
                } catch (NumberFormatException e) {
                    System.err.println("Erro: ID deve ser um número");
                }
                break;
                
            // ... outros casos
        }
    }
}