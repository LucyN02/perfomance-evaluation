package com.itau.performance_evaluation.model.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum BehavioralEnum {
    COLLAB("PROMOVE_COLABORACAO", "Você promove um ambiente colaborativo?", 3),
    LEARN("APRENDE_CONTINUAMENTE", "Você se atualiza e aprende o tempo todo?", 2),
    DATA("USA_DADOS_DECISOES", "Você utiliza dados para tomar suas decisões?", 3),
    AUTONOMY("TRABALHA_AUTONOMIA", "Você trabalha com autonomia?", 2);

    private final String resume;
    private final String description;
    private final int weight;

    BehavioralEnum(String resume, String description, int weight) {
        this.resume = resume;
        this.description = description;
        this.weight = weight;
    }

    public String getResume() {
        return resume;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public static BehavioralEnum getByDescription(String description) {
        return Arrays.stream(values())
                .filter(behavioral -> behavioral.getDescription().equalsIgnoreCase(description))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Descrição não encontrada: " + description));
    }

}
