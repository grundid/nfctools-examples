package org.nfctools.examples.ndef;

import java.io.IOException;

import org.nfctools.examples.AbstractDemo;
import org.nfctools.mf.classic.MfClassicNfcTagListener;
import org.nfctools.mf.ul.Type2NfcTagListener;
import org.nfctools.utils.LoggingNdefListener;

public class NdefUltralight extends AbstractDemo {

	private void runDemo() throws IOException {
		LoggingNdefListener ndefListener = new LoggingNdefListener();
		launchDemo(new Type2NfcTagListener(ndefListener), new MfClassicNfcTagListener(ndefListener));
	}

	public static void main(String[] args) {

		try {
			NdefUltralight demo = new NdefUltralight();
			demo.runDemo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
