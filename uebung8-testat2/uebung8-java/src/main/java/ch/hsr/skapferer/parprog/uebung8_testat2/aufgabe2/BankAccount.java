package ch.hsr.skapferer.parprog.uebung8_testat2.aufgabe2;

import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {
	private AtomicInteger balance = new AtomicInteger(0);

	public void deposit(int amount) {
		balance.addAndGet(amount);
	}

	public boolean withdraw(int amount) {
		int balanceOld;
		int balanceNew;
		do {
			balanceOld = balance.get();
			if (amount <= balanceOld) {
				balanceNew = balanceOld - amount;
			} else {
				return false;
			}
		} while (!balance.compareAndSet(balanceOld, balanceNew));
		return true;
	}

	public int getBalance() {
		return balance.get();
	}
}
