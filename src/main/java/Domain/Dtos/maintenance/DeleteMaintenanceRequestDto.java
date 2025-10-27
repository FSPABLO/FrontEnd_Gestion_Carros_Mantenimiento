package Domain.Dtos.maintenance;

public class DeleteMaintenanceRequestDto {
    private Long id;

    public DeleteMaintenanceRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}