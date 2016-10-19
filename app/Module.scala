import com.google.inject.{Provides, AbstractModule}
import java.time.Clock

import dao.{BaseDAO, AbstractBaseDAO}
import models._
import services.{ApplicationTimer, AtomicCounter, Counter}
import dao.SlickTables._
/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  import dao.SlickTables.{CompetitorTable, CompetitionTable}

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    // Ask Guice to create an instance of ApplicationTimer when the
    // application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()
    // Set AtomicCounter as the implementation for Counter.
    bind(classOf[Counter]).to(classOf[AtomicCounter])
//    bind(classOf[AbstractBaseDAO[CompetitionTable, Competition]]).to(classOf[BaseDAO[CompetitionTable, Competition]])
  }

  @Provides
  def provideCompetitionDAO : AbstractBaseDAO[CompetitionTable, Competition] = new BaseDAO[CompetitionTable, Competition]

  @Provides
  def provideCompetitorDAO : AbstractBaseDAO[CompetitorTable, Competitor] = new BaseDAO[CompetitorTable, Competitor]

  @Provides
  def providePointsDAO : AbstractBaseDAO[PointsTable, Points] = new BaseDAO[PointsTable, Points]

  @Provides
  def provideEventDAO : AbstractBaseDAO[EventTable, Event] = new BaseDAO[EventTable, Event]

  @Provides
  def provideParticipantDAO : AbstractBaseDAO[ParticipantTable, Participant] = new BaseDAO[ParticipantTable, Participant]

  @Provides
  def resultDAO : AbstractBaseDAO[ResultTable, Result] = new BaseDAO[ResultTable, Result]

}
