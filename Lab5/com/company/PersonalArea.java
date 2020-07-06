package Lab5.com.company;


import Client.Client;
import Lab5.commands.A_command;
import Lab5.commands.Command_Clear;
import Lab5.commands.Command_Info;

import Server.Command_Disconnect;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class PersonalArea extends Application {
    private MyMenu menu = new MyMenu();
    private static String Login;
    private static Button info = new Button("Info");
    private static Button clear = new Button("Clear");
    private static avatar avatar = new avatar();
    private static Button sendCommand;
    private static TextField table_head = new TextField("Table");
    private static TextField answers_head = new TextField("Server responses");
    private static TextArea textArea;
    private static TextField commands = new TextField();
    private static AnchorPane pane;
    private static Scene scene;
    private static ScatterChart<Number, Number> numberLineChart;
    private static ArrayList<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
    private static Table table;
    private static Line line;
    private static Line line2;
    private static Line line3;
    private static Group root;
    private static CommandList commandlist;
    private static ArrayList<ObservableList<XYChart.Data<Number, Number>>> datasList = new ArrayList<>();
    Filter filter;

    public PersonalArea() {

    }

    public void showAnsw() {
        textArea.setText(textArea.getText() + "\n" + Client.ReceiveData());
    }

    public PersonalArea(String login) {
        setLocale(MyMenu.getActive());
        setText();
        Login = login;
        avatar.getLogin().setText(login);
        textArea = new TextArea();
        commandlist = new CommandList(commands, sendCommand);
        table = new Table();
        filter = new Filter(table);
        pane = new AnchorPane();
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        numberLineChart = new ScatterChart<>(x, y);
        scene = new Scene(pane, ScreenParametrs.hor, ScreenParametrs.vert);
        line = new Line();
        line2 = new Line();
        line3 = new Line();
        root = new Group(line, line2, line3);
    }

    public static String getLogin() {
        return Login;
    }

    @Override
    public void start(Stage stage) throws SQLException {
        stage.setResizable(false);
        stage.setMaximized(true);
        addSerries();
        addTooltips();
        settings();
        add();
        addSearch();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Client.SendData(new Command_Disconnect(PersonalArea.getLogin()));
            System.exit(1);
        });
    }

    private static void addTooltips() {
        for (int i = 0; i < numberLineChart.getData().size(); i++) {
            ObservableList<XYChart.Data<Number, Number>> dataList = ((XYChart.Series) numberLineChart.getData().get(i)).getData();
            dataList.forEach(data -> {
                Node node = data.getNode();
                Tooltip tooltip = new Tooltip('(' + data.getXValue().toString()
                        + data.getYValue().toString() + ')');
                Tooltip.install(node, tooltip);
            });
        }
    }

    private static void addSearch() {
        for (int i = 0; i < numberLineChart.getData().size(); i++) {
            ObservableList<XYChart.Data<Number, Number>> dataList = ((XYChart.Series) numberLineChart.getData().get(i)).getData();
            dataList.forEach(data -> {
                data.getNode().setOnMouseClicked(mouseEvent -> {
                    TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
                    selectionModel.select(A_command.getTicketById(Long.parseLong(data.getNode().getId())));
                    table.scrollTo(A_command.getTicketById(Long.parseLong(data.getNode().getId())));
                });

            });
        }
    }

    private static void addSerries() {
        datasList.clear();
        numberLineChart.getData().clear();
        seriesList.clear();
        for (String login : A_command.getAllOwners()) {
            ObservableList<XYChart.Data<Number, Number>> datas = FXCollections.observableArrayList();
            ArrayList<Ticket> userTickets = A_command.getAllUsersTicket(login);
            if (userTickets.size() == 0) continue;
            for (int i = 0; i < userTickets.size(); i++) {
                datas.add(new XYChart.Data(userTickets.get(i).getCoordinates().getX(), userTickets.get(i).getCoordinates().getY()));
                datas.get(i).setNode(new StackPane());
                datas.get(i).getNode().setId(Long.toString(userTickets.get(i).getId()));
            }

            datasList.add(datas);
            XYChart.Series<Number, Number> series = new XYChart.Series<>(datas);
            series.setName(login);
            seriesList.add(series);
        }
        numberLineChart.getData().addAll(seriesList);
    }

    public static void updateGraphic() {
        addSerries();
        addSearch();
        addTooltips();
    }

    public static void updateTable() {
        table.updateData();
        System.out.println("Новая таблица");
    }

    private void add() {
        pane.getChildren().addAll(numberLineChart, root, avatar.getPane(), textArea,
                table_head, answers_head, commands, commandlist, sendCommand, table, clear, info, Filter.getAPane(), menu);
    }

    public static void writeText() {
        textArea.setText(PersonalArea.textArea.getText() + "\n" + Client.ReceiveData());
    }

    public static void writeText(ObservableList<Ticket> tickets) {
        for (Ticket t : tickets) {
            textArea.setText(PersonalArea.textArea.getText() + "\n" + t);
        }
    }

    public static void writeText(String text) {
        textArea.setText(PersonalArea.textArea.getText() + "\n" + text);
    }

    public static TextArea getTextArea() {
        return textArea;
    }

    private void settings() {
        avatar.getLogin().setEditable(false);
        table_head.setEditable(false);
        answers_head.setEditable(false);
        clear.setOnMouseClicked(mouseEvent -> {
            Client.SendData(new Command_Clear());
            textArea.setText(PersonalArea.textArea.getText() + "\n" + Client.ReceiveData());
        });
        info.setOnMouseClicked(mouseEvent -> {
            Client.SendData(new Command_Info());
            textArea.setText(PersonalArea.textArea.getText() + "\n" + Client.ReceiveData());
        });
        pane.setLeftAnchor(sendCommand, ScreenParametrs.hor * 0.5 + 50);
        pane.setTopAnchor(sendCommand, 520d);
        pane.setRightAnchor(sendCommand, ScreenParametrs.hor * 0.25);

        textArea.setPrefColumnCount(40);
        textArea.setPrefRowCount(100);

        pane.setLeftAnchor(textArea, ScreenParametrs.hor * 0.5);
        pane.setTopAnchor(textArea, ScreenParametrs.vert * 2 / 3d + 36);
        pane.setBottomAnchor(textArea, 1d);
        pane.setRightAnchor(textArea, 1d);

        pane.setLeftAnchor(table_head, 10d);
        pane.setTopAnchor(table_head, ScreenParametrs.vert * 2 / 3d + 7);
        pane.setRightAnchor(table_head, ScreenParametrs.hor * 0.5 + 10);

        pane.setLeftAnchor(answers_head, ScreenParametrs.hor * 0.5 + 10);
        pane.setTopAnchor(answers_head, ScreenParametrs.vert * 2 / 3d + 7.);
        pane.setRightAnchor(answers_head, 10d);

        pane.setLeftAnchor(numberLineChart, 20D);
        pane.setTopAnchor(numberLineChart, 20D);
        pane.setBottomAnchor(numberLineChart, ScreenParametrs.vert / 3D);
        pane.setRightAnchor(numberLineChart, ScreenParametrs.hor * 0.5);

        pane.setLeftAnchor(commands, ScreenParametrs.hor * 0.5 + 50);
        pane.setTopAnchor(commands, 60D);
        pane.setRightAnchor(commands, ScreenParametrs.hor * 0.25);

        pane.setLeftAnchor(commandlist, ScreenParametrs.hor * 0.5 + 50);
        pane.setTopAnchor(commandlist, 100D);
        pane.setRightAnchor(commandlist, ScreenParametrs.hor * 0.25);

        pane.setLeftAnchor(info, ScreenParametrs.hor * 0.5 + 250);
        pane.setTopAnchor(info, 570d);
        pane.setRightAnchor(info, ScreenParametrs.hor * 0.25);

        pane.setLeftAnchor(clear, ScreenParametrs.hor * 0.5 + 50);
        pane.setTopAnchor(clear, 570d);
        pane.setRightAnchor(clear, ScreenParametrs.hor * 0.25 + 250);


        pane.setLeftAnchor(Filter.getAPane(), ScreenParametrs.hor * 0.5 + 500);
        pane.setTopAnchor(Filter.getAPane(), 130D);


        pane.setLeftAnchor(table, 1D);
        pane.setTopAnchor(table, ScreenParametrs.vert * 2 / 3d + 36);
        pane.setBottomAnchor(table, 1d);
        pane.setRightAnchor(table, ScreenParametrs.hor / 2d + 2);

        line.setStartX(0);
        line.setStartY(ScreenParametrs.vert * 2 / 3d + 5);
        line.setEndX(ScreenParametrs.hor);
        line.setEndY(ScreenParametrs.vert * 2 / 3d + 5);

        line2.setStartX(ScreenParametrs.hor / 2d);
        line2.setStartY(ScreenParametrs.vert * 2 / 3d + 5);
        line2.setEndX(ScreenParametrs.hor / 2d);
        line2.setEndY(ScreenParametrs.vert);

        line3.setStartX(0);
        line3.setStartY((ScreenParametrs.vert * 2 / 3d + 35));
        line3.setEndX(ScreenParametrs.hor);
        line3.setEndY(ScreenParametrs.vert * 2 / 3d + 35);
    }

    private void setLocale(Locale locale) {
        I18N.setLocale(locale);

    }

    public static void setText() {
        sendCommand = I18N.buttonForKey("button.sendCom");
        clear = I18N.buttonForKey("button.clear");
        info = I18N.buttonForKey("button.info");
        table_head.setText(I18N.textForKey("text.tablehead"));
        answers_head.setText(I18N.textForKey("text.serverresponses"));
    }
}
