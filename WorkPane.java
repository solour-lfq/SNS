import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.animation.*;
import javafx.util.Duration;
import java.util.ArrayList;
public class WorkPane extends Pane {
	double arrowP1 = 0.3;
	double arrowP2 = 15.0;
	double lollyP1 = 5.0;
	double cubeP1 = (Math.sqrt(6) - Math.sqrt(2)) / 4;
	double cubeP2 = (Math.sqrt(6) + Math.sqrt(2)) / 4;
	double cubeP3 = 0.6;
	double cubeP4 = 0.5;
	double cosP1 = 160;
	double cosP2 = 120;
	ArrayList<Cos> coses = new ArrayList<Cos>();
	Timeline animation;
	
	class Cos {
		Polyline polyline;
		double A;
		double omega;
		double phi;
		boolean move;
		Color color;
		double speed;
		Cos() {
			A = 0;
			omega = 1;
			phi = 0;
			move = false;
			color = Color.WHITE;
			speed = 1.0;
		}
		Cos(double A, double omega) {
			this.A = A;
			this.omega = omega;
			phi = 0;	
			move = false;
			color = Color.WHITE;
			speed = 1.0;
		}
		public void setPhi(double phi) {
			this.phi = phi;
		}
		public void setSpeed(double speed) {
			this.speed = speed;
		}
	}
	
	class Wave {
		ArrayList<Cos> spectrum;
		double a0;
		public Wave() {
			spectrum = new ArrayList<Cos>();
			a0 = 0.0;
		}	
	}
	
	public WorkPane() {
		this.setStyle("-fx-background-color: black;");
		animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveCos()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}	
	
	public void addArrow(double startX, double startY, double endX, double endY, Color color) {
		Line pivot = new Line(startX, startY, endX, endY);
		double theta = Math.asin((endY - startY) / Math.sqrt((endY - startY) * (endY - startY) + (endX - startX) * (endX - startX)));
		double theta1 = Math.PI / 2 - theta + arrowP1;
		double theta2 = arrowP1 - Math.PI / 2 + theta;
		Line one = new Line(endX, endY, endX - arrowP2 * Math.sin(theta1), endY - arrowP2 * Math.cos(theta1));
		Line two = new Line(endX, endY, endX + arrowP2 * Math.sin(theta2), endY - arrowP2 * Math.cos(theta2));
		pivot.setStroke(color);
		one.setStroke(color);
		two.setStroke(color);
		pivot.setStyle("-fx-stroke-width:5;");
		one.setStyle("-fx-stroke-width:5;");
		two.setStyle("-fx-stroke-width:5;");
		this.getChildren().addAll(pivot, one, two);
	}
	
	public void addArrow(double startX, double startY, double endX, double endY) {
		addArrow(startX, startY, endX, endY, Color.WHITE);
	}
	
	public void addLolly(double startX, double startY, double endX, double endY, Color color) {
		Line pivot = new Line(startX, startY, endX, endY);
		Circle circle = new Circle(endX, endY, lollyP1);
		pivot.setStroke(color);
		circle.setStroke(color);
		circle.setFill(color);
		this.getChildren().addAll(pivot, circle);
	}
	
	public void addLolly(double startX, double startY, double endX, double endY) {
		addLolly(startX, startY, endX, endY, Color.BLACK);	
	}
	
	public void addCube(double x, double y, double da, double db, double dc, Color color) {
		Line[] line = new Line[12];
		double a = cubeP1 * da;
		double b = cubeP2 * da;
		double c = cubeP3 * db;
		double d = cubeP4 * db;
		line[0] = new Line(x, y, x - b, y - a );
		line[1] = new Line(x - b, y - a, x - b + d, y - a - c);
		line[2] = new Line(x + d, y - c, x - b + d, y - a - c);
		line[3] = new Line(x, y, x + d, y - c);
		line[4] = new Line(x, y + dc, x - b, y + dc - a);
		line[5] = new Line(x - b, y + dc - a, x - b + d, y + dc - a - c);
		line[6] = new Line(x + d, y + dc - c, x - b + d, y + dc - a - c);
		line[7] = new Line(x, y + dc, x + d, y + dc - c);
		line[8] = new Line(x - b, y - a, x - b, y + dc - a);
		line[9] = new Line(x - b + d, y - a - c, x - b + d, y + dc - a - c);
		line[10] = new Line(x + d, y - c, x + d, y + dc - c);
		line[11] = new Line(x, y, x, y + dc);
		for (int i = 0; i < 12; ++i) {
			line[i].setStroke(color);
			this.getChildren().add(line[i]);
		}
	}
	
	public void addCube(double x, double y, double da, double db, double dc) {
		addCube(x, y, da, db, dc, Color.BLACK);
	}
	
	public void addCos(double centerX, double centerY, double A, double omega, double phi, Color color, boolean move, double speed) {
		Polyline polyline = new Polyline();
		ObservableList<Double> list = polyline.getPoints();
		for (double i = 0; i < 1024; i += 1) {
			list.add(i);
			list.add(centerY - cosP1 * A * Math.cos((i - 512) / cosP2 * Math.PI * omega + phi));
		}
		polyline.setStroke(color);
		polyline.setStyle("-fx-stroke-width: 3;");
		Cos c = new Cos();
		c.polyline = polyline;
		c.A = A;
		c.omega = omega;
		c.phi = phi;
		c.color = color;
		c.speed = speed;
		coses.add(c);
		this.getChildren().add(polyline);
	}	
	
	public void addCos(double centerX, double centerY, double A, double omega, double phi) {
		addCos(centerX, centerY, A, omega, phi, Color.WHITE, false, 1);
	}
	
	public void addCos(double A, double omega, double phi) {
		addCos(512, 360, A, omega, phi);
	}

	public void addWave(double a0, ArrayList<Double> list) {
		Wave wave = new Wave();
		wave.a0 = a0;
		for (int i = 0; i < list.size() - 1; i += 2) {
			double temp1 = list.get(i);
			double temp2 = list.get(i + 1);
			Cos cos = new Cos(temp1, temp2);
			wave.spectrum.add(cos);
		}
		Polyline polyline = new Polyline();
		ObservableList<Double> olist = polyline.getPoints();
		for (double i = 0; i < 1024; ++i) {
			olist.add(i);
			double sum = 0;
			for (int j = 0; j < wave.spectrum.size(); ++j) {
				sum -= cosP1 * wave.spectrum.get(j).A * Math.cos((i - 512) / cosP2 * Math.PI * wave.spectrum.get(j).omega);
			}	
			olist.add(-a0 * cosP1 + 360 + sum);
		}
		polyline.setStroke(Color.WHITE);
		polyline.setStyle("-fx-stroke-width: 3;");
		this.getChildren().add(polyline);
	}
	
	public void moveCos() {
		for (int i = 0; i < coses.size(); ++i) {
			if (coses.get(i).move) {
				ObservableList<Double> temp = coses.get(i).polyline.getPoints();
				double phi = coses.get(i).phi;
				phi += coses.get(i).speed;
				coses.get(i).setPhi(phi);
				for (int j = 0; j < temp.size(); ++j) {
					if ((j % 2) == 1) {
						temp.set(j, 360 + cosP1 * coses.get(i).A * Math.cos((-1 + (j + 1) / 2) / cosP2 * Math.PI * coses.get(i).omega + coses.get(i).phi));
					}
				}
			}
		}
	}
	
	public void decreaseSpeed() {
		for (int i = 0; i < coses.size(); ++i) {
			double speed = coses.get(i).speed;
			speed /= 2;
			coses.get(i).setSpeed(speed);
		}
	}
	
	public void increaseSpeed() {
		for (int i = 0; i < coses.size(); ++i) {
			double speed = coses.get(i).speed;
			speed *= 2;
			coses.get(i).setSpeed(speed);
		}
	}
}
