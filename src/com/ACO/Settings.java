package com.ACO;

public class Settings {
    private double alpha;
    private double beta;
    private double evaporation;
    private double Q;
    private double antFactor;

    public Settings(double alpha, double beta, double evaporation, double Q, double antFactor){
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.Q = Q;
        this.antFactor = antFactor;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public double getBeta() {
        return this.beta;
    }

    public double getEvaporation() {
        return this.evaporation;
    }

    public double getQ() {
        return this.Q;
    }

    public double getAntFactor() {
        return this.antFactor;
    }
}
