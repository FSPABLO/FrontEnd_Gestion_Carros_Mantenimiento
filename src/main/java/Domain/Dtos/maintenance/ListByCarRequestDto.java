package Domain.Dtos.maintenance;

public class ListByCarRequestDto {
    private Long carId;

    public ListByCarRequestDto(Long carId) {
        this.carId = carId;
    }

    public Long getCarId() {
        return carId;
    }
}