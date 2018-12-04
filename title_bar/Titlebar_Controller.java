package title_bar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Titlebar_Controller {
	double x, y, scenex, sceney;
	@FXML
	private Button min, max, close;
	

	@FXML
	void dragged(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();

		sta.setX(robot.getMouseX()- scenex);
		sta.setY(robot.getMouseY()- sceney);
	}

	@FXML
	void pressed(MouseEvent event) {	
		com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scenex = event.getSceneX();
		sceney = event.getSceneY();
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