package main.ManageAccount.Commands;
import main.ManageAccount.Commands.ViewImagesCommand;
import main.ManageAccount.Commands.FriendsCommand;
import main.ManageAccount.Commands.ManageInformationCommand;
import main.ManageAccount.Commands.UnsubscribeCommand;
import main.ManageAccount.Commands.HelpCommand;

public class CommandGenerator {
	
	private static final Command[] avaiableCommands = {
			new ViewImagesCommand("1", "View Images", "this will display your images", "1. View Images : "),
			new FriendsCommand("2", "Friends", "manage your friends", "2. Friends: "),
            new ManageInformationCommand("3", "Manage your Information", "edit profile", "3. Manage Information: "),
			new UnsubscribeCommand("4", "Unsubscribe", "delete your account", "4.Unsubscribe: " ),
			new HelpCommand("5", "Help", "show help", "5.Help: ")
	};

	public static Command parseCommand(String[] commandWords) {
		//buscamos el comando y en caso de encontrarlo lo retornamos sino devolvemos null
		for(Command command : avaiableCommands) {
			if(command.parse(commandWords) != null)
				return command;
		}
		return null;		
	}
	
	public static String commandHelp() {
		String help = "Available commands:" + "\n";
		for(Command command : avaiableCommands)  {
			help += command.helpText();
		}
		return help;
	}
	
}
