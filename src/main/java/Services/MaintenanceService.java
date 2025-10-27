package Services;

import Domain.Dtos.RequestDto;
import Domain.Dtos.ResponseDto;
import Domain.Dtos.maintenance.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MaintenanceService extends BaseService {
    private final ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());

    public MaintenanceService(String host, int port) {
        super(host, port);
    }

    public Future<MaintenanceResponseDto> addMaintenanceAsync(AddMaintenanceRequestDto dto, Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "add", gson.toJson(dto), userId.toString());
            ResponseDto response = sendRequest(request);
            if (!response.isSuccess()) return null;
            return gson.fromJson(response.getData(), MaintenanceResponseDto.class);
        });
    }

    public Future<MaintenanceResponseDto> updateMaintenanceAsync(UpdateMaintenanceRequestDto dto, Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "update", gson.toJson(dto), userId.toString());
            ResponseDto response = sendRequest(request);
            if (!response.isSuccess()) return null;
            return gson.fromJson(response.getData(), MaintenanceResponseDto.class);
        });
    }

    public Future<Boolean> deleteMaintenanceAsync(DeleteMaintenanceRequestDto dto, Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "delete", gson.toJson(dto), userId.toString());
            ResponseDto response = sendRequest(request);
            return response.isSuccess();
        });
    }

    public Future<MaintenanceResponseDto> getMaintenanceAsync(GetMaintenanceRequestDto dto, Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "get", gson.toJson(dto), userId.toString());
            ResponseDto response = sendRequest(request);
            if (!response.isSuccess()) return null;
            return gson.fromJson(response.getData(), MaintenanceResponseDto.class);
        });
    }

    public Future<List<MaintenanceResponseDto>> listMaintenancesAsync(Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "list", "", userId.toString());
            ResponseDto response = sendRequest(request);
            if (!response.isSuccess()) return null;
            ListMaintenancesResponseDto listResponse = gson.fromJson(response.getData(), ListMaintenancesResponseDto.class);
            return listResponse.getMaintenances();
        });
    }

    public Future<List<MaintenanceResponseDto>> listByCarAsync(ListByCarRequestDto dto, Long userId) {
        return executor.submit(() -> {
            RequestDto request = new RequestDto("Maintenance", "list_by_car", gson.toJson(dto), userId.toString());
            ResponseDto response = sendRequest(request);
            if (!response.isSuccess()) return null;
            ListMaintenancesResponseDto listResponse = gson.fromJson(response.getData(), ListMaintenancesResponseDto.class);
            return listResponse.getMaintenances();
        });
    }

    public void listMaintenancesByCar(Long carId, java.util.function.Consumer<List<MaintenanceResponseDto>> callback) {
        executor.submit(() -> {
            try {
                ListByCarRequestDto dto = new ListByCarRequestDto(carId);
                RequestDto request = new RequestDto("Maintenance", "list_by_car", gson.toJson(dto), "1");
                ResponseDto response = sendRequest(request);

                if (!response.isSuccess()) {
                    callback.accept(null);
                    return;
                }

                ListMaintenancesResponseDto listResponse = gson.fromJson(response.getData(), ListMaintenancesResponseDto.class);
                callback.accept(listResponse.getMaintenances());
            } catch (Exception e) {
                e.printStackTrace();
                callback.accept(null);
            }
        });
    }

    public void addMaintenance(AddMaintenanceRequestDto dto, java.util.function.Consumer<Boolean> callback) {
        executor.submit(() -> {
            try {
                RequestDto request = new RequestDto("Maintenance", "add", gson.toJson(dto), "1"); // Hardcodeado como en el resto
                ResponseDto response = sendRequest(request);
                callback.accept(response.isSuccess());
            } catch (Exception e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }
}