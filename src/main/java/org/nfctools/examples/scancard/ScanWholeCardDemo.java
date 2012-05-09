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

import org.nfctools.examples.AbstractDemo;
import org.nfctools.mf.tools.MfClassicCardScanner;

/**
 * ScanWholeCardDemo scans the whole Mifare Classic card and shows the contents in the log.
 * 
 */
public class ScanWholeCardDemo extends AbstractDemo {

	private void runDemo() throws IOException {
		launchDemo(new MfClassicCardScanner());
	}

	public static void main(String[] args) {

		try {
			ScanWholeCardDemo demo = new ScanWholeCardDemo();
			demo.runDemo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
