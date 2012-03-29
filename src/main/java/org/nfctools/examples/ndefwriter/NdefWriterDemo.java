/**
 * Copyright 2011-2012 Adrian Stabiszewski, as@nfctools.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nfctools.examples.ndefwriter;

import java.io.IOException;

import org.nfctools.examples.TerminalUtils;
import org.nfctools.examples.ndef.NdefExampleRecords;
import org.nfctools.io.NfcDevice;
import org.nfctools.mf.MfCardListener;
import org.nfctools.mf.MfReaderWriter;
import org.nfctools.mf.card.MfCard;
import org.nfctools.mf.ndef.MfNdefWriter;
import org.nfctools.ndef.NdefContext;
import org.nfctools.ndef.NdefException;
import org.nfctools.spi.acs.Acr122ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NdefWriterDemo writes an NDEF message on a Mifare Classic card.
 * 
 */
public class NdefWriterDemo implements MfCardListener {

	private static Logger log = LoggerFactory.getLogger(NdefWriterDemo.class.getName());

	private NfcDevice nfcReaderWriter = TerminalUtils.getAvailableTerminal();
	private MfReaderWriter readerWriter = new Acr122ReaderWriter(nfcReaderWriter);
	private MfNdefWriter ndefWriter = new MfNdefWriter(readerWriter, NdefContext.getNdefMessageEncoder());

	@Override
	public void cardDetected(MfCard card, MfReaderWriter readerWriter) throws IOException {
		log.info("Card found. " + card);
		long time = System.currentTimeMillis();

		try {
			ndefWriter.writeNdefMessage(card, NdefExampleRecords.convertToList(NdefExampleRecords.createWifiSetting()));
		}
		catch (NdefException e) {
			log.error(e.getMessage(), e);
		}

		log.info("All done in: " + (System.currentTimeMillis() - time) + "ms");
		System.exit(0);
	}

	public void runNdefWriter() {

		try {
			nfcReaderWriter.open();
			log.info("Listening...");
			readerWriter.setCardListener(this);
			System.in.read();
			nfcReaderWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			NdefWriterDemo demo = new NdefWriterDemo();
			demo.runNdefWriter();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
