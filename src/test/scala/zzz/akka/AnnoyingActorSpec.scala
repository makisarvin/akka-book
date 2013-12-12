package zzz.akka

import akka.testkit.{TestProbe, ImplicitSender, TestKit}
import akka.actor.{Props, ActorSystem}
import org.scalatest._
import zzz.akka.anoying.{NiceActor, AnnoyingActor}

class AnnoyingActorSpec extends TestKit(ActorSystem("AnnoyingActorSpec"))
  with ImplicitSender
  with WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  "The AnnoyingActor" should {
    "say Hello!!" in {
      val p = TestProbe()
      val a = system.actorOf(Props(new AnnoyingActor(p.ref)))
      p.expectMsg("Hello!!")
      system.stop(a)
    }
  }

  "The NiceActor" should {
    "say Hi" in {
      val a = system.actorOf(Props(new NiceActor(testActor)))
      expectMsg("Hi")
      system.stop(a)
    }
  }

}
