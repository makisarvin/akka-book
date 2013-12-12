package zzz.akka.avionics

import scala.concurrent.duration._
import akka.actor.Actor

trait AttendantResponsiveness {
  val maxResponseTimeMS: Int
  def responseDuration = scala.util.Random.nextInt(maxResponseTimeMS).millis
}

object FlightAttendant {
  case class GetDrink(drinkname: String)
  case class Drink(drinkname: String)

  //By default we will make attendants that response within 5 minutes
  def apply() = new FlightAttendant with AttendantResponsiveness {
    val maxResponseTimeMS = 30000
  }
}

class FlightAttendant extends Actor {
  this: AttendantResponsiveness =>
  import FlightAttendant._

  //bring the execution context into implicit scope for the scheduler
  implicit val ec = context.dispatcher

  def receive = {
    case GetDrink(drinkname) =>
      context.system.scheduler.scheduleOnce(responseDuration, sender, Drink(drinkname))
  }

}
