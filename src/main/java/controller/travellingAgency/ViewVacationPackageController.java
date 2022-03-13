package controller.travellingAgency;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.VacationPackage;
import model.VacationStatus;
import service.VacationPackageService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewVacationPackageController {
    @FXML private TableView<VacationPackage> notBookedTable;
    @FXML private TableView<VacationPackage> bookedTable;
    @FXML private TableView<VacationPackage> inProgressTable;

    private final VacationPackageService vacationPackageService = new VacationPackageService();

    @FXML
    public void displayVacationPackages() {
        Map<VacationStatus, List<VacationPackage>> packagesByStatus = vacationPackageService.sortPackages();

        createTable(packagesByStatus.get(VacationStatus.BOOKED), bookedTable);
        createTable(packagesByStatus.get(VacationStatus.NOT_BOOKED), notBookedTable);
        createTable(packagesByStatus.get(VacationStatus.IN_PROGRESS), inProgressTable);
    }

    private void createTable(List<VacationPackage> vacationPackages, TableView<VacationPackage> table) {
        List<TableColumn<VacationPackage, String>> columns = new ArrayList<>();
        ObservableList<VacationPackage> data = FXCollections.observableArrayList(vacationPackages);
        table.setEditable(true);

        int index = 0;
        for (Field field : VacationPackage.class.getDeclaredFields()) {
            columns.add(new TableColumn<>(field.getName()));
            columns.get(index).setCellValueFactory(
                    new PropertyValueFactory<>(field.getName())
            );
            index++;
        }
        table.getColumns().addAll(columns);
        table.getColumns().remove(--index);
        table.setItems(data);
    }
}
