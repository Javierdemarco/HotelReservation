package src.ui;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    List<String> menuItems = new ArrayList<String>();

    public MainMenu() {
        menuItems.add("Main Menu:");
        menuItems.add("1. Find and reserve a room");
        menuItems.add("2. See my reservation");
        menuItems.add("3. Create an account");
        menuItems.add("4. Admin");
        menuItems.add("5. Exit");
        menuItems.add("Please select and option");
    }

    @Override
    public String toString() {
        String rString = "";
        for (String each : menuItems) {
            rString += each + "\n";
        }
        return rString;

    }
}
