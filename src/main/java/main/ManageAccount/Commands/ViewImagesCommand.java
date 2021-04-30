package main.ManageAccount.Commands;

import main.ManageAccount.ManageAccountController;
import main.ManageAccount.ActionsProfile;

import java.sql.SQLException;


public class ViewImagesCommand extends Command{

	public ViewImagesCommand(String num, String name, String details, String help) {
		super(num, name, details, help);
	}

	@Override
	public void execute(ActionsProfile profile) throws SQLException {
		profile.viewMyImages();
	}

	@Override
	public Command parse(String[] commandWords) {
		return parseNoParamsCommand(commandWords);
	}

}