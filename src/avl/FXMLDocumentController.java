package avl;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author acer
 */
public class FXMLDocumentController implements Initializable {
	Tree cay = new Tree();
	@FXML
	private Label label;
	@FXML
	private Label label2;
	@FXML
	private ComboBox<String> combo1;
	@FXML
	private TextField champ_text1;
	@FXML
	private TextField champ_text2;
	@FXML
	private Button btn1;
	@FXML
	private Button btn2;
	@FXML
	private Button btn3;
	@FXML
	private Button btn4;
	@FXML
	private AnchorPane pan;

	@FXML
	private void handleButtonAction(ActionEvent event) {
		if (event.getSource().equals(btn1)) {
			if (combo1.getValue() == "Delete") {
				int numbre = Integer.parseInt(champ_text1.getText());
				if (cay.goc.timkiem(numbre)) {
					cay.xoa(numbre);
					tracer_arn(cay.goc, null);
					label.setText("Deleted this Node");
				} else
					label.setText("This Node does not exist in this tree");

			} else if (combo1.getValue() == "Search") {
				// label.setText("Search");
				int numbre = Integer.parseInt(champ_text1.getText());
				if (cay.goc.timkiem(numbre)) {
					tracer_arn(cay.goc, numbre);
					label.setText("Node " + numbre + " have in tree©");
				} else
					label.setText("This Node does not exist in this tree");

			} else {
				int numbre = Integer.parseInt(champ_text1.getText());
				cay.them(numbre);
				label.setText("Node " + numbre + " has been added in tree");
				tracer_arn(cay.goc, null);

			}

		} else if (event.getSource().equals(btn2)) {
			String numbres = champ_text2.getText();
			String[] tab = numbres.split(" ");
			for (int i = 0; i < tab.length; i++) {
				cay.them(Integer.parseInt(tab[i]));
				tracer_arn(cay.goc, null);
			}
			label.setText("chain has been added to the tree");
		} else {
			if (event.getSource().equals(btn3)) {
				cay = new Tree();
				pan.getChildren().clear();
				label.setText("Trees have been removed");
			} else if (event.getSource().equals(btn4)) {
				cay = new Tree();
				;
				for (int i = 0; i < 25; i++) {
					int n = (int) Math.ceil(Math.random() * 1000);
					cay.them(n);
				}
				tracer_arn(cay.goc, null);
				label.setText("Added a random tree");
			}
		}
	}

	private void tracer_arn(Node rac, Comparable<Integer> o) {
		if (!rac.laylinhcanh()) {
			pan.getChildren().clear();
			if (o == null)
				tracer_arn(0, (float) pan.getWidth(), rac, 20);
			else
				tracer_after_search(0, (float) pan.getWidth(), rac, 20, o);
		}

	}

	private float tracer_arn(float x1, float x2, Node rac, float y) {
		float xd = 0, xg = 0;

		int nbFeuille = GetnbFeuille(rac);
		int nbFeuilleG = GetnbFeuille(rac.trai);
		float x = (nbFeuilleG * 100 / nbFeuille) * (x2 - x1) / 100 + x1;
		// Tính toán vị trí hiện tại của Node và vẽ những vòng
		tracer_cercle(x, y, rac.color, rac.info);

		if (!rac.phai.laylinhcanh()) {
			xd = tracer_arn(x, x2, rac.phai, y + 50);
			tracer_droite(x + 15, y + 8, xd, y + 35);
		} else {
			if (!rac.trai.laylinhcanh())
				tracer_droite(x + 15, y + 8, x + 20, y + 50);
			else
				tracer_droite(x, y + 15, x + 20, y + 50);
			tracer_rectangle(x + 13, y + 40);
		}

		if (!rac.trai.laylinhcanh()) {
			xg = tracer_arn(x1, x, rac.trai, y + 50);
			tracer_droite(x - 15, y + 8, xg, y + 35);
		} else {

			if (!rac.phai.laylinhcanh())
				tracer_droite(x - 15, y + 8, x - 20, y + 50);
			else
				tracer_droite(x, y + 15, x - 20, y + 50);
			tracer_rectangle(x - 27, y + 40);
		}
		return x;

	}

	private float tracer_after_search(float x1, float x2, Node rac, float y, Comparable<Integer> o) {
		float xd = 0, xg = 0;
		int nbFeuille = GetnbFeuille(rac);
		int nbFeuilleG = GetnbFeuille(rac.trai);
		float x = (nbFeuilleG * 100 / nbFeuille) * (x2 - x1) / 100 + x1;
		if (rac.info.equals(o)) {
			Circle c = new Circle(x, y, 21, Color.DARKBLUE);
			Circle c2 = new Circle(x, y, 17.5, Color.WHITE);
			pan.getChildren().addAll(c, c2);
		}
		tracer_cercle(x, y, rac.color, rac.info);
		if (!rac.phai.laylinhcanh()) {
			xd = tracer_after_search(x, x2, rac.phai, y + 50, o);
			tracer_droite(x + 15, y + 8, xd, y + 35);
		} else {
			if (!rac.trai.laylinhcanh())
				tracer_droite(x + 15, y + 8, x + 20, y + 50);
			else
				tracer_droite(x, y + 15, x + 20, y + 50);

			tracer_rectangle(x + 13, y + 40);
		}

		if (!rac.trai.laylinhcanh()) {
			xg = tracer_after_search(x1, x, rac.trai, y + 50, o);
			tracer_droite(x - 15, y + 8, xg, y + 35);
		} else {
			if (!rac.phai.laylinhcanh())
				tracer_droite(x - 15, y + 8, x - 20, y + 50);
			else
				tracer_droite(x, y + 15, x - 20, y + 50);
			tracer_rectangle(x - 27, y + 40);
		}
		return x;

	}

	private int GetnbFeuille(Node r) {
		if (r.laylinhcanh())
			return 1;
		else
			return (GetnbFeuille(r.trai) + GetnbFeuille(r.phai));
	}

	private void tracer_cercle(float x, float y, Color c, Comparable<?> info) {

		Circle cercle = new Circle(x, y, 15, c);
		Label label = new Label("" + info);
		if (Integer.parseInt(info.toString()) < 100)
			label.setLayoutX(x - 6);
		else
			label.setLayoutX(x - 9);
		label.setLayoutY(y - 9);
		label.setTextFill(Color.WHITE);
		pan.getChildren().add(cercle);
		pan.getChildren().add(label);
	}

	private void tracer_droite(float x1, float y1, float x2, float y2) {
		Line line = new Line();
		line.setStartX(x1);
		line.setStartY(y1);
		line.setEndX(x2);
		line.setEndY(y2);
		pan.getChildren().add(line);
	}

	private void tracer_rectangle(float x, float y) {
		Rectangle rec = new Rectangle(14, 14, Color.BLACK);
		rec.setLayoutX(x);
		rec.setLayoutY(y);
		pan.getChildren().add(rec);
	}

	@FXML
	private void handleComboAction(ActionEvent event) {
		if (combo1.getValue() == "Delete") {
			btn1.setText("Delete");
			champ_text2.setEditable(false);
			btn2.setDisable(true);
			label2.setVisible(false);

		} else if (combo1.getValue() == "Search") {
			btn1.setText("Search");
			champ_text2.setEditable(false);
			btn2.setDisable(true);
			label2.setVisible(false);
		} else {
			btn1.setText("Insert");
			champ_text2.setEditable(true);
			btn2.setDisable(false);
			label2.setVisible(true);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ObservableList<String> options = FXCollections.observableArrayList("Insert", "Search", "Delete");
		combo1.setItems(options);
		combo1.setPromptText("Insert");

		// Hai lisnener trên cửa sổ, nếu việc thay đổi kích thước cửa sổ
		// tôi affche cây
		pan.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

				if (combo1.getValue() == "Search") {
					int numbre = Integer.parseInt(champ_text1.getText());
					if (cay.goc.timkiem(numbre))
						tracer_arn(cay.goc, numbre);
					else
						tracer_arn(cay.goc, null);
				} else
					tracer_arn(cay.goc, null);

			}
		});
	}

}
