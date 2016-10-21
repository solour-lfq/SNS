import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
public class Test extends Application {
	@Override
	public void start(Stage primaryStage) {
		WorkPane workpane = new WorkPane();
		workpane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.UP) {
				workpane.increaseSpeed();
			} else if (e.getCode() == KeyCode.DOWN) {
				workpane.decreaseSpeed();
			}
		});
		workpane.addArrow(0, 360, 1024, 360);
		workpane.addArrow(512, 720, 512, 0);	
		/*
		ArrayList<Double> arraylist = new ArrayList<Double>();
		for (int i = 1; i <= 20; ++i) {
			int key = 2 * i - 1;
			key *= ((i % 2) == 0) ? 1 : -1;
			arraylist.add(1 / Math.PI / key);
			arraylist.add(Math.PI / 4 * key);
			arraylist.add(1 / Math.PI / key);
			arraylist.add(Math.PI / 4 * (0 - key));
		}
		workpane.addDWave(0.5, arraylist);
		*/
		workpane.addDCos(1, 15 * Math.PI / 8, 0);
		Scene scene = new Scene(workpane, 1024, 720);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

