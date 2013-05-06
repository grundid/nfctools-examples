package org.nfctools.examples.ndef;

import java.io.IOException;

import org.nfctools.examples.AbstractDemo;
import org.nfctools.mf.classic.MfClassicNfcTagListener;
import org.nfctools.mf.ul.Type2NfcTagListener;
import org.nfctools.ndef.NdefOperationsListener;

public class NdefWriterUltralight extends AbstractDemo {

	private void runDemo() throws IOException {
		NdefOperationsListener ndefListener = new NdefWriter();
		launchDemo(new Type2NfcTagListener(ndefListener), new MfClassicNfcTagListener(ndefListener));
	}

	public static void main(String[] args) {
		try {
			NdefWriterUltralight demo = new NdefWriterUltralight();
			demo.runDemo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
