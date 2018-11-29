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
			/* Login.fxml을 로드 */
			Parent parent = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
			StackPane StackPane = new StackPane();
			/* 화면의 크기 지정 */
			Scene scene = new Scene(StackPane, 350, 550);
			StackPane.getChildren().add(parent);
			/* css로드 */
			scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());

			/* 화면 고정 */
			stage.setResizable(false);
			/* stage에 scene 적용 */
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
