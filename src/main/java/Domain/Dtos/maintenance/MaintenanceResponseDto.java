package Domain.Dtos.maintenance;

public class MaintenanceResponseDto {
    private Long id;
    private String description;
    private String type;
    private Long carId;

    public MaintenanceResponseDto(Long id, String description, String type, Long carId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.carId = carId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Long getCarId() {
        return carId;
    }
}