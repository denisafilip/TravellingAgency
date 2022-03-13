package controller;

import javafx.fxml.FXML;

public class TravellingAgencyController {

    private final SceneController sceneController = new SceneController();

    @FXML
    public void goToAddVacationDestination() {
        sceneController.activate("Add Vacation Destination", "travellingAgency/AddVacationDestination.fxml");
    }

    @FXML
    public void goToDeleteVacationDestination() {
        sceneController.activate("Delete Vacation Destination", "travellingAgency/DeleteVacationDestination.fxml");
    }

    @FXML
    public void goToDeleteVacationPackage() {
        sceneController.activate("Delete Vacation Package", "travellingAgency/DeleteVacationPackage.fxml");
    }


    @FXML
    public void goToViewVacationPackages() {
        sceneController.activate("View Vacation Packages", "travellingAgency/ViewVacationPackage.fxml");
    }

    @FXML
    public void goToAddVacationPackage() {
        sceneController.activate("Add Vacation Package","travellingAgency/AddVacationPackage.fxml");
    }

    @FXML
    public void goToEditVacationPackage() {
        sceneController.activate("Edit Vacation Package", "travellingAgency/EditVacationPackage.fxml");
    }
}
