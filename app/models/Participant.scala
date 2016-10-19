package models

import play.api.libs.json.Json

/**
  * Created by hlunt on 28/09/16.
  */
case class Participant (id: Long, competition_id: Long, competitor_id: Long) extends BaseEntity

object Participant {
  implicit val participantformat = Json.format[Participant]
}
