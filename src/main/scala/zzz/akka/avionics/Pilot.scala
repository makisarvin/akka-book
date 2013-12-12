package zzz.akka.avionics

import akka.actor.{ActorRef, Actor}
import zzz.akka.avionics.Pilot.ReadyToGo

trait PilotProvider {
  def pilot: Actor = new Pilot
  def copilot: Actor = new CoPilot
  def autopilot: Actor = new AutoPilot
}

class AutoPilot extends Actor {
  def receive = {
    case ReadyToGo =>
  }
}

object Pilot {
  case object ReadyToGo
  case object RelinquishControl
  case class Controls(controlSurfaces: ActorRef)
}

class Pilot extends Actor {
  import Pilot._
  import Plane._

  var controls: ActorRef = context.system.deadLetters
  var copilot: ActorRef = context.system.deadLetters
  var autopilot: ActorRef = context.system.deadLetters

  val copilotName = context.system.settings.config.getString("zzz.akka.avionics.flightcrew.copilotName")

  def receive = {
    case ReadyToGo =>
      context.parent ! GiveMeControl
      copilot = context.actorFor("../" + copilotName)
      autopilot = context.actorFor("../AutoPilot")
    case Controls(controlSurfaces) =>
      controls = controlSurfaces
  }

}
