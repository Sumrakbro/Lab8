package Lab5.com.company;


import Client.Client;
import Server.Command_Disconnect;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class avatar {
   private static Button quit = new Button("Quit");
   private static TextField login = new TextField();
   private static AnchorPane pane = new AnchorPane();
   private static Circle circle = new Circle(30);
   private static Line hor1 = new Line(ScreenParametrs.hor - 2 * circle.getRadius(), circle.getRadius(), ScreenParametrs.hor - 300, circle.getRadius());
   private static Line hor2 = new Line(ScreenParametrs.hor, circle.getRadius() * 2, ScreenParametrs.hor - 300, circle.getRadius() * 2);
   private static Line ver = new Line(ScreenParametrs.hor - 300, circle.getRadius() * 2, ScreenParametrs.hor - 300, 0);

    Image image = new Image("https://sun9-33.userapi.com/c847124/v847124762/108a5a/UcWg4vk4VRo.jpg");

    public TextField getLogin() {
        return login;
    }

    public avatar() {
        add();
        settings();
    }

    public Circle getCircle() {
        return circle;
    }

    public AnchorPane getPane() {
        return pane;
    }

    private void add() {
        pane.getChildren().addAll(circle, login, ver, hor2, hor1, quit);
    }

    private void settings() {
        pane.setLeftAnchor(circle, ScreenParametrs.hor - 2 * circle.getRadius());
        pane.setTopAnchor(circle, 1d);
        pane.setRightAnchor(circle, circle.getRadius());
        pane.setLeftAnchor(login, ScreenParametrs.hor - 290d);
        pane.setTopAnchor(login, 3d);
        pane.setRightAnchor(login, circle.getRadius() * 2 + 30);
        pane.setLeftAnchor(quit, ScreenParametrs.hor - 290d);
        pane.setTopAnchor(quit, 3d);
        pane.setRightAnchor(quit, circle.getRadius() * 2 + 30);
        circle.setFill(new ImagePattern(image));
        quit.setStyle("-fx-background-color: transparent;");
        pane.setTopAnchor(quit, circle.getRadius());
        pane.setRightAnchor(quit,100d);
        quit.setOnMouseClicked(mouseEvent -> {
            Client.SendData(new Command_Disconnect(PersonalArea.getLogin()));
            System.exit(1);
        });
    }
    public static void setText() {
        quit = I18N.buttonForKey("button.quit");
    }
}
