package sign_up;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Sgin_up_Controller {
	@FXML
	private TextField ID, name;
	@FXML
	private PasswordField password, password_R;
	@FXML
	private Button submit, go_login;
	@FXML
	private StackPane Sign_up;

	public void Back(ActionEvent event) throws Exception {

		Parent par2 = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
		BorderPane main_border_pane = (BorderPane) go_login.getScene().getRoot();
		main_border_pane.getChildren().remove(Sign_up);
		main_border_pane.setCenter(par2);

	}
	
	public void Sign_up_submit(ActionEvent event) throws Exception{
		if(password.getText().equals(password_R.getText())) {
			
		}else {
			password.setStyle("-fx-border-color: RED;");
		}
	}

}