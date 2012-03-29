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

import java.io.IOException;

import org.nfctools.com.InputOutputToken;
import org.nfctools.com.SerialPortNfcDevice;
import org.nfctools.io.ByteArrayInputStreamReader;
import org.nfctools.io.ByteArrayOutputStreamWriter;
import org.nfctools.io.ByteArrayReader;
import org.nfctools.io.ByteArrayWriter;
import org.nfctools.spi.arygon.ArygonBaudRateNegotiator;
import org.nfctools.spi.arygon.ArygonLowLevelWriter;
import org.nfctools.spi.tama.nfcip.TamaNfcIpCommunicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This demo does not work atm.
 * 
 */
@Deprecated
public class NfcipDemo {

	private static Logger log = LoggerFactory.getLogger(NfcipDemo.class.getName());

	private SerialPortNfcDevice nfcDevice;

	public NfcipDemo() {
		nfcDevice = new SerialPortNfcDevice(new ArygonBaudRateNegotiator());
		nfcDevice.setBaudRate(460800);
		nfcDevice.setComPort("COM4");

	}

	public void runInitiator() {
		try {
			nfcDevice.open();

			ByteArrayReader reader = new ByteArrayInputStreamReader(
					((InputOutputToken)nfcDevice.getConnectionToken()).getInputStream());
			ByteArrayWriter writer = new ArygonLowLevelWriter(new ByteArrayOutputStreamWriter(
					((InputOutputToken)nfcDevice.getConnectionToken()).getOutputStream()));

			byte[] nfcId = { 0x0F, 0x0F, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x0F, 0x0F };

			TamaNfcIpCommunicator nfcipManager = new TamaNfcIpCommunicator(reader, writer);
			nfcipManager.setNfcId(nfcId);

			NfcInitiator nfcInitiator = new NfcInitiator(nfcipManager);
			Thread thread = new Thread(nfcInitiator);
			thread.start();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runTarget() {
		try {
			nfcDevice.open();
			ByteArrayReader reader = new ByteArrayInputStreamReader(
					((InputOutputToken)nfcDevice.getConnectionToken()).getInputStream());
			ByteArrayWriter writer = new ArygonLowLevelWriter(new ByteArrayOutputStreamWriter(
					((InputOutputToken)nfcDevice.getConnectionToken()).getOutputStream()));

			byte[] nfcId3t = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

			TamaNfcIpCommunicator nfcipManager = new TamaNfcIpCommunicator(reader, writer);
			nfcipManager.setNfcId(nfcId3t);

			NfcTarget nfcTarget = new NfcTarget(nfcipManager);
			Thread thread = new Thread(nfcTarget);
			thread.start();
			log.info("Target running...");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try {
			NfcipDemo nfcipDemo = new NfcipDemo();

			//			log.info("starting Initiator ...");
			//			nfcipDemo.runInitiator();
			//			Thread.sleep(1000);
			log.info("starting Target ...");
			nfcipDemo.runTarget();

			System.in.read();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
