package title_bar;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Titlebar_Controller {
	double x, y, scenex,sceney;
	@FXML
	private Button min, max, close;
	
	@FXML
	void dragged(MouseEvent event) {
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
		
		sta.setX(robot.getMouseX()-(x-scenex)+350);
		sta.setY(robot.getMouseY()-(y-sceney)+20);
	}

	@FXML
	void pressed(MouseEvent event) {
		com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
		Stage sta = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scenex= event.getSceneX();
		sceney= event.getSceneY();
		x=robot.getMouseX();
		y=robot.getMouseY();
		System.out.println("scenex : "+scenex);
		System.out.println("sceney : "+sceney);
		System.out.println("x: "+x);
		System.out.println("y : "+y);
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