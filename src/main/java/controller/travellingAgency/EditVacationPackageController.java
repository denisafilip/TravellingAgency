package controller.travellingAgency;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.VacationDestination;
import model.VacationPackage;
import service.VacationDestinationService;
import service.VacationPackageService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditVacationPackageController implements Initializable {

    private final VacationPackageService vacationPackageService = new VacationPackageService();
    private final VacationDestinationService vacationDestinationService = new VacationDestinationService();
    @FXML private JFXButton editVPackageBtn;
    @FXML private Label resultLbl;
    @FXML private JFXComboBox<VacationDestination> destinationCombo;
    @FXML private JFXTextArea extraDetailsText;
    @FXML private JFXTextArea maxBookingUsersText;
    @FXML private JFXTextArea priceText;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private JFXTextArea packageNameText;
    @FXML private JFXComboBox<VacationPackage> vPackagesCombo;

    @FXML
    private void chooseVacationPackage() {
        editVPackageBtn.setDisable(true);
        VacationPackage vacationPackage = vPackagesCombo.getValue();

        packageNameText.setText(vacationPackage.getName());
        priceText.setText(String.valueOf(vacationPackage.getPrice()));
        maxBookingUsersText.setText(String.valueOf(vacationPackage.getMaxBookingUsers()));
        extraDetailsText.setText(vacationPackage.getExtraDetails());
        destinationCombo.setEditable(true);
        destinationCombo.setValue(vacationPackage.getDestination());
        destinationCombo.setEditable(false);
        startDate.setValue(vacationPackage.getStartDate());
        endDate.setValue(vacationPackage.getEndDate());

        editVPackageBtn.setDisable(false);
    }

    @FXML
    private void editVacationPackage() {
        String packageName = packageNameText.getText();
        Integer price;
        if (!priceText.getText().isEmpty()) {
            price = Integer.parseInt(priceText.getText());
        } else {
            price = null;
        }

        Integer maxBookingUsers;
        if (!maxBookingUsersText.getText().isEmpty()) {
            maxBookingUsers = Integer.parseInt(maxBookingUsersText.getText());
        } else {
            maxBookingUsers = null;
        }
        String extraDetails = extraDetailsText.getText();
        VacationDestination destination = destinationCombo.getValue();

        LocalDate startingDate = startDate.getValue();
        LocalDate endingDate = endDate.getValue();
        VacationPackage vacationPackage = vPackagesCombo.getValue();
        String validationMsg = vacationPackageService.edit(vacationPackage.getId(), destination, packageName, price,
                startingDate, endingDate, extraDetails, maxBookingUsers);

        resultLbl.setVisible(true);
        resultLbl.setText(validationMsg);

      /*  List<VacationPackage> vacationPackages = vacationPackageService.getAll();
        ObservableList<VacationPackage> packageList = FXCollections.observableList(vacationPackages);
        vPackagesCombo.setItems(packageList);*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<VacationPackage> vacationPackages = vacationPackageService.getAll();
        ObservableList<VacationPackage> packageList = FXCollections.observableList(vacationPackages);
        vPackagesCombo.getItems().clear();
        vPackagesCombo.setItems(packageList);

        List<VacationDestination> vacationDestinations = vacationDestinationService.getAll();
        ObservableList<VacationDestination> obList = FXCollections.observableList(vacationDestinations);
        destinationCombo.getItems().clear();
        destinationCombo.setItems(obList);
    }
}
