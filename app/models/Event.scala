package models

import play.api.libs.json.Json

/**
  * Created by hlunt on 27/09/16.
  */
case class Event (id: Long, name: String, points_id: Long) extends BaseEntity


object Event {
  implicit val eventFormat = Json.format[Event]
}
