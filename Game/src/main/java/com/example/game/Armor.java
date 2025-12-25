package com.example.game;

import javafx.beans.property.*;

public class Armor {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty damageNegation;
    private final StringProperty rarity;
    private final StringProperty specialEffect;
    private final DoubleProperty weight;

    public Armor(int id, String name, int damageNegation, String rarity, String specialEffect, double weight) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.damageNegation = new SimpleIntegerProperty(damageNegation);
        this.rarity = new SimpleStringProperty(rarity);
        this.specialEffect = new SimpleStringProperty(specialEffect);
        this.weight = new SimpleDoubleProperty(weight);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public int getDamageNegation() { return damageNegation.get(); }
    public IntegerProperty damageNegationProperty() { return damageNegation; }
    public void setDamageNegation(int damageNegation) { this.damageNegation.set(damageNegation); }

    public String getRarity() { return rarity.get(); }
    public StringProperty rarityProperty() { return rarity; }
    public void setRarity(String rarity) { this.rarity.set(rarity); }

    public String getSpecialEffect() { return specialEffect.get(); }
    public StringProperty specialEffectProperty() { return specialEffect; }
    public void setSpecialEffect(String specialEffect) { this.specialEffect.set(specialEffect); }

    public double getWeight() { return weight.get(); }
    public DoubleProperty weightProperty() { return weight; }
    public void setWeight(double weight) { this.weight.set(weight); }
}
