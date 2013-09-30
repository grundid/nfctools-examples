package org.nfctools.examples;

import java.io.IOException;

import org.nfctools.NfcAdapter;
import org.nfctools.api.NfcTagListener;
import org.nfctools.api.TagScannerListener;
import org.nfctools.scio.TerminalMode;
import org.nfctools.utils.LoggingUnknownTagListener;

public abstract class AbstractDemo implements TagScannerListener {

	@Override
	public void onScanningEnded() {
		System.out.println("Scanning ended");
	}

	@Override
	public void onScanningFailed(Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void onTagHandingFailed(Throwable throwable) {
		throwable.printStackTrace();
	}

	protected void launchDemo(NfcTagListener... listeners) throws IOException {
		NfcAdapter nfcAdapter = new NfcAdapter(TerminalUtils.getAvailableTerminal(), TerminalMode.INITIATOR, this);
		for (NfcTagListener tagListener : listeners)
			nfcAdapter.registerTagListener(tagListener);
		nfcAdapter.registerUnknownTagListerner(new LoggingUnknownTagListener());
		nfcAdapter.startListening();
		System.out.println("Waiting for tags, press ENTER to exit");
		System.in.read();
	}
}
