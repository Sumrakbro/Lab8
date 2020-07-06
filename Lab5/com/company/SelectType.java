package Lab5.com.company;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class SelectType extends ComboBox {
    TicketType type=null;
    public SelectType(){
        super(FXCollections.observableArrayList("Null","Cheap","Budgetary","Usual","Vip"));
        settings();
    }
private void settings(){
   this.setOnAction(event ->System.out.println(this.getValue()));
}
}
