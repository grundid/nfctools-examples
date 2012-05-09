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

package org.nfctools.examples.llcp;

import java.io.IOException;
import java.util.Collection;

import org.nfctools.NfcAdapter;
import org.nfctools.examples.TerminalUtils;
import org.nfctools.llcp.LlcpConnectionManager;
import org.nfctools.llcp.LlcpConstants;
import org.nfctools.llcp.LlcpOverNfcip;
import org.nfctools.ndef.Record;
import org.nfctools.ndefpush.NdefPushFinishListener;
import org.nfctools.ndefpush.NdefPushLlcpService;
import org.nfctools.scio.TerminalMode;
import org.nfctools.utils.LoggingNdefListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LlcpService demonstrates a P2P connection with an Android phone. It will receive any Android Beam and NDEF Push
 * messages and show them in the log.
 * <p>
 * With Android 2.3 please set initiatorMode to false, with Android 4+ it works best in initiatorMode set to true.
 * 
 */
public class LlcpDemo {

	private Logger log = LoggerFactory.getLogger(getClass());

	private NdefPushLlcpService ndefPushLlcpService;
	private boolean initiatorMode = true;

	private LlcpOverNfcip llcpOverNfcip;

	public LlcpDemo() {
		ndefPushLlcpService = new NdefPushLlcpService(new LoggingNdefListener());
		llcpOverNfcip = new LlcpOverNfcip();
		LlcpConnectionManager connectionManager = llcpOverNfcip.getConnectionManager();
		connectionManager.registerWellKnownServiceAccessPoint(LlcpConstants.COM_ANDROID_NPP, ndefPushLlcpService);
	}

	public void addMessages(Collection<Record> ndefRecords, NdefPushFinishListener finishListener) {
		ndefPushLlcpService.addMessages(ndefRecords, finishListener);
	}

	public void runDemo() throws IOException {
		NfcAdapter nfcAdapter = new NfcAdapter(TerminalUtils.getAvailableTerminal(), TerminalMode.TARGET);
		nfcAdapter.setNfcipConnectionListener(llcpOverNfcip);
		nfcAdapter.startListening();
		System.out.println("Waiting for tags, press ENTER to exit");
		System.in.read();

	}

	public static void main(String[] args) {
		try {
			LlcpDemo service = new LlcpDemo();
			service.runDemo();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
