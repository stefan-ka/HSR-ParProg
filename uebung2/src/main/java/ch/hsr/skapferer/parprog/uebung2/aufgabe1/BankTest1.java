package ch.hsr.skapferer.parprog.uebung2.aufgabe1;

class BankAccount {
	private int balance = 0;

	public void deposit(int amount) {
		balance += amount;
	}

	public boolean withdraw(int amount) {
		if (amount <= balance) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}

	public int getBalance() {
		return balance;
	}
}

class BankCustomer extends Thread {
	private static final int NOF_TRANSACTIONS = 10000000;
	private final BankAccount account;

	public BankCustomer(BankAccount account) {
		this.account = account;
	}

	@Override
	public void run() {
		for (int k = 0; k < NOF_TRANSACTIONS; k++) {
			account.deposit(100);
			account.withdraw(100);
		}
		assert account.getBalance() == 0;
	}
}

public class BankTest1 {
	private static final int NOF_CUSTOMERS = 10;

	public static void main(String[] args) {
		BankAccount account = new BankAccount();
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			new BankCustomer(account).start();
		}
	}
}
