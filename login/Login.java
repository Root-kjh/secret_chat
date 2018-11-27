package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene scene = new Scene(parent, 350, 550);
			scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
			stage.setResizable(false);
			stage.setScene(scene);
			stage.setTitle("secret_chat");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
