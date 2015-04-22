package ch.hsr.skapferer.parprog.uebung9.aufgabe3;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import ch.hsr.skapferer.parprog.uebung9.aufgabe3.Messages.IncomingMessage;
import ch.hsr.skapferer.parprog.uebung9.aufgabe3.Messages.OutgoingMessage;
import ch.hsr.skapferer.parprog.uebung9.aufgabe3.Messages.Quit;

/**
 * Repräsentiert einen Teilnehmer im Chat und hält die Session mit dem Browser.
 */
public class ChatUser extends UntypedActor {

	private final Session session;

	public ChatUser(Session session) {
		this.session = session;
	}

	public void onReceive(Object message) throws Exception {
		if (message instanceof IncomingMessage) {
			handleIncomingMessage((IncomingMessage) message);
		} else if (message instanceof OutgoingMessage) {
			handleOutgoingMessage((OutgoingMessage) message);
		} else if (message instanceof Quit) {
			handleQuit();
		}
	}

	/**
	 * Beim Beenden senden wir den anderen Chat-Teilnehmern eine Nachricht und
	 * beenden den Actor mittels einer PoisonPill.
	 */
	private void handleQuit() {
		ActorSelection selection = getChatUsers();
		// TODO
	}

	/**
	 * Beim Empfang einer OutgoingMessage senden wir den Text zurück an den
	 * Browser.
	 */
	private void handleOutgoingMessage(OutgoingMessage message) throws IOException {
		if (session.isOpen()) {
			session.getRemote().sendString(message.text);
		}
	}

	/**
	 * Eine IncomingMessage verteilen wir mittels ActorSelection /user/room/* an
	 * alle anderen Teilnehmer im Chat.
	 */
	private void handleIncomingMessage(IncomingMessage message) {
		ActorSelection selection = getChatUsers();
		// TODO
	}

	private ActorSelection getChatUsers() {
		return ChatRoom.system.actorSelection("/user/room/*");
	}
}