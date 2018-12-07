package GUI.index;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {
	public static boolean is_login=false;;
	public static String name=null;
	public static String id=null;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		Parent title_bar;
		Font.loadFont(getClass().getResourceAsStream("Roboto-Black.ttf"), 10);
		if(is_login) 
			title_bar = FXMLLoader.load(getClass().getResource("/GUI/title_bar/main_Title.fxml"));
		else
			title_bar = FXMLLoader.load(getClass().getResource("/GUI/title_bar/Title.fxml"));
		Scene title_bar_scene = new Scene(title_bar);
		stage.getIcons().add(new Image("/GUI/index/icon.png"));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(title_bar_scene);
		stage.show();

	}

}