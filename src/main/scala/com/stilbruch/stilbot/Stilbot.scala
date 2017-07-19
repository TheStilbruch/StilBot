package com.stilbruch.stilbot

import com.stilbruch.stilbot.command.CommandManager
import com.stilbruch.stilbot.markup.MarkupManager
import net.dv8tion.jda.core.{AccountType, JDA, JDABuilder}

object Stilbot {

  var token: String = _
  var clientId: Long = _
  var jda: JDA = _

  val commandPrefix = ".."

  def main(args: Array[String]): Unit = {

    if (args.length != 2){
      throw new RuntimeException("Invalid arguments!")
    }

    token = args(0)
    clientId = args(1).toLong
    jda = new JDABuilder(AccountType.CLIENT)
        .setToken(token)
        .buildAsync()

    val commandManager = new CommandManager
    val markupManager = new MarkupManager

    jda.addEventListener(commandManager)
    jda.addEventListener(markupManager)

  }

}
