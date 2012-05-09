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

package org.nfctools.examples.clearcard;

import java.io.IOException;

import org.nfctools.examples.AbstractDemo;
import org.nfctools.mf.tools.MfClassicCardCleaner;

/**
 * ClearCardDemo will try to delete all data on a Mifare Classic card and put the card back into transport mode. It will
 * use some known keys to access the sectors.
 */
public class ClearCardDemo extends AbstractDemo {

	private void runDemo() throws IOException {
		launchDemo(new MfClassicCardCleaner());
	}

	public static void main(String[] args) {

		try {
			ClearCardDemo demo = new ClearCardDemo();
			demo.runDemo();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
