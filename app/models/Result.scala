package models

import play.api.libs.json.Json

/**
  * Created by hlunt on 28/09/16.
  */
case class Result (id: Long, participant_id: Long, event_id: Long, position: Long) extends BaseEntity

object Result {
  implicit val resultformat = Json.format[Result]
}
