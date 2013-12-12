package zzz.akka.avionics

import akka.testkit.{TestActorRef, TestKit}
import akka.actor.ActorSystem
import org.scalatest.{WordSpecLike, BeforeAndAfterAll, MustMatchers}

class EventSourceSpec extends TestKit(ActorSystem("EventSourceSpec"))
  with  WordSpecLike
  with MustMatchers
  with BeforeAndAfterAll {

  import EventSource._

  override def afterAll() = { system.shutdown() }

  "Event Source" should {
    "allow us to register a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))

      real.listeners must contain (testActor)
    }
    "allow us to unregister a listener" in {
      val real = TestActorRef[TestEventSource].underlyingActor
      real.receive(RegisterListener(testActor))
      real.receive(UnRegisterListener(testActor))

      real.listeners.size must be (0)
    }
    "send the event to our test actor" in {
      val testA = TestActorRef[TestEventSource]
      testA ! RegisterListener(testActor)
      testA.underlyingActor.sendEvent("Hello")
      expectMsg("Hello")
    }
  }

}
