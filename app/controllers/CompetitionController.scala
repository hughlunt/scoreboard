package controllers

import dao.SlickTables.CompetitionTable
import models.Competition
import org.joda.time.DateTime
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import dao._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

/**
  * Created by hlunt on 21/09/16.
  */

@Singleton
class CompetitionController @Inject() (repo: AbstractBaseDAO[CompetitionTable, Competition], val messagesApi: MessagesApi)
                                       (implicit ec: ExecutionContext) extends Controller with I18nSupport{

    val competitionForm: Form[CreateCompetitionForm] = Form {
      mapping(
        "name" -> nonEmptyText,
        "date" -> jodaDate
      )(CreateCompetitionForm.apply)(CreateCompetitionForm.unapply)
    }

    def index = Action {
      Ok(views.html.competitions(competitionForm))
    }

    def addCompetition() = Action.async { implicit request =>
      // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
      competitionForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(BadRequest(views.html.competitions(errorForm)))
        },
        competition => {
          repo.insert(Competition(0, competition.name, competition.date)).map { _ =>
            Ok(views.html.competitions(competitionForm)) //Redirect(routes.CompetitionController.index)
          }
        }
      )
    }

    def getCompetitions = Action.async {
      repo.list map { competitions =>
        Ok(Json.toJson(competitions))
      }
    }
}

case class CreateCompetitionForm(name: String, date: DateTime)

