package models

import play.api.libs.json.Json

/**
  * Created by hlunt on 03/09/16.
  */
case class Competitor (id: Long, name: String) extends BaseEntity

object Competitor {
  implicit val competitorFormat = Json.format[Competitor]
}
