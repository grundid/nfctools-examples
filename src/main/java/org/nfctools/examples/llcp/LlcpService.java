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

import java.util.Collection;

import org.nfctools.llcp.LlcpConnectionManager;
import org.nfctools.llcp.LlcpConstants;
import org.nfctools.llcp.LlcpOverNfcip;
import org.nfctools.ndef.NdefListener;
import org.nfctools.ndef.Record;
import org.nfctools.ndefpush.NdefPushFinishListener;
import org.nfctools.ndefpush.NdefPushLlcpService;
import org.nfctools.scio.Terminal;
import org.nfctools.scio.TerminalHandler;
import org.nfctools.scio.TerminalStatusListener;
import org.nfctools.spi.acs.AcsTerminal;
import org.nfctools.spi.scm.SclTerminal;
import org.nfctools.utils.LoggingNdefListener;
import org.nfctools.utils.LoggingStatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LlcpService demonstrates a P2P connection with an Android phone. It will receive any Android Beam and NDEF Push
 * messages and show them in the log.
 * <p>
 * With Android 2.3 please set initiatorMode to false, with Android 4+ it works best in initiatorMode set to true.
 * 
 */
public class LlcpService {

	private Logger log = LoggerFactory.getLogger(getClass());

	private NdefListener ndefListener;
	private NdefPushLlcpService ndefPushLlcpService;
	private Terminal terminal;
	private boolean initiatorMode = true;

	public LlcpService(NdefListener ndefListener, TerminalStatusListener statusListener) {
		this.ndefListener = ndefListener;

		TerminalHandler terminalHandler = new TerminalHandler();
		terminalHandler.addTerminal(new AcsTerminal());
		terminalHandler.addTerminal(new SclTerminal());

		terminal = terminalHandler.getAvailableTerminal();
		terminal.setStatusListener(statusListener);
		terminal.setNdefListener(ndefListener);
		log.info("Connected to " + terminal.getTerminalName());

	}

	public void addMessages(Collection<Record> ndefRecords, NdefPushFinishListener finishListener) {
		ndefPushLlcpService.addMessages(ndefRecords, finishListener);
	}

	public String getTerminalName() {
		return terminal.getTerminalName();
	}

	public void run() {

		ndefPushLlcpService = new NdefPushLlcpService(ndefListener);
		LlcpOverNfcip llcpOverNfcip = new LlcpOverNfcip();
		LlcpConnectionManager connectionManager = llcpOverNfcip.getConnectionManager();
		connectionManager.registerWellKnownServiceAccessPoint(LlcpConstants.COM_ANDROID_NPP, ndefPushLlcpService);

		try {
			terminal.setNfcipConnectionListener(llcpOverNfcip);
			if (initiatorMode)
				terminal.initInitiatorDep();
			else
				terminal.initTargetDep();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LlcpService service = new LlcpService(new LoggingNdefListener(), new LoggingStatusListener());
		service.run();
	}

}
