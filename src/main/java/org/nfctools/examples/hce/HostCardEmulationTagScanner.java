package org.nfctools.examples.hce;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

import org.nfctools.api.TagType;
import org.nfctools.scio.TerminalStatus;
import org.nfctools.spi.acs.AbstractTerminalTagScanner;
import org.nfctools.spi.acs.ApduTagReaderWriter;

public class HostCardEmulationTagScanner extends AbstractTerminalTagScanner {

	protected HostCardEmulationTagScanner(CardTerminal cardTerminal) {
		super(cardTerminal);
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			notifyStatus(TerminalStatus.WAITING);
			try {
				Card card = cardTerminal.connect("direct");
				ApduTagReaderWriter readerWriter = new ApduTagReaderWriter(new AcsDirectChannelTag(TagType.ISO_DEP,
						null, card));
				try {
					IsoDepTamaCommunicator tamaCommunicator = new IsoDepTamaCommunicator(readerWriter, readerWriter);
					tamaCommunicator.connectAsInitiator();
				}
				catch (Exception e1) {
					card.disconnect(true);
					e1.printStackTrace();
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						break;
					}
				}
				finally {
					waitForCardAbsent();
				}
			}
			catch (CardException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
