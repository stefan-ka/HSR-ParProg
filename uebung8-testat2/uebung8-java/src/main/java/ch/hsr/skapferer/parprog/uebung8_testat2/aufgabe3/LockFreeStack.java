package ch.hsr.skapferer.parprog.uebung8_testat2.aufgabe3;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> implements Stack<T> {
	AtomicReference<Node<T>> top = new AtomicReference<>();

	public void push(T value) {
		Node<T> newNode = new Node<>(value);
		Node<T> current;
		boolean retry = false;
		do {
			if (retry)
				Thread.yield();
			current = top.get();
			newNode.setNext(current);
			retry = true;
		} while (!top.compareAndSet(current, newNode));
	}

	public T pop() {
		Node<T> topNodeOld;
		Node<T> topNodeNew;
		T value;
		boolean retry = false;
		do {
			if (retry)
				Thread.yield();
			topNodeOld = top.get();
			topNodeNew = topNodeOld.getNext();
			value = topNodeOld.getValue();
			retry = true;
		} while (!top.compareAndSet(topNodeOld, topNodeNew));
		return value;
	}
}
