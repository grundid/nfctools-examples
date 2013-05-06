package org.nfctools.examples.ndef;

import org.nfctools.ndef.NdefOperations;
import org.nfctools.ndef.NdefOperationsListener;
import org.nfctools.ndef.wkt.records.UriRecord;

public class NdefWriter implements NdefOperationsListener {

	@Override
	public void onNdefOperations(NdefOperations ndefOperations) {
		System.out.println("Formated: " + ndefOperations.isFormatted() + " Writable: " + ndefOperations.isWritable());
		if (ndefOperations.isWritable()) {
			System.out.println("Writing NDEF data...");
			UriRecord record = new UriRecord("http://www.grundid.de");
			if (ndefOperations.isFormatted())
				ndefOperations.writeNdefMessage(record);
			else
				ndefOperations.format(record);
			System.out.println("Done");
		}
		else
			System.out.println("Tag not writable");
	}
}
