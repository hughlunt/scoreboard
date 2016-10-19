package utils

import com.github.tminglei.slickpg._

trait MyPostgresDriver extends ExPostgresDriver
with PgPlayJsonSupport
with PgDateSupportJoda
with PgSearchSupport {

  override val pgjson = "jsonb"
  ///
  override val api = new API
    with DateTimeImplicits
    with PlayJsonImplicits
    with SearchImplicits
    with SearchAssistants {}
}

object MyPostgresDriver extends MyPostgresDriver