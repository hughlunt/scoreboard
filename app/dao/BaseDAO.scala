package dao

/**
  * Created by hlunt on 30/09/16.
  */
import models._
import dao.SlickTables._
import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.lifted.{CanBeQueryCondition, TableQuery}
import utils.MyPostgresDriver
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

trait AbstractBaseDAO[T,A] {
  def insert(row : A): Future[Long]
  def insert(rows : Seq[A]): Future[Seq[Long]]
  def update(row : A): Future[Int]
  def update(rows : Seq[A]): Future[Unit]
  def findById(id : Long): Future[Option[A]]
  def findByFilter[C : CanBeQueryCondition](f: (T) => C): Future[Seq[A]]
  def deleteById(id : Long): Future[Int]
  def deleteById(ids : Seq[Long]): Future[Int]
  def deleteByFilter[C : CanBeQueryCondition](f:  (T) => C): Future[Int]
  def list: Future[Seq[A]]
}

class BaseDAO[T <: BaseTable[A], A <: BaseEntity]()(implicit val tableQ: TableQuery[T]) extends AbstractBaseDAO[T,A] with HasDatabaseConfig[MyPostgresDriver] {

  protected lazy val dbConfig = DatabaseConfigProvider.get[MyPostgresDriver](Play.current)

  import dbConfig.driver.api._

  def insert(row: A): Future[Long] = {
    insert(Seq(row)).map(_.head)
  }

  def insert(rows: Seq[A]): Future[Seq[Long]] = {
    db.run(tableQ returning tableQ.map(_.id) ++= rows.filter(_.isValid))
  }

  def update(row: A): Future[Int] = {
    if (row.isValid)
      db.run(tableQ.filter(_.id === row.id).update(row))
    else
      Future {
        0
      }
  }

  def update(rows: Seq[A]): Future[Unit] = {
    db.run(DBIO.seq((rows.filter(_.isValid).map(r => tableQ.filter(_.id === r.id).update(r))): _*))
  }

  def findById(id: Long): Future[Option[A]] = {
    db.run(tableQ.filter(_.id === id).result.headOption)
  }

  def findByFilter[C: CanBeQueryCondition](f: (T) => C): Future[Seq[A]] = {
    db.run(tableQ.withFilter(f).result)
  }

  def deleteById(id: Long): Future[Int] = {
    deleteById(Seq(id))
  }

  def deleteById(ids: Seq[Long]): Future[Int] = {
    db.run(tableQ.filter(_.id.inSet(ids)).delete)
  }

  def deleteByFilter[C: CanBeQueryCondition](f: (T) => C): Future[Int] = {
    db.run(tableQ.withFilter(f).delete)
  }

  def list: Future[Seq[A]] = {
    db.run(tableQ.result)
  }
}