package GUI.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import data.func.func;
import data.socket.server_conn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
	@FXML
	private TextField ID;
	@FXML
	private PasswordField password;
	@FXML
	private Button go, submit;
	@FXML
	private StackPane login;
	@FXML
	private Label label;
	private int maxlength = 10;

	public void Sign_UP(ActionEvent event) throws Exception {

		Parent par1 = FXMLLoader.load(getClass().getResource("/GUI/sign_up/Sign_up.fxml"));
		BorderPane main_border_pane = (BorderPane) go.getScene().getRoot();
		main_border_pane.getChildren().remove(login);
		main_border_pane.setCenter(par1);
	}

	public void login(ActionEvent event) throws Exception {
		if (ID.getText().length() >= maxlength) {
			label.setText("아이디는 10글자 이하로 입력해 주시기 바랍니다.");
			label.setPrefWidth(300.0);
			submit.setLayoutY(296.0);
			go.setLayoutY(336.0);
			ID.setText(ID.getText());
			ID.setStyle("-fx-border-color: RED;");
		} else {
			URL ipAdress = new URL("http://myexternalip.com/raw");
			BufferedReader in = new BufferedReader(new InputStreamReader(ipAdress.openStream()));
			String ip = in.readLine();
			func f=new func();
			
			new server_conn();
			
			f.syn_block(ip);
			if (f.Search_Block(ID.getText(), password.getText()).equals("")) {		
				label.setText("아이디 혹은 비밀번호가 맞지 않습니다.");
				label.setPrefWidth(300.0);
				submit.setLayoutY(296.0);
				go.setLayoutY(336.0);
				ID.setText(ID.getText());
				password.setText("");
				password.setStyle("-fx-border-color: RED;");
			} else {
				Stage oldstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				Stage newstage = new Stage();
				Parent second = FXMLLoader.load(getClass().getResource("/GUI/title_bar/main_Title.fxml"));
				Scene sc = new Scene(second);
				newstage.setScene(sc);
				newstage.getIcons().add(new Image("/GUI/index/icon.png"));
				newstage.initStyle(StageStyle.TRANSPARENT);
				newstage.show();
				oldstage.close();
			}
		}
	}

	@FXML
	void Limit_id(KeyEvent evnet) throws Exception {
		if (ID.getText().length() >= maxlength) {
			ID.setText(ID.getText().substring(0, 9));
		}
	}

}