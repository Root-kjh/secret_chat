package index;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {

		Font.loadFont(getClass().getResourceAsStream("Roboto-Black.ttf"), 10);

		Parent title_bar = FXMLLoader.load(getClass().getResource("/title_bar/Title.fxml"));

		Scene title_bar_scene = new Scene(title_bar);

		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(title_bar_scene);
		stage.show();

	}

}