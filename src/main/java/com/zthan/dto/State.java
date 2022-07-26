package com.zthan.dto;

import java.math.BigDecimal;

public class State {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public State() {
    }

    public State(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State state)) return false;

        if (!getStateAbbreviation().equals(state.getStateAbbreviation())) return false;
        if (!getStateName().equals(state.getStateName())) return false;
        return getTaxRate().equals(state.getTaxRate());
    }

    @Override
    public int hashCode() {
        int result = getStateAbbreviation().hashCode();
        result = 31 * result + getStateName().hashCode();
        result = 31 * result + getTaxRate().hashCode();
        return result;
    }
}
