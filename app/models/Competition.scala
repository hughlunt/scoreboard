package models

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by hlunt on 21/09/16.
  */
case class Competition (id: Long, name: String, date: DateTime) extends BaseEntity

object Competition {
  implicit val competitionFormat = Json.format[Competition]
}
