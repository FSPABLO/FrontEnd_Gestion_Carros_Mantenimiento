package Domain.Dtos.maintenance;

public class UpdateMaintenanceRequestDto {
    private Long id;
    private String description;
    private String type;

    public UpdateMaintenanceRequestDto(Long id, String description, String type) {
        this.id = id;
        this.description = description;
        this.type = type;
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
}