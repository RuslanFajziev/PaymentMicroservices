package payservice.config;

public enum StatusHealth {
    DEGRADED("Degraded"),
    UNHEALTHY("Unhealthy"),
    HEALTHY("Healthy");

    private String title;

    StatusHealth(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  title;
    }
}