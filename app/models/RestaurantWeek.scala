package models

import play.api.libs.json._

case class Schedule(hours: List[(String, Long)])
object Schedule {
  def zero = new Schedule(List.empty)
  def apply(hours: List[(String, Long)]): Schedule = new Schedule(hours)

  implicit val reads: Reads[Schedule] = new Reads[Schedule] {
    override def reads(o: JsValue): JsResult[Schedule] = {
      val default: List[(String, Long)]  = List.empty

      val result: List[(String, Long)] = o match {
        case JsObject(_) => default
        case JsArray(arr) =>
         arr.toList.flatMap {
            json: JsValue =>
              json.as[JsObject].values.toList match {
                case List(k, v) => Some(k.as[String].toLowerCase -> v.as[Long])
                case _ => None
              }
          }
        case _ => default
      }

      JsSuccess(apply(result))
    }
  }
}

case class Day(name: WeekDay.Value, schedule: Schedule)
object Day {
  def zero = new Day(WeekDay.Undefined, Schedule.zero)

  def apply(weekDay: WeekDay.Value, openingHours: Schedule): Day = new Day(weekDay, openingHours)
  def apply(weekDay: String, openingHours: Schedule): Day = new Day(WeekDay.toEnum(weekDay), openingHours)
  def apply(weekDay: String, openingHours: JsValue): Day = {
      val weekdayEnum = WeekDay.toEnum(weekDay)
      val hrs = openingHours.validate[Schedule].asOpt.fold(Schedule.zero)(s => s)
      apply(weekdayEnum, hrs)
  }

}

case class RestaurantSchedule(weekdays: List[Day])

object RestaurantSchedule {
  def apply(weekdays: List[Day]): RestaurantSchedule = new RestaurantSchedule(weekdays)

  def zero: RestaurantSchedule = apply(List.empty)

  implicit val reads: Reads[RestaurantSchedule] = new Reads[RestaurantSchedule] {
    override def reads(o: JsValue): JsResult[RestaurantSchedule] = {
      val weekdays = o match {
        case JsObject(keyValueMap) =>
          //Map to List to ensure the order
          keyValueMap.toList.map {
            case (weekDay, openingHours: JsValue) =>
              Day.apply(weekDay, openingHours)
          }
        case JsArray(_) => throw new JsonParseException("Json is malformed, weekday schedule should be object, not array")
        case _ => throw new JsonParseException("Json is malformed, please check format again")
      }

      JsSuccess(apply(weekdays))
    }
  }
}