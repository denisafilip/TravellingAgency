package controller.travellingAgency;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.VacationDestinationService;

public class AddVacationDestinationController {
    @FXML private JFXTextArea vDestinationName;
    @FXML private Label resultLbl;

    private final VacationDestinationService vacationDestinationService = new VacationDestinationService();

    @FXML
    public void addVacationDestination() {
        String destinationName = vDestinationName.getText();
        String message = vacationDestinationService.insertVacationDestination(destinationName);
        resultLbl.setVisible(true);
        resultLbl.setText(message);
        vDestinationName.clear();
    }
}
