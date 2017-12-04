package nl.jqno.composekey

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, StandardOpenOption}
import java.util.UUID

import com.google.common.io.{Resources, Files => GFiles}
import nl.jqno.composekey.JsonProtocol._
import org.zeroturnaround.zip.ZipUtil
import play.api.libs.json.Json

import scala.collection.JavaConverters.collectionAsScalaIterableConverter

object Generator extends App {

  // Reading

  val cl = getClass.getClassLoader

  val rawInput = Resources.readLines(cl.getResource("symbols.json"), StandardCharsets.UTF_8).asScala.mkString("\n")
  val jsonInput = Json.parse(rawInput)
  val sourceSnippets = jsonInput.validate[List[SourceSnippet]].get // quick 'n dirty ;)

  val alfredSnippets = for {
    s <- sourceSnippets
    k <- s.keywords
  } yield AlfredSnippet(s.snippet, UUID.randomUUID(), s.snippet, k)



  // Writing

  val tempDir = Files.createTempDirectory("composekey").toFile

  println(s"Writing to $tempDir")
  for (as <- alfredSnippets) {
    val s = AlfredContainer(as)
    val j = Json.prettyPrint(Json.toJson(s))
    val f = new File(tempDir, s"${as.name} [${as.uid}]").toPath
    Files.write(f, j.getBytes, StandardOpenOption.CREATE)
  }

  def copyToTempDir(name: String) =
    Resources.asByteSource(cl.getResource(name)).copyTo(GFiles.asByteSink(new File(tempDir, "info.plist")))

  copyToTempDir("info.plist")
  copyToTempDir("icon.png")

  ZipUtil.pack(tempDir, new File("./ComposeKey.alfredsnippets"))

}
