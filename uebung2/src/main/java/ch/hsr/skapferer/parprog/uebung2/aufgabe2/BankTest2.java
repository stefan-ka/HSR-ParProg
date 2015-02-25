package ch.hsr.skapferer.parprog.uebung2.aufgabe2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BankAccount {
	private int balance = 0;

	public synchronized void deposit(int amount) {
		balance += amount;
		notifyAll();
	}

	public synchronized boolean withdraw(int amount, long timeOutMillis) {
		long tryUntil = System.currentTimeMillis() + timeOutMillis;
		while (balance < amount) {
			if (System.currentTimeMillis() > tryUntil)
				return false;
			try {
				wait(timeOutMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		balance -= amount;
		return true;
	}

	public synchronized int getBalance() {
		return balance;
	}
}

class BankCreditCustomer extends Thread {
	private static final int NOF_TRANSACTIONS = 100;
	private final BankAccount account;
	private final int amount;

	public BankCreditCustomer(BankAccount account, int amount) {
		this.account = account;
		this.amount = amount;
	}

	@Override
	public void run() {
		for (int i = 0; i < NOF_TRANSACTIONS; i++) {
			if (account.withdraw(amount, 5)) {
				System.out.println("Use credit " + amount + " by " + Thread.currentThread().getName());
				account.deposit(amount);
			} else {
				System.out.println("Can not withdraw: " + amount + " by " + Thread.currentThread().getName());
			}
		}
	}
}

public class BankTest2 {
	private static final int NOF_CUSTOMERS = 10;
	private static final int START_BUDGET = 1000;

	public static void main(String[] args) throws InterruptedException {
		BankAccount account = new BankAccount();
		List<BankCreditCustomer> customers = new ArrayList<>();
		Random random = new Random(4711);
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			customers.add(new BankCreditCustomer(account, random.nextInt(1000)));
		}
		account.deposit(START_BUDGET);
		for (BankCreditCustomer customer : customers) {
			customer.start();
		}
		for (BankCreditCustomer customer : customers) {
			customer.join();
		}
		System.out.println("account: " + account.getBalance());
	}
}
