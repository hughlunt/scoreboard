package models

import play.api.libs.json.{Json, JsValue}

/**
  * Created by hlunt on 27/09/16.
  */
case class Points (id: Long, points: JsValue) extends BaseEntity

object Points {
  implicit val pointsFormat = Json.format[Points]
}
