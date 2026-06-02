package br.com.divenclasse.inventory.entity.enums;

public enum Gender {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    UNISSEX("Unissex");

    private final String displayName;
    Gender(String displayName) { this.displayName = displayName; }
    public String getDisplayName() { return displayName; }
}
