package com.stilbruch.stilbot.command

class Command(name: String,
              maxArgs: Int = -1,
              minArgs: Int = -1,
              deleteCommandMessage: Boolean = true,
              executor: (CommandContext => Unit) = (cmd => ())) {

  def execute(context: CommandContext): Unit = {

    if (minArgs != -1 && context.commandArgs.length < minArgs){
      context.sendErrorMessage("Not enough arguments!")
      ()
    } else if (maxArgs != -1 && context.commandArgs.length > maxArgs){
      context.sendErrorMessage("Too many arguments")
      ()
    }

    if (deleteCommandMessage) context.message.delete().queue()
    executor.apply(context)
  }

  def matchesCommand(input: String) = {
    name.toLowerCase.equals(input.toLowerCase)
  }

}