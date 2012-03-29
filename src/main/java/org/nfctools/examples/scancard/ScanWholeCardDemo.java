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
package org.nfctools.examples.scancard;

import java.io.IOException;

import org.nfctools.examples.TerminalUtils;
import org.nfctools.io.NfcDevice;
import org.nfctools.mf.MfCardListener;
import org.nfctools.mf.MfReaderWriter;
import org.nfctools.mf.card.MfCard;
import org.nfctools.mf.tools.CardScanner;
import org.nfctools.spi.acs.Acr122ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scans the whole Mifare classic card and shows the contents in the log.
 * 
 */
public class ScanWholeCardDemo implements MfCardListener {

	private static Logger log = LoggerFactory.getLogger(ScanWholeCardDemo.class.getName());

	private NfcDevice nfcReaderWriter = TerminalUtils.getAvailableTerminal();
	private MfReaderWriter readerWriter = new Acr122ReaderWriter(nfcReaderWriter);

	private CardScanner cardScanner = new CardScanner();

	@Override
	public void cardDetected(MfCard card, MfReaderWriter readerWriter) throws IOException {
		log.info("Card found. " + card);
		long time = System.currentTimeMillis();
		cardScanner.doWithCard(card, readerWriter);
		log.info("Done in " + (System.currentTimeMillis() - time) + "ms");

	}

	public void run() {

		try {
			nfcReaderWriter.open();
			log.info("Listening...");
			readerWriter.setCardListener(this);
			log.info("Done");
			System.in.read();
			nfcReaderWriter.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			ScanWholeCardDemo demo = new ScanWholeCardDemo();
			demo.run();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
