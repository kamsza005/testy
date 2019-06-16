package aco;

public enum AcoProperty {
    ALPHA ("Alpha"),
    BETA ("Beta"),
    RO ("RO"),
    RANDOM_FACTOR ("Random Factor"),
    ANT_FACTOR ("Ant Factor"),
    ATTEMPTS  ("Attempts"),
    MAX_ITERATIONS ("Max Iterations");

    private String name;

    AcoProperty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
