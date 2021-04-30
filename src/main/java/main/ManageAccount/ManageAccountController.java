package main.ManageAccount;

import main.ManageAccount.Commands.Command;
import main.ManageAccount.Commands.CommandGenerator;
import main.ManageAccount.ActionsProfile;
import java.sql.Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class  ManageAccountController {

    public final String prompt = "Command > ";
	public static final String unknownCommandMsg ="Unknown command";


    private Scanner _sc;
    private ActionsProfile _actions;

    public ManageAccountController(Scanner scanner, ActionsProfile actions){
        this._sc = scanner;
        this._actions = actions;
    }

    public void run() throws SQLException {

            System.out.print(prompt);	
            String s = _sc.nextLine();
            String[] parameters = s.toLowerCase().trim().split(" ");
            System.out.println("[DEBUG] Executing: " + s);
            Command command = CommandGenerator.parseCommand(parameters);
            if (command != null) { command.execute(_actions);}
            else {
                    System.out.println("[ERROR]: "+ unknownCommandMsg + "\n");
            }
	    
    }

}