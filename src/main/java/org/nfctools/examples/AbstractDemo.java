package org.nfctools.examples;

import java.io.IOException;

import org.nfctools.NfcAdapter;
import org.nfctools.api.NfcTagListener;
import org.nfctools.scio.TerminalMode;
import org.nfctools.utils.LoggingUnknownTagListener;

public abstract class AbstractDemo {

	protected void launchDemo(NfcTagListener... listeners) throws IOException {
		NfcAdapter nfcAdapter = new NfcAdapter(TerminalUtils.getAvailableTerminal(), TerminalMode.INITIATOR);
		for (NfcTagListener tagListener : listeners)
			nfcAdapter.registerTagListener(tagListener);

		nfcAdapter.registerUnknownTagListerner(new LoggingUnknownTagListener());
		nfcAdapter.startListening();
		System.out.println("Waiting for tags, press ENTER to exit");
		System.in.read();
	}
}
