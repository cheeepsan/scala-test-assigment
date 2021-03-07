package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{JsValue, Json}
import play.api.test._
import play.api.test.Helpers._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.io.Source

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController POST" should {

    implicit val sys = ActorSystem("MyTest")

    val sendPost = (controller: HomeController, json: JsValue) => controller.processTimetable.apply(
      FakeRequest()
        .withMethod(POST)
        .withHeaders("Content-type" -> "application/json")
        .withJsonBody(json))

    "process JSON requests to schedule from payload" in {
      val json = Json.parse(Source.fromFile("test/json/payload.json").getLines.mkString)
      val result = Source.fromFile("test/json/payloadResponse").getLines.mkString("\n")

      val controller = inject[HomeController]
      val response = sendPost(controller, json)
      status(response) mustBe OK
      contentType(response) mustBe Some("text/plain")
      contentAsString(response) must include (result)
    }

    "process JSON requests to schedule from payload1" in {
      val json = Json.parse(Source.fromFile("test/json/payload1.json").getLines.mkString)
      val result = Source.fromFile("test/json/payloadResponse").getLines.mkString("\n")

      val controller = inject[HomeController]
      val response = sendPost(controller, json)

      status(response) mustBe OK
      contentType(response) mustBe Some("text/plain")
      contentAsString(response) must include (result)
    }

  }
}
