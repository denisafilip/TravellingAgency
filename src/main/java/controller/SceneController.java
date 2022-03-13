package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneController {
    private final HashMap<String, Pane> screenMap = new HashMap<>();

    public SceneController() {

    }

    protected void addScreen(String name, String fxmlPath) {
        try {
            screenMap.put(name, FXMLLoader.load(getClass().getResource(fxmlPath)));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name, String fxmlPath){
        addScreen(name, fxmlPath);

        Parent root = screenMap.get(name);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(name);
        stage.show();

        stage.setOnCloseRequest(we -> removeScreen(name));
    }

    protected void activateAndSetData(String name, String fxmlPath, Object object) {
        addScreen(name, fxmlPath);

        Parent root = screenMap.get(name);
        Stage stage = new Stage();
        stage.setUserData(object);
        stage.setScene(new Scene(root));
        stage.setTitle(name);
        stage.show();

        stage.setOnCloseRequest(we -> removeScreen(name));
    }
}