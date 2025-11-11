package model;

public enum Status {
    DONE("done"),
    TODO("todo"),
    IN_PROGRESS("in-progress");

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

    // Converte uma string para um Status
    public static Status fromString(String value) {
        if (value == null) return null;
        for (Status status : Status.values()) {
            if (status.displayValue.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
