package Presentation.Controllers;

import Domain.Dtos.cars.CarResponseDto;
import Domain.Dtos.maintenance.AddMaintenanceRequestDto;
import Domain.Dtos.maintenance.MaintenanceResponseDto;
import Presentation.Views.CarsView;
import Services.MaintenanceService;

import javax.swing.*;
import java.util.List;

public class MaintenanceController {

    private final MaintenanceService service;
    private final CarsView view;

    public MaintenanceController(MaintenanceService service, CarsView view) {
        this.service = service;
        this.view = view;

        setupListeners();
    }

    private void setupListeners() {
        view.getAgregarMainButton().addActionListener(e -> addMaintenance());
        view.getClearMainButton().addActionListener(e -> view.clearMaintenanceFields());

        // Cargar mantenimientos al seleccionar un carro (vista -> modelo)
        view.getCarsTable().getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int viewRow = view.getCarsTable().getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = view.getCarsTable().convertRowIndexToModel(viewRow);
                    CarResponseDto car = view.getTableModel().getCars().get(modelRow);

                    // Fijar seleccionado y cargar
                    view.setSelectedCar(car);
                    loadMaintenancesForCar(car.getId());
                }
            }
        });
    }

    private void addMaintenance() {
        CarResponseDto selectedCar = view.getSelectedCar();

        if (selectedCar == null) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un carro primero.");
            return;
        }

        String description = view.getMaintenanceDescriptionField().getText();
        String type = (String) view.getTypeMaintenanceComboBox().getSelectedItem();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La descripción no puede estar vacía.");
            return;
        }

        AddMaintenanceRequestDto request =
                new AddMaintenanceRequestDto(description, type, selectedCar.getId());

        view.showLoading(true);
        service.addMaintenance(request, success -> {
            view.showLoading(false);

            if (success) {
                loadMaintenancesForCar(selectedCar.getId());
                view.clearMaintenanceFields();
                JOptionPane.showMessageDialog(null, "Mantenimiento agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar mantenimiento.");
            }
        });
    }

    private void loadMaintenancesForCar(Long carId) {
        view.showLoading(true);
        service.listMaintenancesByCar(carId, (List<MaintenanceResponseDto> maintenances) -> {
            view.showLoading(false);
            view.getMaintenanceTableModel().setMaintenances(maintenances);
        });
    }
}