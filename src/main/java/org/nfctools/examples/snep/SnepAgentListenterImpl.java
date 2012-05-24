package org.nfctools.examples.snep;

import java.util.ArrayList;
import java.util.List;

import org.nfctools.ndef.Record;
import org.nfctools.ndef.wkt.records.UriRecord;
import org.nfctools.snep.SnepAgent;
import org.nfctools.snep.SnepAgentListener;

public class SnepAgentListenterImpl implements SnepAgentListener {

	@Override
	public void onSnepConnection(SnepAgent snepAgent) {
		List<Record> records = new ArrayList<Record>();
		records.add(new UriRecord("http://www.grundid.de"));
		snepAgent.doPut(records);
	}
}
