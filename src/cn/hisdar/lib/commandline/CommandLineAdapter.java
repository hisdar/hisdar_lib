package cn.hisdar.lib.commandline;

import java.util.ArrayList;

import cn.hisdar.lib.log.HLog;

public class CommandLineAdapter {

	private static CommandLineAdapter commandLineAdapter = null;
	
	private ArrayList<String> commandList;
	private ArrayList<CommandLineItem> commandLineItems;
	
	private CommandLineAdapter() {
		commandList = new ArrayList<>();
		commandLineItems = new ArrayList<>();
	}

	public static CommandLineAdapter getInstance() {
		if (commandLineAdapter == null) {
			synchronized (CommandLineAdapter.class) {
				if (commandLineAdapter == null) {
					commandLineAdapter = new CommandLineAdapter();
				}
			}
		}
		
		return commandLineAdapter;
	}
	
	public void addCommand(String command) {
		if (isCommand(command)) {
			return;
		}
		
		commandList.add(command);
	}
	
	public void addCommands(String[] commands) {
		for (int i = 0; i < commands.length; i++) {
			addCommand(commands[i]);
		}
	}
	
	public ArrayList<CommandLineItem> init(String[] commandLineStrings) {
		
		while (commandLineItems.size() > 0) {
			commandLineItems.remove(0);
		}
		
		CommandLineItem currentCommandLine = null;
		for (int i = 0; i < commandLineStrings.length; i++) {
			if (isCommand(commandLineStrings[i])) {
				if (isCommanded(commandLineItems, commandLineStrings[i])) {
					
					continue;
				} else {
					CommandLineItem commandLineItem = new CommandLineItem();
					commandLineItem.setCommand(commandLineStrings[i]);
					commandLineItems.add(commandLineItem);
				}
				
				currentCommandLine = getCommandLineItem(commandLineItems, commandLineStrings[i]);
			} else {
				if (currentCommandLine != null) {
					currentCommandLine.addCommandValue(commandLineStrings[i]);
				} else {
					HLog.il("Drop command line value:" + commandLineStrings[i]);
				}
			}
		}
		
		return commandLineItems;
	}
	
	public boolean isCommand(String command) {
		for (int i = 0; i < commandList.size(); i++) {
			if (commandList.get(i).equals(command)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isCommanded(ArrayList<CommandLineItem> commandLineItems, String command) {
		CommandLineItem commandLineItem = getCommandLineItem(commandLineItems, command);
		if (commandLineItem == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isCommanded(String command) {
		return isCommanded(commandLineItems, command);
	}
	
	private CommandLineItem getCommandLineItem(ArrayList<CommandLineItem> commandLineItems, String command) {
		for (int i = 0; i < commandLineItems.size(); i++) {
			
			HLog.dl(commandLineItems.get(i).getCommand() + " - " + command);
			
			if (commandLineItems.get(i).getCommand().equals(command)) {
				return commandLineItems.get(i);
			}
		}
		
		return null;
	}
	
	public ArrayList<CommandLineItem> getCommandLineItems() {
		return commandLineItems;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("command line:\n");
		for (int i = 0; i < commandLineItems.size(); i++) {
			CommandLineItem currentCommandLineItem = commandLineItems.get(i); 
			stringBuffer.append(currentCommandLineItem.toString());
		}
		
		return stringBuffer.toString();
	}
	
	
}

