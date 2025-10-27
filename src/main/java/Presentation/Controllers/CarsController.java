package Presentation.Controllers;

import Domain.Dtos.cars.AddCarRequestDto;
import Domain.Dtos.cars.CarResponseDto;
import Domain.Dtos.cars.DeleteCarRequestDto;
import Domain.Dtos.cars.UpdateCarRequestDto;
import Presentation.Observable;
import Presentation.Views.CarsView;
import Services.CarService;
import Utilities.EventType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class CarsController extends Observable {
    private final CarsView carsView;
    private final CarService carService;
    private final Long currentUserId;

    public CarsController(CarsView carsView, CarService carService, Long currentUserId) {
        this.carsView = carsView;
        this.carService = carService;
        this.currentUserId = currentUserId;

        addObserver(carsView.getTableModel());
        loadCarsAsync();
        addListeners();
    }

    private void loadCarsAsync() {
        carsView.showLoading(true);

        SwingWorker<List<CarResponseDto>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<CarResponseDto> doInBackground() throws Exception {

                return carService.listCarsAsync(currentUserId).get();
            }

            @Override
            protected void done() {
                try {
                    List<CarResponseDto> cars = get();
                    carsView.getTableModel().setCars(cars);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    carsView.showLoading(false);
                }
            }
        };
        worker.execute();
    }

    private void addListeners() {
        carsView.getAgregarButton().addActionListener(e -> handleAddCar());
        carsView.getUpdateButton().addActionListener(e -> handleUpdateCar());
        carsView.getBorrarButton().addActionListener(e -> handleDeleteCar());
        carsView.getClearButton().addActionListener(e -> handleClearFields());
        carsView.getCarsTable().getSelectionModel().addListSelectionListener(this::handleRowSelection);
    }

    // ---------------------------
    // Action Handlers
    // ---------------------------
    private void handleAddCar() {
        String make = carsView.getCarMakeField().getText();
        String model = carsView.getCarModelField().getText();
        int year = Integer.parseInt(carsView.getYearTextField().getText());

        AddCarRequestDto dto = new AddCarRequestDto(make, model, year, currentUserId);

        SwingWorker<CarResponseDto, Void> worker = new SwingWorker<>() {
            @Override
            protected CarResponseDto doInBackground() throws Exception {
                // Token = userId real
                return carService.addCarAsync(dto, currentUserId).get();
            }

            @Override
            protected void done() {
                try {
                    CarResponseDto car = get();
                    notifyObservers(EventType.CREATED, car);
                    carsView.clearCarFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    carsView.showLoading(false);
                }
            }
        };

        carsView.showLoading(true);
        worker.execute();
    }

    private void handleUpdateCar() {
        int selectedViewRow = carsView.getCarsTable().getSelectedRow();
        if (selectedViewRow < 0) return;

        int modelRow = carsView.getCarsTable().convertRowIndexToModel(selectedViewRow);
        CarResponseDto selectedCar = carsView.getTableModel().getCars().get(modelRow);

        String make = carsView.getCarMakeField().getText();
        String model = carsView.getCarModelField().getText();
        int year = Integer.parseInt(carsView.getYearTextField().getText());

        UpdateCarRequestDto dto = new UpdateCarRequestDto(selectedCar.getId(), make, model, year);

        SwingWorker<CarResponseDto, Void> worker = new SwingWorker<>() {
            @Override
            protected CarResponseDto doInBackground() throws Exception {
                return carService.updateCarAsync(dto, currentUserId).get();
            }

            @Override
            protected void done() {
                try {
                    CarResponseDto updatedCar = get();
                    notifyObservers(EventType.UPDATED, updatedCar);
                    carsView.clearCarFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    carsView.showLoading(false);
                }
            }
        };

        carsView.showLoading(true);
        worker.execute();
    }

    private void handleDeleteCar() {
        int selectedViewRow = carsView.getCarsTable().getSelectedRow();
        if (selectedViewRow < 0) return;

        int modelRow = carsView.getCarsTable().convertRowIndexToModel(selectedViewRow);
        CarResponseDto selectedCar = carsView.getTableModel().getCars().get(modelRow);
        DeleteCarRequestDto dto = new DeleteCarRequestDto(selectedCar.getId());

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return carService.deleteCarAsync(dto, currentUserId).get();
            }

            @Override
            protected void done() {
                try {
                    Boolean success = get();
                    if (success) notifyObservers(EventType.DELETED, selectedCar.getId());
                    carsView.clearCarFields();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    carsView.showLoading(false);
                }
            }
        };

        carsView.showLoading(true);
        worker.execute();
    }

    private void handleClearFields() {
        carsView.clearCarFields();
    }

    private void handleRowSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int viewRow = carsView.getCarsTable().getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = carsView.getCarsTable().convertRowIndexToModel(viewRow);
                CarResponseDto car = carsView.getTableModel().getCars().get(modelRow);

                carsView.setSelectedCar(car);
                carsView.populateFields(car);
            }
        }
    }
}