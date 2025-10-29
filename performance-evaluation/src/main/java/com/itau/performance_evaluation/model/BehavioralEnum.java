package com.itau.performance_evaluation.model;

public enum BehavioralEnum {
    BEHAVIORAL_1("Você promove um ambiente colaborativo?", 4),
    BEHAVIORAL_2("Você se atualiza e aprende o tempo todo?", 3),
    BEHAVIORAL_3("Você utiliza dados para tomar suas decisões?", 5),
    BEHAVIORAL_4("Você trabalha com autonomia?", 3);

    private final String description;
    private final int weight;

    BehavioralEnum(String description, int weight) {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

}
