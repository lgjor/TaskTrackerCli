package model;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;

    public Task(int id, String description, LocalDateTime createdAt, LocalDateTime updatedAt, Status status){
        validateTask(id, description, status);
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Task(int id, String description, Status status){
        validateTask(id, description, status);
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        if (updatedAt == null) {
            throw new IllegalArgumentException("updatedAt não pode ser nulo");
        }
        this.updatedAt = updatedAt;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    // Métodos auxiliares

    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markDone() {
        this.status = Status.DONE;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void markTodo() {
        this.status = Status.TODO;
        this.updatedAt = LocalDateTime.now();
    }

    public void validateTask(int id, String description, Status status){
        if (id <= 0) {
            throw new IllegalArgumentException("ID deve ser positivo");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
    }

    // Override methods

    @Override
    public String toString() {
        return String.format("Task{id='%d', description='%s', status='%s', createdAt='%s', updatedAt='%s'}",
            id, description, status, createdAt, updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

}
