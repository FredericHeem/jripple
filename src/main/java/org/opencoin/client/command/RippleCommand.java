package org.opencoin.client.command;

public abstract class RippleCommand {
	private String commandName;

	abstract public String toJsonString();
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}
}
