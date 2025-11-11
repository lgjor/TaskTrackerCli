package Model;

public enum Status {
    DONE("Done"),
    TODO("Todo"),
    IN_PROGRESS("In Progress");

    private final String displayValue;

    // Construtor privado para inicializar o campo
    private Status(String displayValue) {
        this.displayValue = displayValue;
    }

    // Getter público para acessar o valor
    public String getDisplayValue() {
        return displayValue;
    }

    // Retorna o valor de exibição
    @Override
    public String toString() {
        return displayValue;
    }
}
