package com.stilbruch.stilbot.command

import com.stilbruch.stilbot.Stilbot
import net.dv8tion.jda.core.OnlineStatus
import net.dv8tion.jda.core.entities.Game.GameType
import net.dv8tion.jda.core.entities.impl.GameImpl
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

import scala.collection.mutable.ListBuffer

class CommandManager extends ListenerAdapter {

  val commands: ListBuffer[Command] = new ListBuffer[Command]

  //Please note that this command does not work.
  commands += new CommandBuilder("purple")
    .withMaxArgs(0)
      .withExecutor(context => {
        Stilbot.getJda.getPresence.setGame(null)
        Stilbot.getJda.getPresence.setPresence(new GameImpl("I wanna be purple", "https://discord.gg/JvVdMk4", GameType.TWITCH), false)
      })
    .build()

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if (event.getAuthor.getIdLong != Stilbot.getClientId || !event.getMessage.getRawContent.startsWith(Stilbot.commandPrefix)){
      return
    }

    //TODO: Use multithreading to handle commands
    val context: CommandContext = new CommandContext(event)
    if (context.commandName.isEmpty) return
    val cmd = commands.find(_.matchesCommand(context.commandName))

    cmd match {
      case Some(command) => command.execute(context)
      case None => context.sendErrorMessage(s"Invalid Command: **${context.commandName}**")
    }
  }

}
