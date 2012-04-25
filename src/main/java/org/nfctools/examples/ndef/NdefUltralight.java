package org.nfctools.examples.ndef;

import org.nfctools.NfcAdapter;
import org.nfctools.examples.TerminalUtils;
import org.nfctools.mf.ul.Type2NfcTagListener;
import org.nfctools.utils.LoggingNdefListener;
import org.nfctools.utils.LoggingUnknownTagListener;

public class NdefUltralight {

	private void runTest() {
		NfcAdapter nfcAdapter = new NfcAdapter();
		nfcAdapter.registerTagListener(new Type2NfcTagListener(new LoggingNdefListener()));
		nfcAdapter.registerUnknownTagListerner(new LoggingUnknownTagListener());
		nfcAdapter.setTerminal(TerminalUtils.getAvailableTerminal());
	}

	public static void main(String[] args) {

		try {
			NdefUltralight demo = new NdefUltralight();
			demo.runTest();
			System.out.println("Waiting for tags, press ENTER to exit");
			System.in.read();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
