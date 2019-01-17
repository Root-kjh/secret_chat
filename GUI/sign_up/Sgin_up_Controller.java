package GUI.sign_up;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import data.func.func;
import data.socket.send_block_socket;
import data.socket.server_conn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
	@FXML
	private Label label;

	private int IDmaxlength = 10, NameMaxlength = 20;

	public void Back(ActionEvent event) throws Exception {

		Parent par2 = FXMLLoader.load(getClass().getResource("/GUI/login/Login.fxml"));
		BorderPane main_border_pane = (BorderPane) go_login.getScene().getRoot();
		main_border_pane.getChildren().remove(Sign_up);
		main_border_pane.setCenter(par2);

	}

	public void Sign_up_submit(ActionEvent event) throws Exception {
		if (ID.getText().length() >= IDmaxlength) {
			label.setText("아이디는 10글자 이하로 입력해 주시기 바랍니다.");
			label.setPrefWidth(300.0);
			submit.setLayoutY(380.0);
			go_login.setLayoutY(380.0);
			ID.setText(ID.getText());
			ID.setStyle("-fx-border-color: RED;");
		} else if (name.getText().length() >= NameMaxlength) {
			label.setText("이름은 20글자 이하로 입력해 주시기 바랍니다.");
			label.setPrefWidth(300.0);
			submit.setLayoutY(380.0);
			go_login.setLayoutY(380.0);
			name.setText(name.getText());
			name.setStyle("-fx-border-color: RED;");
		} else if (!password.getText().equals(password_R.getText())) {
			password.setStyle("-fx-border-color: RED;");
		} else {
			URL ipAdress = new URL("http://myexternalip.com/raw");
			BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
			String ip = in.readLine();
			func f=new func();
			
			new server_conn();
			
			f.syn_block(ip);
			System.out.println("syn_block");
			f.add_block(password.getText(), ip, name.getText(), ID.getText());
			f.syn_block(ip);
		}

	}

	@FXML
	void ID_Limit(KeyEvent evnet) throws Exception {
		if (ID.getText().length() >= IDmaxlength) {
			ID.setText(ID.getText().substring(0, 9));
		}
	}

	@FXML
	void name_Limit(KeyEvent evnet) throws Exception {
		if (name.getText().length() >= NameMaxlength) {
			name.setText(name.getText().substring(0, 19));
		}
	}

}