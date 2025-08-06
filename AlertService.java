

public interface AlertService {
    void showInfo(String title, String message);
    void showWarning(String title, String message);
    void showError(String title, String message);
}
