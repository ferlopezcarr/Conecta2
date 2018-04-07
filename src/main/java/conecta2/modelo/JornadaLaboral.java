package conecta2.modelo;

public enum JornadaLaboral {
	TiempoCompleto("Tiempo Completo"),
	TiempoParcial("Tiempo Parcial"),
	PorHoras("Por horas");

    private final String displayName;

    JornadaLaboral(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public String toString() {
    	return displayName;
    }
}

