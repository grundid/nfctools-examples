/**
 * Copyright 2011 Adrian Stabiszewski, as@nfctools.org
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

package org.nfctools.examples.nfcip;

import org.nfctools.nfcip.NFCIPConnection;
import org.nfctools.nfcip.NFCIPManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NfcTarget implements Runnable {

	private Logger log = LoggerFactory.getLogger(getClass());
	private NFCIPManager nfcipManager;

	public NfcTarget(NFCIPManager nfcipManager) {
		this.nfcipManager = nfcipManager;
	}

	@Override
	public void run() {

		try {
			while (true) {

				log.info("Waiting for connection");
				NFCIPConnection nfcipConnection = nfcipManager.connectAsTarget();

				log.info("Connected, waiting for data...");

				byte[] data = null;
				int runs = 0;
				do {
					log.trace("Start of Run: " + runs);
					data = nfcipConnection.receive();
					log.info("Received: " + data.length + " Runs: " + runs);
					nfcipConnection.send(data);
					log.trace("End of Run: " + runs);
					runs++;
				} while (data != null && data.length > 0);

				log.info("DONE!!!");

				log.info("Closing connection");
				nfcipConnection.close();
				Thread.sleep(500);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
