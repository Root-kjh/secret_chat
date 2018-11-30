package title_bar;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Titlebar_Controller {
	double x, y;
	@FXML
	private Button min, max, close;

	@FXML
	void dragged(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		sta.setX(event.getSceneX()-x);
		sta.setY(event.getSceneY()-y);
	}

	@FXML
	void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
		System.out.println(x);
		System.out.println(y);
	}

	@FXML
	void close(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		sta.close();
	}

	@FXML
	void min(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		sta.setIconified(true);
	}

	@FXML
	void max(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		sta.setFullScreenExitHint("Secret_chat is Full Screen Mode!!!");
		sta.setFullScreen(true);
	}
}