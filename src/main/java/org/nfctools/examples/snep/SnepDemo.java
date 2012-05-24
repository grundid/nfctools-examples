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

package org.nfctools.examples.snep;

import java.io.IOException;

import org.nfctools.NfcAdapter;
import org.nfctools.examples.TerminalUtils;
import org.nfctools.llcp.LlcpConnectionManager;
import org.nfctools.llcp.LlcpOverNfcip;
import org.nfctools.scio.TerminalMode;
import org.nfctools.snep.SnepClient;
import org.nfctools.snep.SnepConstants;
import org.nfctools.snep.SnepServer;
import org.nfctools.utils.LoggingNdefListener;

/**
 * LlcpService demonstrates a P2P connection with an Android phone. It will receive any Android Beam messages and show
 * them in the log.
 * <p>
 * With Android 2.3 please set initiatorMode to false, with Android 4+ it works best in initiatorMode set to true.
 * 
 */
public class SnepDemo {

	private boolean initiatorMode;

	private LlcpOverNfcip llcpOverNfcip;

	public SnepDemo(boolean initiatorMode) {
		this.initiatorMode = initiatorMode;
		LoggingNdefListener loggingNdefListener = new LoggingNdefListener();
		SnepServer snepServer = new SnepServer(loggingNdefListener);
		SnepClient snepClient = new SnepClient();
		snepClient.setSnepAgentListener(new SnepAgentListenterImpl());
		llcpOverNfcip = new LlcpOverNfcip();
		LlcpConnectionManager connectionManager = llcpOverNfcip.getConnectionManager();
		connectionManager.registerServiceAccessPoint(SnepConstants.SNEP_SERVICE_ADDRESS, snepServer);
		connectionManager.registerServiceAccessPoint(snepClient);
	}

	public void runDemo() throws IOException {
		TerminalMode terminalMode = initiatorMode ? TerminalMode.INITIATOR : TerminalMode.TARGET;
		NfcAdapter nfcAdapter = new NfcAdapter(TerminalUtils.getAvailableTerminal(), terminalMode);
		nfcAdapter.setNfcipConnectionListener(llcpOverNfcip);
		nfcAdapter.startListening();
		System.out.println("Mode: " + terminalMode);
		System.out.println("Waiting for P2P, press ENTER to exit");
		System.in.read();
	}

	public static void main(String[] args) {
		try {
			boolean targetMode = args.length == 1 && args[0].equals("-target");
			SnepDemo service = new SnepDemo(!targetMode);
			service.runDemo();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
