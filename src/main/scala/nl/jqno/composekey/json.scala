package nl.jqno.composekey

import java.util.UUID

import play.api.libs.json.Json

case class SourceSnippet(snippet: String, name: String, keywords: List[String])
case class AlfredSnippet(snippet: String, uid: UUID, name: String, keyword: String)
case class AlfredContainer(alfredsnippet: AlfredSnippet)

object JsonProtocol {
  implicit val sourceSnippetFormat = Json.format[SourceSnippet]
  implicit val alfredSnippetFormat = Json.format[AlfredSnippet]
  implicit val alfredContainerFormat = Json.format[AlfredContainer]
}
