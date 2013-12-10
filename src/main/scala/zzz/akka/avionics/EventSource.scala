package zzz.akka.avionics

import akka.actor.{Actor, ActorRef}

object EventSource {

  case class RegisterListener(listener: ActorRef)
  case class UnRegisterListener(listener: ActorRef)
}

trait EventSource { this: Actor =>
  import EventSource._

  var listeners = Vector.empty[ActorRef]

  /** Send the event ot all the listeners */
  def sendEvent[T](event: T): Unit  = listeners foreach {
    _ ! event
  }

  def eventSourceReceive: Receive = {
    case RegisterListener(listener) => listeners :+= listener
    case UnRegisterListener(listener) => listeners = listeners filter ( _ != listener )
  }
}
