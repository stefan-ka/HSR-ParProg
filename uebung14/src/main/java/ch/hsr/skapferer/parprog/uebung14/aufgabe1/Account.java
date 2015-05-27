package ch.hsr.skapferer.parprog.uebung14.aufgabe1;

import java.util.Date;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class Account {

	final Ref.View<Integer> balance = STM.newRef(0);
	final Ref.View<Date> lastUpdate = STM.newRef(new Date());
	final Ref.View<Boolean> isClosed = STM.newRef(false);

	public void withdraw(int amount) {
		if (isClosed.get()) {
			throw new RuntimeException("Closed account");
		}
		STM.atomic(() -> {
			if (balance.get() < amount) {
				STM.retry();
			}
			balance.set(balance.get() - amount);
			lastUpdate.set(new Date());
		});
	}

	public void deposit(int amount) {
		if (isClosed.get()) {
			throw new RuntimeException("Closed account");
		}
		STM.atomic(() -> {
			balance.set(balance.get() + amount);
			lastUpdate.set(new Date());
		});
	}

	public void setClosed(boolean isClosed) {
		this.isClosed.set(isClosed);
	}

	public int getBalance() {
		return balance.get();
	}

	public Date getLastUpdate() {
		return lastUpdate.get();
	}

	public static void transfer(Account from, Account to, int amount) {
		STM.atomic(() -> {
			from.withdraw(amount);
			to.deposit(amount);
		});
	}
}
