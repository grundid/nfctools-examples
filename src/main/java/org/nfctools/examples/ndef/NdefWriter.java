package org.nfctools.examples.ndef;

import java.util.Collection;

import org.nfctools.ndef.NdefListener;
import org.nfctools.ndef.NdefOperations;
import org.nfctools.ndef.Record;
import org.nfctools.ndef.wkt.records.UriRecord;

public class NdefWriter implements NdefListener {

	@Override
	public void onNdefMessages(Collection<Record> records) {
	}

	@Override
	public void onNdefOperations(NdefOperations ndefOperations) {
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
