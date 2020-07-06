package Lab5.com.company;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.util.Locale;

public class MyMenu extends MenuBar {
    private static Menu helpMenu = new Menu("Languages");
    private static RadioMenuItem englishLan = new RadioMenuItem("English");
    private static RadioMenuItem frenchLan = new RadioMenuItem("French");
    private static RadioMenuItem spanishLan = new RadioMenuItem("Spanish");
    private static RadioMenuItem rusLan = new RadioMenuItem("Russian");
    private static RadioMenuItem macLan = new RadioMenuItem("Macedonian");
    private static ToggleGroup group = new ToggleGroup();

    public static Locale getActive() {
        if (englishLan.isSelected()) return Locale.ENGLISH;
        if (frenchLan.isSelected()) return Locale.FRENCH;
        return new Locale("sp");
    }

    public static Menu getHelpMenu() {
        return helpMenu;
    }

    public MyMenu() {
        englishLan.setToggleGroup(group);
        frenchLan.setToggleGroup(group);
        spanishLan.setToggleGroup(group);
        rusLan.setToggleGroup(group);
        macLan.setToggleGroup(group);
        englishLan.setSelected(true);
        helpMenu.getItems().addAll( englishLan, frenchLan, spanishLan,rusLan,macLan);
        this.getMenus().addAll(helpMenu);
        settings();
    }

    private void settings() {
        englishLan.setOnAction(actionEvent -> {
            I18N.setLocale(Locale.ENGLISH);
        });
        frenchLan.setOnAction(actionEvent -> {
            I18N.setLocale(Locale.FRENCH);
        });
        spanishLan.setOnAction(actionEvent -> {
            I18N.setLocale(new Locale("sp"));
        });
        rusLan.setOnAction(actionEvent -> {
            I18N.setLocale(new Locale("ru"));
        });
        macLan.setOnAction(actionEvent -> {
            I18N.setLocale(new Locale("mac"));
        });
    }
}
