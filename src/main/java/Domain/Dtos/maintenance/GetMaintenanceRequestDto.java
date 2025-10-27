package Domain.Dtos.maintenance;

public class GetMaintenanceRequestDto {
    private Long id;

    public GetMaintenanceRequestDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}