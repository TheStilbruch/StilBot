package com.stilbruch.stilbot.command

import com.stilbruch.stilbot.Stilbot
import java.awt.Color
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

class CommandContext(event: MessageReceivedEvent) {

  val author = event.getAuthor
  val guild = event.getGuild
  val message = event.getMessage
  val channel = event.getTextChannel

  val allArgs: Array[String] = message.getRawContent.split(" ")
  val commandName = allArgs(0).replace(Stilbot.commandPrefix, "")
  val commandArgs = allArgs.slice(1, allArgs.length)

  def sendErrorMessage(message: String): Unit = {

    channel.sendMessage(
      new EmbedBuilder()
        .setTitle(":no_entry: Error")
        .setDescription(message)
        .setColor(Color.getColor("#F44336"))
        .setFooter("Stilbot", null)
        .build()
    ).queue()
    ()
  }

  def combineArgs(start: Int = 0, delim: String = " "): String = {
    combineArgs(start, commandArgs.length, delim)
  }

  def combineArgs(start: Int, end: Int, delim: String): String = {

    var subArray = commandArgs.slice(start, end)
    var builder: StringBuilder = new StringBuilder

    for (i <- 0 until subArray.length){
      builder.append(subArray(i))
      if (i + 1 < subArray.length){
        builder.append(" ")
      }
    }
    builder.toString
  }

}
