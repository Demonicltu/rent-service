package com.task.backend.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commitment_months")
    private Integer commitmentMonths;

    private float value;

    public Price(Long id, Integer commitmentMonths, float value) {
        this.id = id;
        this.commitmentMonths = commitmentMonths;
        this.value = value;
    }

    public Price() {
        //Empty for auto init
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCommitmentMonths() {
        return commitmentMonths == null ? 0 : commitmentMonths;
    }

    public void setCommitmentMonths(Integer commitmentMonths) {
        this.commitmentMonths = commitmentMonths;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
