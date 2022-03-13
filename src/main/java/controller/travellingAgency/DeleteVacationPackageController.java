package controller.travellingAgency;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.VacationPackage;
import service.VacationPackageService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteVacationPackageController implements Initializable {
    @FXML private JFXComboBox<VacationPackage> vPackagesCombo;
    @FXML private Label resultLbl;

    private final VacationPackageService vacationPackageService = new VacationPackageService();

    @FXML
    private void chooseVacationPackage() {
        resultLbl.setText("");
        resultLbl.setVisible(false);
    }

    @FXML
    private void deleteVacationPackage() {
        VacationPackage vacationPackage = vPackagesCombo.getValue();
        Integer id = vacationPackage.getId();
        String resultMessage = vacationPackageService.delete(id);

        resultLbl.setVisible(true);
        resultLbl.setText(resultMessage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<VacationPackage> vacationPackages = vacationPackageService.getAll();
        ObservableList<VacationPackage> packageList = FXCollections.observableList(vacationPackages);
        vPackagesCombo.getItems().clear();
        vPackagesCombo.setItems(packageList);
    }
}
