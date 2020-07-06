package Lab5.com.company;

import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Filter extends Region {
    private static Button filter = new Button("Filter");
    private static Button clear = new Button("Clear");
    private static Label idLabel = new Label("Id:");
    private static TextField idField = new TextField();
    private static Label ownerLabel = new Label("Owner:");
    private static TextField ownerField = new TextField();
    private static Label titleLabel = new Label("Title:");
    private static TextField titleField = new TextField();
    private static Label timeLabel = new Label("Creation time:");
    private static TextField timeField = new TextField();
    private static Label priceLabel = new Label("Price:");
    private static TextField priceField = new TextField();
    private static Label xLabel = new Label("X:");
    private static TextField xField = new TextField();
    private static Label yLabel = new Label("Y:");
    private static TextField yField = new TextField();
    private static Label perIdLabel = new Label("Person id:");
    private static TextField perIdField = new TextField();
    private static Label perWeightLabel = new Label("Person weight:");
    private static TextField perWeightField = new TextField();
    private static Button selectTypeButton = new Button("Type");
    private static VBox typeVBox = new VBox(10);
    private static CheckBox nullCheckBox = new CheckBox("Null");
    private static CheckBox budgetaryCheckBox = new CheckBox("Budgetary");
    private static CheckBox cheapCheckBox = new CheckBox("Cheap");
    private static CheckBox usualCheckBox = new CheckBox("Usual");
    private static CheckBox vipCheckBox = new CheckBox("Vip");
    private static AnchorPane pane = new AnchorPane();
    private static Table table;

    public Filter(Table table) {
        this.table = table;
        add();
        settings();
        setFilterSettings();
        setAnchors();
    }

    public static AnchorPane getAPane() {
        return pane;
    }

    private void add() {
        typeVBox.getChildren().addAll(selectTypeButton, nullCheckBox, budgetaryCheckBox, cheapCheckBox, usualCheckBox, vipCheckBox);
        pane.getChildren().addAll(idLabel, titleLabel, ownerLabel, timeLabel, xLabel,
                yLabel, priceLabel, perIdLabel, perWeightLabel, idField, titleField, ownerField, timeField, xField,
                yField, priceField, perIdField, perWeightField, typeVBox, filter, clear);

    }

    private void settings() {
        clear.setPrefWidth(70);
        filter.setPrefWidth(90);
        selectTypeButton.setPrefSize(50, perIdField.getHeight());

        nullCheckBox.setVisible(false);
        budgetaryCheckBox.setVisible(false);
        cheapCheckBox.setVisible(false);
        vipCheckBox.setVisible(false);
        usualCheckBox.setVisible(false);
        selectTypeButton.setOnMouseClicked(mouseEvent -> {
            if (!nullCheckBox.isVisible()) {
                nullCheckBox.setVisible(true);
                budgetaryCheckBox.setVisible(true);
                cheapCheckBox.setVisible(true);
                vipCheckBox.setVisible(true);
                usualCheckBox.setVisible(true);

            } else {
                nullCheckBox.setVisible(false);
                budgetaryCheckBox.setVisible(false);
                cheapCheckBox.setVisible(false);
                vipCheckBox.setVisible(false);
                usualCheckBox.setVisible(false);

            }
        });
        xField.setPrefWidth(40);
        yField.setPrefWidth(xField.getPrefWidth());
    }

    private void setAnchors() {
        pane.setTopAnchor(idField, 1d);
        pane.setTopAnchor(titleField, 30d);
        pane.setTopAnchor(ownerField, 60d);
        pane.setTopAnchor(timeField, 90d);
        pane.setTopAnchor(xField, 120d);
        pane.setTopAnchor(yField, 120d);
        pane.setTopAnchor(priceField, 150d);
        pane.setTopAnchor(perIdField, 180d);
        pane.setTopAnchor(perWeightField, 210d);
        pane.setTopAnchor(filter, 270d);
        pane.setTopAnchor(clear, 240d);
        pane.setTopAnchor(typeVBox, 240d);

        pane.setLeftAnchor(idField, 100d);
        pane.setLeftAnchor(titleField, 100d);
        pane.setLeftAnchor(ownerField, 100d);
        pane.setLeftAnchor(timeField, 105d);
        pane.setLeftAnchor(xField, 110d);
        pane.setLeftAnchor(yField, 190d);
        pane.setLeftAnchor(priceField, 100d);
        pane.setLeftAnchor(perIdField, 100d);
        pane.setLeftAnchor(perWeightField, 100d);
        pane.setLeftAnchor(xLabel, 100d);
        pane.setLeftAnchor(yLabel, 175d);
        pane.setLeftAnchor(typeVBox, 100d);
        pane.setLeftAnchor(clear, 150d);
        pane.setLeftAnchor(filter, 150d);

        pane.setTopAnchor(idLabel, 1d);
        pane.setTopAnchor(titleLabel, 30d);
        pane.setTopAnchor(ownerLabel, 60d);
        pane.setTopAnchor(timeLabel, 90d);
        pane.setTopAnchor(xLabel, 120d);
        pane.setTopAnchor(yLabel, 120d);
        pane.setTopAnchor(priceLabel, 150d);
        pane.setTopAnchor(perIdLabel, 180d);
        pane.setTopAnchor(perWeightLabel, 210d);

    }

    private void setFilterSettings() {
        filter.setOnMouseClicked(mouseEvent -> {
            String owner = readOwner();
            Long id = readId();
            String name = readTitle();
            Double x = readX();
            Float y = readY();
            Timestamp creationDate = readTime();
            Double price = readPrice();
            ArrayList<TicketType> types = new ArrayList<>();
            String passportID = readPerId();
            Long weight = readWeight();
            FilteredList<Ticket> filteredList;
            types.clear();
            if (nullCheckBox.isSelected()) types.add(null);
            if (budgetaryCheckBox.isSelected()) types.add(TicketType.BUDGETARY);
            if (cheapCheckBox.isSelected()) types.add(TicketType.CHEAP);
            if (usualCheckBox.isSelected()) types.add(TicketType.USUAL);
            if (vipCheckBox.isSelected()) types.add(TicketType.VIP);
            filteredList = table.getItems().filtered(elem -> {
                if (owner != null) {
                    if (elem.getOwner() == null) return false;
                    if (!elem.getOwner().equals(owner)) return false;
                }
                if (id != null) {
                    if (elem.getId() != id) return false;

                }

                if (creationDate != null) {
                    if (!creationDate.equals(elem.getCreationDate())) return false;
                }
                if (name != null) {
                    if (!name.equals(elem.getName())) return false;
                }

                if (price != null) {
                    if (price != elem.getPrice()) return false;
                }

                if (!types.isEmpty()) {
                    if (!equalsTypes(types, elem.getType())) return false;
                }

                if (x != null) {
                    if (x != elem.getCoordinates().getX()) return false;
                }

                if (y != null) {
                    if (y != elem.getCoordinates().getY()) return false;
                }

                if (weight != null) {
                    if (elem.getPerson() == null | elem.getPerson().getWeight() != weight) return false;
                }

                if (passportID != null) {
                    if (elem.getPerson() == null | !elem.getPerson().getPassportID().equals(passportID)) return false;
                }

                return true;
            });
            if (filteredList.isEmpty()) PersonalArea.writeText("В списке не удалось найти нужных элементов");
            PersonalArea.writeText(filteredList);

        });
    }

    private String readOwner() {
        if (ownerField.getText().isEmpty()) return null;
        String owner = ownerField.getText();
        return owner;
    }

    private String readTitle() {
        if (titleField.getText().isEmpty()) return null;
        String title = titleField.getText();
        return title;
    }

    private String readPerId() {
        if (perIdField.getText().isEmpty()) return null;
        String perId = perIdField.getText();
        return perId;
    }

    private Long readWeight() {
        if (perWeightField.getText().isEmpty()) return null;
        long weight;
        try {
            weight = Long.parseLong(perWeightField.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(perWeightField);
            weight = 0L;
        }
        return weight;
    }

    private Long readId() {
        if (idField.getText().isEmpty()) {
            return null;
        }
        long id;
        try {
            id = Long.parseLong(idField.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(idField);
            id = 0L;
        }
        return id;
    }

    private Float readY() {
        if (yField.getText().isEmpty()) return null;
        float y;
        try {
            y = Float.parseFloat(yField.getText());
        } catch (IllegalArgumentException e) {
            y = Float.NEGATIVE_INFINITY;
            makeredborder(yField);
        }
        return y;
    }

    private Double readX() {
        if (xField.getText().isEmpty()) return null;
        double x;
        try {
            x = Double.parseDouble(xField.getText());
        } catch (IllegalArgumentException e) {
            x = Double.NEGATIVE_INFINITY;
            makeredborder(xField);
        }
        return x;
    }

    private Double readPrice() {
        if (priceField.getText().isEmpty()) return null;
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
        } catch (IllegalArgumentException e) {
            price = Double.NEGATIVE_INFINITY;
            makeredborder(priceField);
        }
        return price;
    }

    private Timestamp readTime() {
        if (timeField.getText().isEmpty()) return null;
        Timestamp time;
        try {
            time = Timestamp.valueOf(timeField.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(timeField);
            return new Timestamp(new Date().getTime());
        }
        return time;
    }

    private boolean equalsTypes(ArrayList<TicketType> list, TicketType type) {
        for (TicketType t : list) {
            System.out.println(t);
            if (t == null && type == null) return true;
            if (t == null) return false;
            if (t.compareTo(type) == 0) return true;
        }
        return false;
    }

    public static void setText() {
        titleLabel.setText(I18N.textForKey("text.name"));
        ownerLabel.setText(I18N.textForKey("text.owner"));
        perWeightLabel.setText(I18N.textForKey("text.weight"));
        timeLabel.setText(I18N.textForKey("text.time"));
        priceLabel.setText(I18N.textForKey("text.price"));
        filter.setText(I18N.textForKey("button.filter"));
        clear.setText(I18N.textForKey("button.clear"));
        selectTypeButton.setText(I18N.textForKey("text.type"));
    }
    private void makeredborder(TextField field){
       field.setStyle("-fx-border-color: red;");
    }
}





