package Lab5.com.company;

import Client.Client;
import Lab5.commands.A_command;
import Lab5.commands.Command_Update;
import Lab5.commands.Command_UpdateTable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;

import java.sql.Timestamp;

public class Table extends TableView<Ticket> {
    private static ObservableList<Ticket> list = FXCollections.observableArrayList(A_command.getSet());
    private static TableColumn<Ticket, Long> idColum = new TableColumn<>("id");
    private static TableColumn<Ticket, String> nameColumn = new TableColumn<>("Name");
    private static TableColumn<Ticket, String> ownerColumn = new TableColumn<>("Owner");
    private static TableColumn<Ticket, Coordinates> coordinatesColumn = new TableColumn<>("Coordinates");
    private static TableColumn<Ticket, Double> coordinate_xColumn = new TableColumn<>("X");
    private static TableColumn<Ticket, Float> coordinate_yColumn = new TableColumn<>("Y");
    private static TableColumn<Ticket, Timestamp> dateColumn = new TableColumn<>("Creation time");
    private static TableColumn<Ticket, Double> priceColumn = new TableColumn<>("Price");
    private static TableColumn<Ticket, TicketType> typeColum = new TableColumn<>("Type");
    private static TableColumn<Ticket, Person> personColumn = new TableColumn<>("Person");
    private static TableColumn<Ticket, String> pass_idColumn = new TableColumn<>("ID");
    private static TableColumn<Ticket, Long> weightColumn = new TableColumn<>("Weight");

    public Table() {
        this.setItems(list);
        settings();
        add();
    }

    public void updateData() {
        list.clear();
        list.addAll(FXCollections.observableArrayList(A_command.getSet()));
    }

    private void add() {
        this.getColumns().add(idColum);
        this.getColumns().add(ownerColumn);
        this.getColumns().add(nameColumn);
        this.getColumns().add(dateColumn);
        this.getColumns().add(priceColumn);
        this.getColumns().add(typeColum);
        coordinatesColumn.getColumns().addAll(coordinate_xColumn, coordinate_yColumn);
        this.getColumns().add(coordinatesColumn);
        personColumn.getColumns().addAll(pass_idColumn, weightColumn);
        this.getColumns().add(personColumn);
    }

    private void settings() {
        setCellValFac();
        setCellFactories();
        setEditCom();
        this.setEditable(true);
    }

    private void setCellValFac() {
        nameColumn.setText("Dima");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        idColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        coordinate_xColumn.setCellValueFactory(cellData -> (new SimpleDoubleProperty(cellData.getValue().getCoordinates().getX())).asObject());
        pass_idColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPerson() != null)
                return new SimpleStringProperty(cellData.getValue().getPerson().getPassportID());
            else return null;
        });
        weightColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPerson() != null)
                return new SimpleLongProperty(cellData.getValue().getPerson().getWeight()).asObject();
            else return null;
        });
        coordinate_yColumn.setCellValueFactory(cellData -> (new SimpleFloatProperty(cellData.getValue().getCoordinates().getY())).asObject());
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColum.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColum.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getType()));
    }

    private void setCellFactories() {
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        coordinate_xColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        coordinate_yColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        weightColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        pass_idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColum.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(TicketType.values())));
    }

    public static void setText() {
        nameColumn.setText(I18N.textForKey("text.name"));
        ownerColumn.setText(I18N.textForKey("text.owner"));
        coordinatesColumn.setText(I18N.textForKey("text.coordinates"));
        weightColumn.setText(I18N.textForKey("text.weight"));
        dateColumn.setText(I18N.textForKey("text.time"));
        priceColumn.setText(I18N.textForKey("text.price"));
        personColumn.setText(I18N.textForKey("text.person"));
        typeColum.setText(I18N.textForKey("text.type"));
    }

    private void setEditCom() {
        weightColumn.setOnEditCommit((TableColumn.CellEditEvent<Ticket, Long> t) -> {
            TablePosition<Ticket, Long> pos = t.getTablePosition();
            Long newWeight = t.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
            Ticket ticket = t.getTableView().getItems().get(row);
            ticket.getPerson().setWeight(newWeight);
            Client.SendData(new Command_Update(ticket.getId(), ticket));
            PersonalArea.writeText();
            Client.SendData(new Command_UpdateTable());
            Client.ReceiveData();
            } else {
                this.updateData();
            }
        });
        pass_idColumn.setOnEditCommit((TableColumn.CellEditEvent<Ticket, String> t) -> {
            TablePosition<Ticket, String> pos = t.getTablePosition();
            String newId = t.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
            Ticket ticket = t.getTableView().getItems().get(row);
            ticket.getPerson().setPassportID(newId);
            Client.SendData(new Command_Update(ticket.getId(), ticket));
            PersonalArea.writeText();
            Client.SendData(new Command_UpdateTable());
            Client.ReceiveData();
            } else {
                this.updateData();
            }
        });
        priceColumn.setOnEditCommit((TableColumn.CellEditEvent<Ticket, Double> t) -> {
            TablePosition<Ticket, Double> pos = t.getTablePosition();
            Double newPrice = t.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
            Ticket ticket = t.getTableView().getItems().get(row);
            ticket.setPrice(newPrice);
            Client.SendData(new Command_Update(ticket.getId(), ticket));
            PersonalArea.writeText();
            Client.SendData(new Command_UpdateTable());
            Client.ReceiveData();
            } else {
                this.updateData();
            }
        });
        coordinate_xColumn.setOnEditCommit((TableColumn.CellEditEvent<Ticket, Double> t) -> {
            TablePosition<Ticket, Double> pos = t.getTablePosition();
            Double newX = t.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
            Ticket ticket = t.getTableView().getItems().get(row);
            ticket.getCoordinates().setX(newX);
            Client.SendData(new Command_Update(ticket.getId(), ticket));
            PersonalArea.writeText();
            Client.SendData(new Command_UpdateTable());
            Client.ReceiveData();
            PersonalArea.updateGraphic();
            PersonalArea.updateTable();
            } else {
                this.updateData();
            }
        });
        coordinate_yColumn.setOnEditCommit((TableColumn.CellEditEvent<Ticket, Float> t) -> {
            TablePosition<Ticket, Float> pos = t.getTablePosition();
            Float newY = t.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
                Ticket ticket = t.getTableView().getItems().get(row);
                ticket.getCoordinates().setY(newY);
                Client.SendData(new Command_Update(ticket.getId(), ticket));
                PersonalArea.writeText();
                Client.SendData(new Command_UpdateTable());
                Client.ReceiveData();
                PersonalArea.updateGraphic();
                PersonalArea.updateTable();
            } else {
                this.updateData();
            }
        });
        nameColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Ticket, String> event) -> {
                    TablePosition<Ticket, String> pos = event.getTablePosition();
                    String newName = event.getNewValue();
                    int row = pos.getRow();
                    if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
                        Ticket ticket = event.getTableView().getItems().get(row);
                        ticket.setName(newName);
                        Client.SendData(new Command_Update(ticket.getId(), ticket));
                        Client.SendData(new Command_UpdateTable());
                        Client.ReceiveData();
                        PersonalArea.writeText();
                    } else {
                        this.updateData();
                    }
                });
        typeColum.setOnEditCommit((TableColumn.CellEditEvent<Ticket, TicketType> event) -> {
            TablePosition<Ticket, TicketType> pos = event.getTablePosition();
            TicketType newType = event.getNewValue();
            int row = pos.getRow();
            if (PersonalArea.getLogin().equals(this.getColumns().get(1).getCellData(row))) {
                Ticket ticket = event.getTableView().getItems().get(row);
                ticket.setType(newType);
                Client.SendData(new Command_Update(ticket.getId(), ticket));
                Client.SendData(new Command_UpdateTable());
                Client.ReceiveData();
                PersonalArea.writeText();
            } else {
                this.updateData();
            }
        });

    }

}
