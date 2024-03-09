package org.puremvc.java.multicore;

import org.puremvc.java.multicore.interfaces.IFacade;
import org.puremvc.java.multicore.patterns.command.MacroCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

public class Main {
    private static final String MULTITON_KEY = "Example";

    public static void main(String[] args) {
    IFacade facade = Facade.getInstance(MULTITON_KEY, key -> new Facade(key));
    facade.registerCommand("Macro",() -> new MacroCommand());
    facade.sendNotification("Macro");

    }
}
