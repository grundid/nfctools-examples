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
import org.nfctools.llcp.LlcpConnectionManagerFactory;
import org.nfctools.llcp.LlcpOverNfcip;
import org.nfctools.scio.Terminal;
import org.nfctools.scio.TerminalMode;
import org.nfctools.snep.SnepClient;
import org.nfctools.snep.SnepConstants;
import org.nfctools.snep.SnepServer;
import org.nfctools.utils.LoggingNdefListener;

/**
 * SnepDemo demonstrates a P2P connection with an Android phone. It will receive any Android Beam messages and show them
 * in the log.
 * <p>
 * You can pass a -url parameter followed by your url to send an Andoird Beam message from this demo.
 * 
 */
public class SnepDemo {

	private LlcpOverNfcip llcpOverNfcip;
	private SnepClient snepClient;

	public SnepDemo() {
		LoggingNdefListener loggingNdefListener = new LoggingNdefListener();
		final SnepServer snepServer = new SnepServer(loggingNdefListener);
		snepClient = new SnepClient();
		llcpOverNfcip = new LlcpOverNfcip(new LlcpConnectionManagerFactory() {

			@Override
			protected void configureConnectionManager(LlcpConnectionManager connectionManager) {
				connectionManager.registerServiceAccessPoint(SnepConstants.SNEP_SERVICE_ADDRESS, snepServer);
				connectionManager.registerServiceAccessPoint(snepClient);
			}
		});
	}

	public void addUrlToSend(String url) {
		snepClient.setSnepAgentListener(new SnepAgentListenterImpl(url));
	}

	public void runDemo(boolean initiatorMode, String preferredTerminalName) throws IOException {
		TerminalMode terminalMode = initiatorMode ? TerminalMode.INITIATOR : TerminalMode.TARGET;
		Terminal terminal = TerminalUtils.getAvailableTerminal(preferredTerminalName);
		System.out.println("Using: " + terminal.getTerminalName());
		NfcAdapter nfcAdapter = new NfcAdapter(terminal, terminalMode);
		nfcAdapter.setNfcipConnectionListener(llcpOverNfcip);
		nfcAdapter.startListening();
		System.out.println("Mode: " + terminalMode);
		System.out.println("Waiting for P2P, press ENTER to exit");
		System.in.read();
	}

	private static String findParam(String name, String[] args) {
		for (int x = 0; x < args.length; x++) {
			if (("-" + name).equals(args[x]))
				return args[x + 1];
		}
		return null;
	}

	private static boolean isTargetMode(String[] args) {
		for (int x = 0; x < args.length; x++) {
			if (args[x].equals("-target"))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			boolean targetMode = isTargetMode(args);
			String preferredTerminalName = findParam("terminal", args);
			SnepDemo demo = new SnepDemo();
			String url = findParam("url", args);
			if (url != null)
				demo.addUrlToSend(url);
			demo.runDemo(!targetMode, preferredTerminalName);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
