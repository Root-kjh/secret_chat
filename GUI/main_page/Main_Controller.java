package GUI.main_page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Main_Controller {
	@FXML
	private AnchorPane friend_pane;
	@FXML
	private ListView<String> friends;
	@FXML
	private StackPane friend_aad, main_pane;
	@FXML
	private ImageView imageView;
	@FXML
	private TextField friend_add_text;
	@FXML
	private Button friend_add_button;
	private int maxlength = 10;
	@FXML
	private Label friend_label;

	@FXML
	void go_frined_add(MouseEvent event) throws Exception {
		Parent par3 = FXMLLoader.load(getClass().getResource("/GUI/main_page/Friend_add.fxml"));
		BorderPane main_border_pane = (BorderPane) imageView.getScene().getRoot();
//		main_border_pane.getChildren().remove(main_pane);
//		main_border_pane.setRight(par3);
		par3.setLayoutX(380);
		par3.setLayoutY(180.0);
		main_border_pane.getChildren().add(par3);

	}

	public void friend_add_action(ActionEvent evnet) throws Exception {

		if (friend_add_text.getText().length() >= maxlength) {
			friend_label.setText("아이디는 10글자 이하로 입력해 주시기 바랍니다.");
			friend_label.setPrefWidth(300.0);
			friend_add_button.setLayoutY(196.0);
			friend_add_button.setLayoutY(236.0);
			friend_add_text.setText(friend_add_text.getText());
			friend_add_text.setStyle("-fx-border-color: RED;");
		}
			BorderPane main_border_pane = (BorderPane) friend_add_button.getScene().getRoot();
			ListView<String> friend_list=(ListView<String>) main_border_pane.getScene().lookup("#friends");
			friend_list.getItems().add("test");
	}

	@FXML
	void frined_add_text(KeyEvent event) throws Exception {
		if (friend_add_text.getText().length() >= maxlength) {
			friend_add_text.setText(friend_add_text.getText().substring(0, 9));
		}
	}
}