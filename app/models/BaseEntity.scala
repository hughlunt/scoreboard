package models

/**
  * Created by hlunt on 30/09/16.
  */
trait BaseEntity {
  val id : Long
  def isValid = true
}
