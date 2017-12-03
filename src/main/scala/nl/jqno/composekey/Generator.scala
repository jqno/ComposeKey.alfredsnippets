package nl.jqno.composekey

import java.io.File
import java.nio.file.{Files, StandardOpenOption}
import java.util.UUID

import nl.jqno.composekey.JsonProtocol._
import play.api.libs.json.Json

import scala.io.Source

object Generator extends App {

  val rawInput = Source.fromResource("symbols.json").getLines().mkString("\n")
  val jsonInput = Json.parse(rawInput)
  val sourceSnippets = jsonInput.validate[List[SourceSnippet]].get // quick 'n dirty ;)

  val alfredSnippets = for {
    s <- sourceSnippets
    k <- s.keywords
  } yield AlfredSnippet(s.snippet, UUID.randomUUID(), s.snippet, k)

  val tempDir = Files.createTempDirectory("composekey")

  println(s"Writing to $tempDir")
  for (as <- alfredSnippets) {
    val s = AlfredContainer(as)
    val j = Json.prettyPrint(Json.toJson(s))
    val f = new File(tempDir.toFile, s"${as.name} [${as.uid}]").toPath
    Files.write(f, j.getBytes, StandardOpenOption.CREATE)
  }

  // copy info.plist
  // copy icon.png
  // zip it up

}
