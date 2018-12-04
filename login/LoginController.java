package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class LoginController {
	@FXML
	private TextField ID;
	@FXML
	private PasswordField password;
	@FXML
	private Button sign_up_page;
	@FXML
	private StackPane login;

	public void Sign_UP(ActionEvent event) throws Exception {

		Parent par1 = FXMLLoader.load(getClass().getResource("/sign_up/Sign_up.fxml"));
		BorderPane main_border_pane = (BorderPane) sign_up_page.getScene().getRoot();
		main_border_pane.getChildren().remove(login);
		main_border_pane.setCenter(par1);
	}

}
