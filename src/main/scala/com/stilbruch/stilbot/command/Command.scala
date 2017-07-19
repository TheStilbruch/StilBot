package com.stilbruch.stilbot.command

class Command(builder: CommandBuilder) {

  val name = builder.name
  val maxArgs = builder.maxArgs
  val minArgs = builder.minArgs
  val deleteCommandMessage = builder.deleteCommandMessage
  val executor = builder.executor

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

  def matchesCommand(input: String): Boolean = {
    name.toLowerCase.equals(input.toLowerCase)
  }

}

class CommandBuilder(commandName: String) {

  val name = commandName
  var maxArgs = -1
  var minArgs = -1
  var deleteCommandMessage = true
  var executor: CommandContext => Unit = _

  def withMaxArgs(num: Int): CommandBuilder = {
    this.maxArgs = num
    this
  }

  def withMinArgs(num: Int): CommandBuilder = {
    this.minArgs = num
    this
  }

  def withDeleteCommandMessage(delete: Boolean): CommandBuilder = {
    this.deleteCommandMessage = delete
    this
  }

  def withExecutor(executor: CommandContext => Unit): CommandBuilder = {
    this.executor = executor
    this
  }

  def build(): Command = {
    new Command(this)
  }

}
