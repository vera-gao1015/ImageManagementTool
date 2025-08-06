

import javafx.scene.control.Alert;

public class BasicAlert implements AlertService {

    @Override
    public void showInfo(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    @Override
    public void showWarning(String title, String message) {
        show(Alert.AlertType.WARNING, title, message);
    }

    @Override
    public void showError(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    // Common method to display alert 
    private void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
