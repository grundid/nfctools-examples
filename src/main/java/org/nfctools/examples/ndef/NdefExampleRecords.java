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

package org.nfctools.examples.ndef;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.nfctools.ndef.Record;
import org.nfctools.ndef.wkt.records.Action;
import org.nfctools.ndef.wkt.records.ActionRecord;
import org.nfctools.ndef.wkt.records.GcActionRecord;
import org.nfctools.ndef.wkt.records.GcDataRecord;
import org.nfctools.ndef.wkt.records.GcTargetRecord;
import org.nfctools.ndef.wkt.records.GenericControlRecord;
import org.nfctools.ndef.wkt.records.SmartPosterRecord;
import org.nfctools.ndef.wkt.records.TextRecord;
import org.nfctools.ndef.wkt.records.UriRecord;

/**
 * Demo NDEF records for the examples.
 * 
 */
public class NdefExampleRecords {

	public static GenericControlRecord createVibrateModeGc() {
		GenericControlRecord gcr = new GenericControlRecord(new GcTargetRecord(new TextRecord("PropertyManager")));
		gcr.setAction(new GcActionRecord(new TextRecord("SilentMode")));
		gcr.setData(new GcDataRecord(new TextRecord("VIBRATE")));
		return gcr;
	}

	public static GenericControlRecord createNavigationGc() {
		GenericControlRecord gcr = new GenericControlRecord(new GcTargetRecord(new TextRecord("Navigation")));
		gcr.setAction(new GcActionRecord(Action.DEFAULT_ACTION));
		gcr.setData(new GcDataRecord(new TextRecord("D", "Street 3, 12345 City")));
		return gcr;
	}

	public static GenericControlRecord createSilentModeGc() {
		GenericControlRecord gcr = new GenericControlRecord(new GcTargetRecord(new TextRecord("PropertyManager")));
		gcr.setAction(new GcActionRecord(new TextRecord("SilentMode")));
		gcr.setData(new GcDataRecord(new TextRecord("ON")));
		return gcr;
	}

	public static GenericControlRecord createSilentModeOffGc() {
		GenericControlRecord gcr = new GenericControlRecord(new GcTargetRecord(new TextRecord("PropertyManager")));
		gcr.setAction(new GcActionRecord(new TextRecord("SilentMode")));
		gcr.setData(new GcDataRecord(new TextRecord("OFF")));
		return gcr;
	}

	public static GenericControlRecord createWifiSetting() {
		GenericControlRecord gcr = new GenericControlRecord(new GcTargetRecord(new TextRecord("WifiManager")));
		gcr.setAction(new GcActionRecord(Action.DEFAULT_ACTION));
		gcr.setData(new GcDataRecord(new TextRecord("T", "WPA"), new TextRecord("S", "somelan"), new TextRecord("P",
				"26888282a6eff09dbc7e91e61dccf99bd660881ea69a6fad5941288f6cbc0f35")));
		return gcr;
	}

	public static UriRecord createUri() {
		UriRecord uriRecord = new UriRecord("http://www.google.com/");
		return uriRecord;
	}

	public static SmartPosterRecord createSmartPoster() {
		SmartPosterRecord spr = new SmartPosterRecord();
		spr.setTitle(new TextRecord("Hello, this is a SmartPosterTag for NFC Tools", Charset.forName("UTF8"),
				Locale.ENGLISH));
		spr.setUri(new UriRecord("http://www.nfctools.org"));
		spr.setAction(new ActionRecord(Action.DEFAULT_ACTION));
		return spr;
	}

	public static List<Record> convertToList(Record... records) {
		List<Record> returnRecords = new ArrayList<Record>();
		for (Record record : records) {
			returnRecords.add(record);
		}
		return returnRecords;
	}

}
