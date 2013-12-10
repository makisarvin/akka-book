package zzz.akka.avionics

import akka.actor.{Props, Actor, ActorSystem, ActorLogging}
import scala.concurrent.duration._

object Altimeter {
	case class RateChange(amount: Float)
	case object Tick
  case class AltitudeUpdate(altitude: Double)
}

class Altimeter extends Actor with ActorLogging with EventSource {
	import Altimeter._

	implicit val ec = context.dispatcher

	// The maximum ceiling of our plane in 'feet'
	val ceiling = 43000
	val maxRateOfClimb = 5000

	var rateOfClimb = 0f
	var altitude = 0d

	var lastTick = System.currentTimeMillis
	// We need to periodically update our altitude. This
	// scheduled message send will tell us when to do that
	val ticker = context.system.scheduler.schedule(100.millis, 100.millis, self, Tick)

  def receive = eventSourceReceive orElse altimeterReceive

	def altimeterReceive: Receive = {
		// Our rate of climb has changed
		case RateChange(amount) =>
			rateOfClimb = amount.min(1.0f).max(-1.0f) * maxRateOfClimb
			log info s"Altimeter changed rate of climb to $rateOfClimb."
		case Tick =>
			val tick = System.currentTimeMillis
			altitude = altitude + ((tick - lastTick) / 60000.0) * rateOfClimb
			lastTick = tick
      //send event
      sendEvent(AltitudeUpdate(altitude))

	}

	override def postStop(): Unit = ticker.cancel
}