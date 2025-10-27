package Domain.Dtos.maintenance;

public class AddMaintenanceRequestDto {
    private String description;
    private String type;
    private Long carId;

    public AddMaintenanceRequestDto(String description, String type, Long carId) {
        this.description = description;
        this.type = type;
        this.carId = carId;
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