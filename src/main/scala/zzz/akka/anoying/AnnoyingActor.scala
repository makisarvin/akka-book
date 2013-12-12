package zzz.akka.anoying

import akka.actor.{ActorRef, Actor}

class AnnoyingActor(snooper: ActorRef) extends Actor {

  override def preStart() {
    self ! "send"
  }

  def receive = {
    case "send" =>
      snooper ! "Hello!!"
      self ! "send"
  }
}

class NiceActor(snooper: ActorRef) extends Actor {
  override def preStart() {
    snooper ! "Hi"
  }
  def receive = {
    case _ =>
  }
}
