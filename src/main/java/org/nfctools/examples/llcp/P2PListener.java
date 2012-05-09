package org.nfctools.examples.llcp;

import java.io.IOException;

import org.nfctools.nfcip.NFCIPConnection;
import org.nfctools.nfcip.NFCIPConnectionListener;

public class P2PListener implements NFCIPConnectionListener {

	@Override
	public void onConnection(NFCIPConnection connection) throws IOException {
		System.out.println("Connection...");
	}
}
