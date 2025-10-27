package Presentation.Views;

import Domain.Dtos.cars.CarResponseDto;
import Domain.Dtos.maintenance.MaintenanceResponseDto;
import Presentation.Models.CarsTableModel;
import Presentation.Models.MaintenanceTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import javax.swing.*;

public class CarsView {
    private JPanel ContentPanel;
    private JPanel CarPanel;
    private JPanel MaintenancePanel;
    private JScrollPane CarsTableScroll;
    private JTable CarsTable;
    private JButton AgregarButton;
    private JButton BorrarButton;
    private JButton ClearButton;
    private JButton UpdateButton;
    private JTextField CarMakeField;
    private JTextField CarModelField;
    private JTextField YearTextField;
    private JPanel CarFormPanel;
    private JPanel CarButtonPanel;
    private JTable MaintenanceTable;
    private JScrollPane MaintenanceTableScroll;
    private JButton BorrarMainButton;
    private JButton AgregarMainButton;
    private JButton UpdateMainButton;
    private JButton ClearMainButton;
    private JPanel MaintenanceButtonPanel;
    private JPanel MaintenanceFormPanel;
    private JTextField MaintenanceDescriptionField;
    private JComboBox TypeMaintenanceComboBox;

    private final CarsTableModel tableModel;
    private final MaintenanceTableModel maintenanceTableModel;
    private final LoadingOverlay loadingOverlay;

    private CarResponseDto selectedCar;

    public CarsView(JFrame parentFrame) {
        tableModel = new CarsTableModel();
        maintenanceTableModel = new MaintenanceTableModel();

        CarsTable.setModel(tableModel);
        MaintenanceTable.setModel(maintenanceTableModel);

        TypeMaintenanceComboBox.setModel(
                new DefaultComboBoxModel<>(new String[]{"REPAIR", "MOD", "ROUTINE"})
        );

        loadingOverlay = new LoadingOverlay(parentFrame);

        // Listener de selección de filas (vista -> modelo)
        CarsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = CarsTable.getSelectedRow();
                if (viewRow >= 0) {
                    int modelRow = CarsTable.convertRowIndexToModel(viewRow);
                    selectedCar = tableModel.getCars().get(modelRow);
                    populateFields(selectedCar);
                } else {
                    selectedCar = null;
                }
            }
        });
    }

    // --- setter/getter de ---
    public void setSelectedCar(CarResponseDto car) { this.selectedCar = car; }
    public CarResponseDto getSelectedCar() { return selectedCar; }

    // -------------------------------
    // Loading overlay control
    // -------------------------------
    public void showLoading(boolean visible) { loadingOverlay.show(visible); }

    // -------------------------------
    // Getters — Car
    // -------------------------------
    public CarsTableModel getTableModel() { return tableModel; }
    public JPanel getContentPanel() { return ContentPanel; }
    public JTable getCarsTable() { return CarsTable; }
    public JButton getAgregarButton() { return AgregarButton; }
    public JButton getBorrarButton() { return BorrarButton; }
    public JButton getUpdateButton() { return UpdateButton; }
    public JButton getClearButton() { return ClearButton; }
    public JTextField getCarMakeField() { return CarMakeField; }
    public JTextField getCarModelField() { return CarModelField; }
    public JTextField getYearTextField() { return YearTextField; }

    // -------------------------------
    // Getters — Maintenance
    // -------------------------------
    public MaintenanceTableModel getMaintenanceTableModel() { return maintenanceTableModel; }
    public JTable getMaintenanceTable() { return MaintenanceTable; }
    public JButton getBorrarMainButton() { return BorrarMainButton; }
    public JButton getAgregarMainButton() { return AgregarMainButton; }
    public JButton getUpdateMainButton() { return UpdateMainButton; }
    public JButton getClearMainButton() { return ClearMainButton; }
    public JTextField getMaintenanceDescriptionField() { return MaintenanceDescriptionField; }
    public JComboBox<String> getTypeMaintenanceComboBox() { return TypeMaintenanceComboBox; }

    // -------------------------------
    // Helpers
    // -------------------------------
    public void clearCarFields() {
        CarMakeField.setText("");
        CarModelField.setText("");
        YearTextField.setText("");
        CarsTable.clearSelection();
        selectedCar = null;
    }

    public void clearMaintenanceFields() {
        MaintenanceDescriptionField.setText("");
        MaintenanceTable.clearSelection();
    }

    public void populateFields(CarResponseDto car) {
        CarMakeField.setText(car.getMake());
        CarModelField.setText(car.getModel());
        YearTextField.setText(String.valueOf(car.getYear()));
    }

    public void populateMaintenanceFields(MaintenanceResponseDto maintenance) {
        MaintenanceDescriptionField.setText(maintenance.getDescription());
        TypeMaintenanceComboBox.setSelectedItem(maintenance.getType());
    }
}