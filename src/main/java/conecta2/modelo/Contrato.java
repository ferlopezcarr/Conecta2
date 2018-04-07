package conecta2.modelo;

public enum Contrato {
	Indefinido("Indefinido"),
	Temporal("Temporal"),
	Formacion("En formación"),
	Prácticas("Prácticas");
	
    private final String displayName;

    Contrato(String displayName) {
        this.displayName = displayName;
    }
    
    

    public String getDisplayName() {
        return displayName;
    }

}
