package com.github.tamaki.study.hajimeni.future

import org.scalatest._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nishiyama on 2015/07/24.
 */
class NormalSumTest extends FunSpec with Matchers  {
  describe("Normal") {
    it ("sum") {
      val begin = System.currentTimeMillis()
      val sum = NormalSum.sum(Seq("rikunavi", "mynavi", "en"))
      val end = System.currentTimeMillis()

      println(s"======== [sum = $sum],  ${end - begin} ms")
    }
  }
}
class FutureSumTest extends FunSpec with Matchers  {
  describe("Future") {
    it("sum") {
      val begin = System.currentTimeMillis()
      val f = FutureSum.sum(Seq("rikunavi", "mynavi", "en"))
      f.map { sum =>
        val end = System.currentTimeMillis()
        println(s"======== [sum = $sum],  ${end - begin} ms")
      }

      Thread.sleep(10000)
    }
  }

}
