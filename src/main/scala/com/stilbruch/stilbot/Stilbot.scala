package com.stilbruch.stilbot

import com.stilbruch.stilbot.command.CommandManager
import com.stilbruch.stilbot.markup.MarkupManager
import net.dv8tion.jda.core.{AccountType, JDA, JDABuilder}

object Stilbot {

  var token: Option[String] = None
  var clientId: Option[Long] = None
  var jda: Option[JDA] = None

  def getClientId = clientId.get
  def getJda = jda.get

  val commandPrefix = ".."

  def main(args: Array[String]): Unit = {

    if (args.length != 2){
      throw new RuntimeException("Invalid arguments!")
    }

    token = Some(args(0))
    clientId = Some(args(1).toLong)
    jda = Some(new JDABuilder(AccountType.CLIENT)
        .setToken(token.get)
        .buildAsync())

    val commandManager = new CommandManager
    val markupManager = new MarkupManager

    jda.get.addEventListener(commandManager)
    jda.get.addEventListener(markupManager)

  }

}
