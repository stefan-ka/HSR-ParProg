package ch.hsr.skapferer.parprog.uebung9.aufgabe1;

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

		/*
		 * indem wir p2 als Sender angeben kann p1 mit getSender darauf
		 * zugreifen
		 */
		p1.tell(new Start(), p2);
	}

	static class Start {
	}

	static class Ping {
		final int count;

		public Ping(int count) {
			this.count = count;
		}
	}

	static class PingPongActor extends UntypedActor {

		public void onReceive(Object message) {
			if (message instanceof Start) {
				handleStart((Start) message);
			} else if (message instanceof Ping) {
				handlePing((Ping) message);
			}
		}

		private void handlePing(Ping msg) {
			System.out.println(getSelf().path().name() + ": count " + msg.count);

			if (msg.count == MAX_PING_PONG) {
				getContext().system().shutdown();
			} else {
				sleep();
				getSender().tell(new Ping(msg.count + 1), getSelf());
			}
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