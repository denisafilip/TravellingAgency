package controller.travellingAgency;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.VacationDestination;
import service.VacationDestinationService;
import service.VacationPackageService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddVacationPackageController implements Initializable {
    @FXML private JFXTextArea packageNameText;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private JFXTextArea priceText;
    @FXML private JFXTextArea maxBookingUsersText;
    @FXML private JFXTextArea extraDetailsText;
    @FXML private JFXComboBox<VacationDestination> destinationCombo;
    @FXML private Label resultLbl;

    private final VacationPackageService vacationPackageService = new VacationPackageService();
    private final VacationDestinationService vacationDestinationService = new VacationDestinationService();

    @FXML
    public void addVacationPackage() {
        String packageName = packageNameText.getText();
        Integer price;
        if (!priceText.getText().isEmpty()) {
            price = Integer.parseInt(priceText.getText());
        } else {
            price = null;
        }

        Integer maxBookingUsers = Integer.parseInt(maxBookingUsersText.getText());
        String extraDetails = extraDetailsText.getText();
        VacationDestination destination = destinationCombo.getValue();

        LocalDate startingDate = startDate.getValue();
        LocalDate endingDate = endDate.getValue();

        String validationMsg = vacationPackageService.insert(packageName, price, extraDetails, maxBookingUsers, startingDate, endingDate, destination);
        resultLbl.setVisible(true);
        resultLbl.setText(validationMsg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<VacationDestination> vacationDestinations = vacationDestinationService.getAll();
        ObservableList<VacationDestination> obList = FXCollections.observableList(vacationDestinations);
        destinationCombo.getItems().clear();
        destinationCombo.setItems(obList);
    }
}
