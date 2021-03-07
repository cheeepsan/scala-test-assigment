package controllers

import java.text.SimpleDateFormat
import java.util.TimeZone

import javax.inject._
import models.{Day, RestaurantSchedule, WeekDay}
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc._

import scala.annotation.tailrec
import scala.collection.immutable.ListMap
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents)
                              (implicit val executionContext: ExecutionContext) extends BaseController {


  def processTimetable = Action.async({ implicit req =>
    val default: Option[RestaurantSchedule] = None
    val jsonParse = (jsValue: JsValue) => jsValue.validateOpt[RestaurantSchedule] match {
      case JsSuccess(value, _) => value
      case JsError(_) => None
    }

    req.body.asJson.fold(default)(jsonParse) match {
      case Some(schedule) =>
        Future{
          Ok{
            renderSchedule(schedule.weekdays).map {
              case (day, hours) =>
                if (hours.nonEmpty) {
                  s"${WeekDay.toPlainText(day)}: ${hours.grouped(2).map(_.mkString(" - ")).mkString(", ")}"
                } else s"${WeekDay.toPlainText(day)}: closed"
            }.mkString("\n")
          }
        }
      case None => Future(InternalServerError)
    }
  })

  def renderSchedule(days: List[Day]): ListMap[WeekDay.Value, List[String]] = {
    implicit val df: SimpleDateFormat = new SimpleDateFormat("hh a")
    df.setTimeZone(TimeZone.getTimeZone("GMT"))

    @tailrec
    def buildText(list: List[Day], fullList: ListMap[WeekDay.Value, List[String]]): ListMap[WeekDay.Value, List[String]] = list match {
      case Nil => fullList
      case head :: tail =>
        val hourList = head.schedule.hours
        val processedList = hourList.zipWithIndex.foldLeft(List.empty[String]) {
          case (acc, data) =>
            val ((status, epoch), index) = data
            val time = processEpoch(epoch)
            status match {
              case "open" =>
                if (index == (hourList.size - 1)) {
                  processEpoch(tail.head.schedule.hours.head._2) :: time :: acc
                } else time :: acc
              case "close" =>
                if(index == 0) acc else time :: acc
            }
        }.reverse
        buildText(tail, fullList ++ ListMap(head.name -> processedList))
    }

    buildText(days, ListMap.empty)
  }

  def processEpoch(epoch: Long)(implicit df: SimpleDateFormat): String = df.format(epoch * 1000)
}
