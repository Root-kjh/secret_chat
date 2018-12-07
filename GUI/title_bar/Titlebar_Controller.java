package GUI.title_bar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Titlebar_Controller {
	double x, y, scenex, sceney;
	@FXML
	private BorderPane border;
	@FXML
	private HBox title_bar;
	@FXML
	private Button min, max, close;
	@FXML
	private Text title_text;
	boolean full_screen_flag=false;
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
		
		if(full_screen_flag) {
			sta.setFullScreen(false);
			full_screen_flag=false;
		}else {
		sta.setFullScreenExitHint("Secret_chat is Full Screen Mode!!!");
		sta.setFullScreen(true);
		full_screen_flag=true;
		}
		
		}
}