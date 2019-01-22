package popup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

  public static void display(String title, String label) {
    final Stage popupWindow = new Stage();

    popupWindow.initModality(Modality.APPLICATION_MODAL);
    popupWindow.setTitle(title);

    Label label1 = new Label(label);

    Button button1 = new Button("Ok");

    button1.setOnAction(e -> popupWindow.close());

    VBox layout = new VBox(20);

    layout.getChildren().addAll(label1, button1);

    layout.setAlignment(Pos.CENTER);

    Scene scene1 = new Scene(layout, 200, 150);

    popupWindow.setScene(scene1);

    popupWindow.showAndWait();

  }

}
