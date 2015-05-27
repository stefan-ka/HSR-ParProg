package ch.hsr.skapferer.parprog.uebung14.aufgabe1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class BankTest {
	@Test
	public void testParallelBankOpening() throws InterruptedException {
		final Bank bank = new Bank();
		final int nofThreads = 10;
		final int nofRuns = 1000;
		Collection<Thread> allThreads = new ArrayList<>();
		for (int k = 0; k < nofThreads; k++) {
			final String id = "Account " + k;
			allThreads.add(new Thread(() -> {
				for (int i = 0; i < nofRuns; i++) {
					String name = id + "-" + i;
					Account account = bank.openAccount(name);
					assertNotNull(account);
					assertSame(account, bank.getAccount(name));
				}
			}));
		}
		for (Thread t : allThreads) {
			t.start();
		}
		for (Thread t : allThreads) {
			t.join();
		}
		assertEquals(nofThreads * nofRuns, bank.nofAccounts());
	}
}
