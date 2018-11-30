package index;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {
	public static Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
			
		try {
			Font.loadFont(getClass().getResourceAsStream("Roboto-Black.ttf"),10);
			/* Login.fxml�� �ε� */
			Parent parent = FXMLLoader.load(getClass().getResource("/title_bar/Title.fxml"));
			StackPane StackPane = new StackPane();
			/* ȭ���� ũ�� ���� */
			scene = new Scene(StackPane, 350, 550);
			StackPane.getChildren().add(parent);

			stage.setResizable(false);
			stage.setTitle("secret_chat");
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setScene(scene);
			
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
