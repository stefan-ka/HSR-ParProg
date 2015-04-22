package ch.hsr.skapferer.parprog.uebung9.aufgabe1;

import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class PingPong {

	private static final int MAX_PING_PONG = 10;

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("PingPong");

		ActorRef p1 = system.actorOf(Props.create(PingPongActor.class), "P1");
		ActorRef p2 = system.actorOf(Props.create(PingPongActor.class), "P2");

		p1.tell(new Start(), p2);

		FiniteDuration FIVE_SECONDS = Duration.create(5, TimeUnit.SECONDS);
		system.scheduler().schedule(FIVE_SECONDS, FIVE_SECONDS, p1, new Reset(), system.dispatcher(), ActorRef.noSender());
	}

	static class Start {
	}

	static class Ping {
		final int count;

		public Ping(int count) {
			this.count = count;
		}
	}

	static class Reset {

	}

	static class PingPongActor extends UntypedActor {

		private boolean doReset = false;

		public void onReceive(Object message) {
			if (message instanceof Start) {
				handleStart((Start) message);
			} else if (message instanceof Ping) {
				handlePing((Ping) message);
			} else if (message instanceof Reset) {
				handleReset((Reset) message);
			}
		}

		private void handleReset(Reset message) {
			this.doReset = true;
		}

		private void handlePing(Ping msg) {
			System.out.println(getSelf().path().name() + ": count " + msg.count);

			if (msg.count == MAX_PING_PONG) {
				getContext().system().shutdown();
			} else {
				sleep();
				getSender().tell(increment(msg), getSelf());
			}
		}

		private Ping increment(Ping msg) {
			if (doReset) {
				doReset = false;
				return new Ping(0);
			}
			return new Ping(msg.count + 1);
		}

		private void sleep() {
			try {
				Thread.sleep((long) (Math.random() * 1000) + 300);
			} catch (InterruptedException e) {
			}
		}

		private void handleStart(Start message) {
			System.out.println("Starting...");
			getSender().tell(new Ping(0), getSelf());
		}
	}
}