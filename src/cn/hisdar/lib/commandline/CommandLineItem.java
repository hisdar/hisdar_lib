package cn.hisdar.lib.commandline;

import java.util.ArrayList;

public class CommandLineItem {
	private ArrayList<String> commandValues;
	private String command;
	
	public CommandLineItem() {
		
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public void addCommandValue(String commandValue) {
		for (int i = 0; i < commandValues.size(); i++) {
			if (commandValues.get(i).equals(commandValue)) {
				return;
			}
		}
		
		commandValues.add(commandValue);
	}
	
	public String getCommand() {
		return command;
	}
	
	public ArrayList<String> getCommandValues() {
		return commandValues;
	}
	
	public String toString() {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(getCommand() + ":");
		for (int j = 0; j < getCommandValues().size(); j++) {
			stringBuffer.append(getCommandValues().get(j));
			if (j + 1 <= getCommandValues().size()) {
				stringBuffer.append(" ");
			}
		}
		
		return stringBuffer.toString();
	}
}
