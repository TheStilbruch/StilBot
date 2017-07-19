package com.stilbruch.stilbot.markup

import scala.util.matching.Regex

import com.stilbruch.stilbot.Stilbot
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class MarkupManager extends ListenerAdapter {

  val emojis = Map(
    ' ' -> "  ",
    '.' -> ":record_button:",
    ',' -> "",
    '?' -> ":grey_question:",
    '!' -> ":grey_exclamation:",
    '#' -> ":hash:",
    'p' -> ":parking:",
    'b' -> ":b:",
    '1' -> ":one:",
    '2' -> ":two:",
    '3' -> ":three:",
    '4' -> ":four:",
    '5' -> ":five:",
    '6' -> ":six:",
    '7' -> ":seven:",
    '8' -> ":eight:",
    '9' -> ":nine:",
    '0' -> ":zero:"
  )

  val markupFuncs = Map[Char, String => String](
    '-' -> (_.toLowerCase),
    '+' -> (_.toUpperCase),
    '=' -> (_.toCharArray.mkString(" ")),
    '&' -> (_.toCharArray.map(c => emojis.getOrElse(c, s":regional_indicator_${c.toLower}:")).mkString)
  )

  def withMarkup(input: String): String = {

    var markedString = new String(input)

    for (code <- markupFuncs.keySet) {
      val markupRegex = s"\\${code}([^\\${code}]*)\\${code}".r
      markupRegex.findAllMatchIn(markedString).foreach(m => {
        markedString = markedString.replace(m.matched, markupFuncs.get(code).get.apply(m.matched.substring(1, m.matched.length - 1)))
      })
    }

    return markedString
  }

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {

    if (event.getAuthor.getIdLong != Stilbot.getClientId || event.getMessage.getRawContent.startsWith(Stilbot.commandPrefix)){
      return
    }

    val markupMessage = withMarkup(event.getMessage.getRawContent)

    if (markupMessage != event.getMessage.getRawContent){
      event.getMessage.editMessage(markupMessage).queue()
    }
  }

}
