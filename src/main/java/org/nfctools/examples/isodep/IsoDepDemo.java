package org.nfctools.examples.isodep;

import java.io.IOException;

import org.nfctools.NfcAdapter;
import org.nfctools.examples.TerminalUtils;
import org.nfctools.scio.TerminalMode;

public class IsoDepDemo {

	private void run(boolean initiator) throws IOException {
		TerminalMode terminalMode = initiator ? TerminalMode.INITIATOR : TerminalMode.TARGET;
		NfcAdapter nfcAdapter = new NfcAdapter(TerminalUtils.getAvailableTerminal(), terminalMode);
		nfcAdapter.setNfcipConnectionListener(new IsoDepConnectionListener());
		nfcAdapter.startListening();

		System.out.println("Running...");
		System.in.read();
	}

	public static void main(String[] args) {
		try {
			IsoDepDemo demo = new IsoDepDemo();
			demo.run(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
