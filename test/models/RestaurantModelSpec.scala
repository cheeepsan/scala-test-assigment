package models

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.Injecting

import scala.io.Source

class RestaurantModelSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "Used models " should {
    "Restaurant model should be valid" in {
      val rModel = Json.parse(Source.fromFile("test/json/payload.json").getLines.mkString).as[RestaurantSchedule]
      val days = List(
        Day.apply(WeekDay.Fri, Schedule(
          List(("open" -> 64800)))
        ),
        Day.apply(WeekDay.Sat, Schedule(
          List(
            ("close" -> 3600),
            ("open" -> 32400),
            ("close" -> 39600),
            ("open" -> 57600),
            ("close" -> 82800)
          ))
        )
      )
      val toEqual = new RestaurantSchedule(days)
      rModel mustEqual (toEqual)
    }

    "Schedule should be valid" in {
      val raw = """[
                     |    {
                     |      "type": "close",
                     |      "value": 3600
                     |    },
                     |    {
                     |      "type": "open",
                     |      "value": 32400
                     |    },
                     |    {
                     |      "type": "close",
                     |      "value": 39600
                     |    },
                     |    {
                     |      "type": "open",
                     |      "value": 57600
                     |    },
                     |    {
                     |      "type": "close",
                     |      "value": 82800
                     |    }
                     |  ]""".stripMargin
      val sModel = Json.parse(raw).as[Schedule]
      val toEqual = Schedule(
        List(
          ("close" -> 3600),
          ("open" -> 32400),
          ("close" -> 39600),
          ("open" -> 57600),
          ("close" -> 82800)
        ))

      sModel mustEqual(toEqual)
    }
  }
}
