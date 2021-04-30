package main.ManageAccount.Commands;

import main.ManageAccount.ManageAccountController;
import main.ManageAccount.ActionsProfile;

import java.sql.SQLException;


public abstract class Command {

	  protected final String num;
      protected final String name;
      private final String details; 
	  private final String help;

	  protected static final String incorrectNumberOfArgsMsg = "Incorrect number of arguments";
	  protected static final String incorrectArgsMsg = "Incorrect arguments format";
	  
	  public Command(String num, String name, String details, String help){
	    this.num = num;
	  	this.name = name;
	  	this.details = details;
	    this.help = help;
	  }
	  
	  //M�todos abstractos
	  public abstract void execute(ActionsProfile profile) throws SQLException;
	  
	  public abstract Command parse(String[] commandWords);
	  
	  //Otros m�todos
	  protected boolean matchCommandName(String name) {
		    return this.num.equalsIgnoreCase(name) || this.name.equalsIgnoreCase(name);
	  }
	  
	  protected Command parseNoParamsCommand(String[] words) {
	
			if (matchCommandName(words[0])) {
				if (words.length != 1) {
					System.err.println(incorrectArgsMsg);
					return null;
				}
				return this;
			}
			
			return null;
	  }
	  
	  public String helpText(){
	    return help + ": " + details + "\n";
	  }
}