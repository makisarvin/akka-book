package zzz.akka.avionics

import akka.actor.Actor

class TestEventSource extends Actor with ProductionEventSource {

  def receive = eventSourceReceive

}
