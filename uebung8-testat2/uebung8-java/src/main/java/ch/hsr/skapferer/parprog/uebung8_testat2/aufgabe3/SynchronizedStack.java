package ch.hsr.skapferer.parprog.uebung8_testat2.aufgabe3;

public class SynchronizedStack<T> implements Stack<T> {
	private java.util.Stack<T> stack = new java.util.Stack<>();

	@Override
	public void push(T item) {
		stack.push(item);
	}

	@Override
	public T pop() {
		return stack.pop();
	}
}
