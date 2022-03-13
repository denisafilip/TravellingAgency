package controller.travellingAgency;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.VacationDestination;
import service.VacationDestinationService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteVacationDestinationController implements Initializable {
    @FXML private JFXComboBox<VacationDestination> vDestinationCombo;
    @FXML private Label resultLbl;

    private final VacationDestinationService vacationDestinationService = new VacationDestinationService();

    @FXML
    private void chooseVacationDestination() {
        resultLbl.setText("");
        resultLbl.setVisible(false);
    }

    @FXML
    private void deleteVacationDestination() {
        VacationDestination vacationDestination = vDestinationCombo.getValue();
        Integer id = vacationDestination.getId();
        String resultMessage = vacationDestinationService.delete(id);

        resultLbl.setVisible(true);
        resultLbl.setText(resultMessage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<VacationDestination> vacationDestinations = vacationDestinationService.getAll();
        ObservableList<VacationDestination> destinationsList = FXCollections.observableList(vacationDestinations);
        vDestinationCombo.getItems().clear();
        vDestinationCombo.setItems(destinationsList);
    }
}
