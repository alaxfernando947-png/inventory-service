package br.com.divenclasse.inventory.entity.enums;

public enum Category {
    TERNO("Terno"),
    BLAZER("Blazer"),
    CAMISA_SOCIAL("Camisa Social"),
    CALCA_SOCIAL("Calça Social"),
    GRAVATA("Gravata"),
    SAPATO_SOCIAL("Sapato Social"),
    CINTO("Cinto"),
    COLETE("Colete");

    private final String displayName;
    Category(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
