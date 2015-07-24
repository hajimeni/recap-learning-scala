package com.github.tamaki.study.hajimeni.future

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nishiyama on 2015/07/24.
 */
object NormalSum {

  // 元コード
  def aggregate(id: String): Int = {
    Thread.sleep(id.length * 1000)
    id.length
  }

  def sum(seq: Seq[String]): Int = {
//    val result = Seq("rikunavi", "mynavi", "en").map(aggregate).sum
    seq.map(aggregate).sum
  }
}

object FutureSum {

  import scala.concurrent.Future

  def aggregate(id: String): Future[Int] = {
    Future {
      Thread.sleep(id.length * 1000)
      id.length
    }
  }

  def sum(seq: Seq[String]): Future[Int] = {
    Future.sequence(seq.map(aggregate)).map(_.sum)
  }
}
