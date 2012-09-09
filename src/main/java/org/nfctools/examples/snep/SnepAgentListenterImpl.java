package org.nfctools.examples.snep;

import java.util.ArrayList;
import java.util.List;

import org.nfctools.ndef.Record;
import org.nfctools.ndef.wkt.records.UriRecord;
import org.nfctools.snep.PutResponseListener;
import org.nfctools.snep.SnepAgent;
import org.nfctools.snep.SnepAgentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnepAgentListenterImpl implements SnepAgentListener, PutResponseListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	private List<Record> records = new ArrayList<Record>();

	public SnepAgentListenterImpl(String url) {
		records.add(new UriRecord(url));
	}

	@Override
	public void onSnepConnection(SnepAgent snepAgent) {
		if (!records.isEmpty()) {
			log.info("SNEP connection available, sending message...");
			snepAgent.doPut(new ArrayList<Record>(records), this);
			records.clear();
		}
	}

	@Override
	public boolean hasDataToSend() {
		return !records.isEmpty();
	}

	@Override
	public void onSuccess() {
		log.info("SNEP succeeded");
		records.clear();
	}

	@Override
	public void onFailed() {
		log.info("SNEP failed");
	}

}
