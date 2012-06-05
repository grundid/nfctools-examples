package org.nfctools.examples.isodep;

import java.io.IOException;

import org.nfctools.nfcip.NFCIPConnection;
import org.nfctools.nfcip.NFCIPConnectionListener;

public class IsoDepConnectionListener implements NFCIPConnectionListener {

	@Override
	public void onConnection(NFCIPConnection connection) throws IOException {

		System.out.println("NFCIP connection...");
		if (connection.isInitiator()) {
			System.out.println("Sending hello");
			connection.send("Hello NFC".getBytes());
		}

		int counter = 0;
		while (!Thread.interrupted()) {
			byte[] receive = connection.receive();
			System.out.println(new String(receive));
			connection.send(("Hello NFC " + counter).getBytes());
			counter++;
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				break;
			}
		}

	}

}
