package models

object WeekDay extends Enumeration { self =>
  type WeekDay = Value
  val Mon, Tue, Wed, Thu, Fri, Sat, Sun, Undefined = Value

  def toEnum(weekdayName: String) = weekdayName match {
    case "monday" => self.Mon
    case "tuesday" => self.Tue
    case "wednesday" => self.Wed
    case "thursday" => self.Thu
    case "friday" => self.Fri
    case "saturday" => self.Sat
    case "sunday" => self.Sun
    case _ => self.Undefined
  }

  def toPlainText(weekdayEnum: WeekDay) = weekdayEnum match {
    case WeekDay.Mon => "Monday"
    case WeekDay.Tue => "Tuesday"
    case WeekDay.Wed => "Wednesday"
    case WeekDay.Thu => "Thursday"
    case WeekDay.Fri => "Friday"
    case WeekDay.Sat => "Saturday"
    case WeekDay.Sun => "Sundays"
    case WeekDay.Undefined => "Undefined"
  }
}