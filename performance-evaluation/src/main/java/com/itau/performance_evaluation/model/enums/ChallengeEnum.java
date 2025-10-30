package com.itau.performance_evaluation.model.enums;

public enum ChallengeEnum {

    CHALLENGE( 2);

    private final int weight;

    ChallengeEnum(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
