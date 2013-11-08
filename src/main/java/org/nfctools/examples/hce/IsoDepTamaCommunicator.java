package org.nfctools.examples.hce;

import java.io.IOException;

import org.nfctools.io.ByteArrayReader;
import org.nfctools.io.ByteArrayWriter;
import org.nfctools.spi.tama.AbstractTamaCommunicator;
import org.nfctools.spi.tama.request.DataExchangeReq;
import org.nfctools.spi.tama.request.InListPassiveTargetReq;
import org.nfctools.spi.tama.response.DataExchangeResp;
import org.nfctools.spi.tama.response.InListPassiveTargetResp;
import org.nfctools.utils.NfcUtils;

public class IsoDepTamaCommunicator extends AbstractTamaCommunicator {

	private int messageCounter = 0;

	public IsoDepTamaCommunicator(ByteArrayReader reader, ByteArrayWriter writer) {
		super(reader, writer);
	}

	public void connectAsInitiator() throws IOException {
		while (true) {
			InListPassiveTargetResp inListPassiveTargetResp = sendMessage(new InListPassiveTargetReq((byte)1, (byte)0,
					new byte[0]));
			if (inListPassiveTargetResp.getNumberOfTargets() > 0) {
				System.out
						.println("TargetData: " + NfcUtils.convertBinToASCII(inListPassiveTargetResp.getTargetData()));
				if (inListPassiveTargetResp.isIsoDepSupported()) {
					System.out.println("IsoDep Supported");
					byte[] dataOut = NfcUtils.convertASCIIToBin("00A4040007F001020304050600");
					DataExchangeResp resp = sendMessage(new DataExchangeReq(inListPassiveTargetResp.getTargetId(),
							false, dataOut, 0, dataOut.length));
					String data = new String(resp.getDataOut());
					System.out.println("Received: " + data);
					if (data.startsWith("Hello")) {
						while (true) {
							dataOut = ("Message from desktop: " + messageCounter++).getBytes();
							resp = sendMessage(new DataExchangeReq(inListPassiveTargetResp.getTargetId(), false,
									dataOut, 0, dataOut.length));
							data = new String(resp.getDataOut());
							System.out.println("Received: " + data);
						}
					}
				}
				break;
			}
			else {
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
