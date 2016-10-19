package controllers

import dao.SlickTables.CompetitorTable
import models.Competitor
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import dao._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

@Singleton
class CompetitorController @Inject() (repo: AbstractBaseDAO[CompetitorTable, Competitor], val messagesApi: MessagesApi)
                                   (implicit ec: ExecutionContext) extends Controller with I18nSupport{

  val competitorForm: Form[CreateCompetitorForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreateCompetitorForm.apply)(CreateCompetitorForm.unapply)
  }

  def index = Action {
    Ok(views.html.index(competitorForm))
  }

  def addCompetitor() = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    competitorForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(BadRequest(views.html.index(errorForm)))
      },
      competitor => {
        repo.insert(Competitor(0, competitor.name)).map { _ =>
          Redirect(routes.CompetitorController.index)
        }
      }
    )
  }

  def getCompetitors = Action.async {
    repo.list map { competitors =>
      Ok(Json.toJson(competitors))
    }
  }
}

case class CreateCompetitorForm(name: String)