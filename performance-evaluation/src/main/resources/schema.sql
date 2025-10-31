CREATE TABLE IF NOT EXISTS tb_performance_assessment (
    id BIGSERIAL NOT NULL,
    employee_id VARCHAR(255) NOT NULL,
    challenge_final_average DECIMAL(19, 2),
    behavior_final_average DECIMAL(19, 2),

    PRIMARY KEY (id)
);

CREATE TABLE tb_challenge_detail (
    id BIGSERIAL NOT NULL,
    description VARCHAR(255),
    score INT NOT NULL,

    assessment_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_challenge_assessment
        FOREIGN KEY (assessment_id)
        REFERENCES tb_performance_assessment (id)
        ON DELETE CASCADE
);


CREATE TABLE tb_behavioral_detail (
    id BIGSERIAL NOT NULL,
    description VARCHAR(255),
    score INT NOT NULL,

    assessment_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_behavioral_assessment
        FOREIGN KEY (assessment_id)
        REFERENCES tb_performance_assessment (id)
        ON DELETE CASCADE
);