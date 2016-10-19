package dao

import models._
import org.joda.time.DateTime
import play.api.libs.json.JsValue

/**
  * Created by hlunt on 30/09/16.
  */
object SlickTables extends {
  val profile = utils.MyPostgresDriver
} with SlickTables

trait SlickTables {
  val profile: utils.MyPostgresDriver
  import profile.api._

    abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
      def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    }

    // Competitions
    case class CompetitionRow (name: String, date: DateTime)
    class CompetitionTable(tag: Tag) extends BaseTable[Competition](tag, "competition") {
      def name = column[String]("name")
      def date = column[DateTime]("date")
      def * = (id, name, date) <> ((Competition.apply _).tupled, Competition.unapply)
    }
    implicit val competitions: TableQuery[CompetitionTable] = TableQuery[CompetitionTable]

    // Competitors
    case class CompetitorRow (name: String)
    class CompetitorTable(tag: Tag) extends BaseTable[Competitor](tag, "competitor") {
      def name = column[String]("name")
      def * = (id, name) <> ((Competitor.apply _).tupled, Competitor.unapply)
    }
    implicit val competitors = TableQuery[CompetitorTable]

    // Events
    case class EventRow (name: String, points_id: Long)
    class EventTable(tag: Tag) extends BaseTable[Event](tag, "event") {
      def name = column[String]("name")
      def points_id = column[Long]("points_id")
      def * = (id, name, points_id) <> ((Event.apply _).tupled, Event.unapply)
      def points = foreignKey("points_fk", points_id, scoring)(_.id)
    }
    implicit val events = TableQuery[EventTable]

    // Participant
    case class ParticipantRow (competition_id: Long, competitor_id: Long)
    class ParticipantTable(tag: Tag) extends BaseTable[Participant](tag, "participant") {
      def competition_id = column[Long]("competition_id")
      def competitor_id = column[Long]("competitor_id")
      def * = (id, competition_id, competitor_id) <> ((Participant.apply _).tupled, Participant.unapply)
      def competition = foreignKey("competition_fk", competition_id, competitions)(_.id)
      def competitor = foreignKey("competitor_fk", competitor_id, competitors)(_.id)
    }
    implicit val participants = TableQuery[ParticipantTable]

    // Points
    case class PointsRow (points: JsValue)
    class PointsTable(tag: Tag) extends BaseTable[Points](tag, "points") {
      def points = column[JsValue]("points")
      def * = (id, points) <> ((Points.apply _).tupled, Points.unapply)
    }
    implicit val scoring = TableQuery[PointsTable]

    // Results
    case class ResultRow (participant_id: Long, event_id: Long, position: Long)
    class ResultTable(tag: Tag) extends BaseTable[Result](tag, "result") {
      def participant_id = column[Long]("participant_id")
      def event_id = column[Long]("event_id")
      def position = column[Long]("position")
      def * = (id, participant_id, event_id, position) <> ((Result.apply _).tupled, Result.unapply)
      def participant = foreignKey("participant_fk", participant_id, participants)(_.id)
      def event = foreignKey("event_fk", event_id, events)(_.id)
    }
    implicit val results = TableQuery[ResultTable]
}
