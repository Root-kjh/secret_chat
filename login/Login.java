package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Login extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			/* title */
			stage.setTitle("secret_chat");
			/* Login.fxml�� �ε� */
			Parent parent = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			StackPane StackPane = new StackPane();
			/* ȭ���� ũ�� ���� */
			Scene scene = new Scene(StackPane, 350, 550);
			StackPane.getChildren().add(parent);
			/* css�ε� */
			scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());

			/* ȭ�� ���� */
			stage.setResizable(false);
			/* stage�� scene ���� */
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
