package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import service.BookingService;
import service.VacationDestinationService;
import service.VacationPackageService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserMainMenuController {
    private final VacationPackageService vacationPackageService = new VacationPackageService();
    private final VacationDestinationService vacationDestinationService = new VacationDestinationService();
    private final BookingService bookingService = new BookingService();

    @FXML
    private TableView<VacationPackage> bookedVacationsTable;
    @FXML
    private DatePicker startDate;
    @FXML
    private JFXComboBox<VacationDestination> destinationCombo;
    @FXML
    private JFXTextArea priceTxt;
    @FXML
    private HBox filterHBox;
    @FXML
    private TableView<VacationPackage> packagesTable;
    @FXML
    private Label resultLbl;

    @FXML
    private void viewAvailableVacationPackages() {
        resultLbl.setVisible(false);
        List<VacationPackage> availablePackages = vacationPackageService.getAvailablePackages();
        packagesTable.setVisible(true);
        bookedVacationsTable.setVisible(false);
        packagesTable.getItems().clear();

        if (packagesTable.getColumns().isEmpty()) {
            createTable(availablePackages, packagesTable);
            addButtonToTable();
        } else {
            ObservableList<VacationPackage> data = FXCollections.observableArrayList(availablePackages);
            packagesTable.setItems(data);
        }
    }

    @FXML
    private void viewBookedVacations(ActionEvent event) {
        resultLbl.setVisible(false);
        packagesTable.setVisible(false);
        bookedVacationsTable.setVisible(true);
        bookedVacationsTable.getItems().clear();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        User user = (User) stage.getUserData();

        List<VacationPackage> bookedVacationPackages = bookingService.getBookedVacations(user);
        if (bookedVacationsTable.getColumns().isEmpty()) {
            createTable(bookedVacationPackages, bookedVacationsTable);
        } else {
            ObservableList<VacationPackage> data = FXCollections.observableArrayList(bookedVacationPackages);
            bookedVacationsTable.setItems(data);
        }
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

    @FXML
    private void searchVacationPackages() {
        packagesTable.setVisible(true);
        bookedVacationsTable.setVisible(false);
        packagesTable.getItems().clear();

        Integer price;
        if (!priceTxt.getText().isEmpty()) {
            price = Integer.parseInt(priceTxt.getText());
        } else {
            price = null;
        }
        LocalDate startingDate = startDate.getValue();
        VacationDestination destination = destinationCombo.getValue();

        List<VacationPackage> filteredPackages = vacationPackageService.
                filterPackagesByDestinationPriceAndStartDate(destination, price, startingDate);

        if (packagesTable.getColumns().isEmpty()) {
            createTable(filteredPackages, packagesTable);
            addButtonToTable();
        } else {
            ObservableList<VacationPackage> data = FXCollections.observableArrayList(filteredPackages);
            packagesTable.setItems(data);
        }
    }

    @FXML
    private void filterVacationPackages() {
        filterHBox.setVisible(true);

        List<VacationDestination> vacationDestinations = vacationDestinationService.getAll();
        ObservableList<VacationDestination> obList = FXCollections.observableList(vacationDestinations);
        destinationCombo.getItems().clear();
        destinationCombo.setItems(obList);
    }

    private void addButtonToTable() {
        TableColumn<VacationPackage, Void> colBtn = new TableColumn<>("Button Column");

        Callback<TableColumn<VacationPackage, Void>, TableCell<VacationPackage, Void>> cellFactory =
                new Callback<TableColumn<VacationPackage, Void>, TableCell<VacationPackage, Void>>() {
            @Override
            public TableCell<VacationPackage, Void> call(final TableColumn<VacationPackage, Void> param) {
                return new TableCell<VacationPackage, Void>() {

                    private final JFXButton btn = new JFXButton("Book");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Node node = (Node) event.getSource();
                            Stage stage = (Stage) node.getScene().getWindow();
                            User user = (User) stage.getUserData();

                            VacationPackage vacationPackage = getTableView().getItems().get(getIndex());
                            String resultMsg = bookingService.insert(user, vacationPackage);
                            resultLbl.setVisible(true);
                            resultLbl.setText(resultMsg);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        colBtn.setCellFactory(cellFactory);
        packagesTable.getColumns().add(colBtn);

    }
}
