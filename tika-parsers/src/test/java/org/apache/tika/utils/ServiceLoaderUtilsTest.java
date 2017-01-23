/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tika.utils;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.apache.tika.TikaTest;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.Parser;
import org.junit.Test;

public class ServiceLoaderUtilsTest extends TikaTest {
    @Test
    public void testOrdering() throws Exception {
        //make sure that non Tika parsers come last
        //which means that they'll overwrite Tika parsers and
        //be preferred.
        DefaultParser defaultParser = new DefaultParser();
        int rtfIndex = -1;
        int fictIndex = -1;
        int dcxmlIndex = -1;
        int i = 0;
        for (Parser p : defaultParser.getAllComponentParsers()) {
            if ("class org.apache.tika.parser.rtf.RTFParser".equals(p.getClass().toString())) {
                rtfIndex = i;
            }
            if ("class org.apache.tika.parser.xml.FictionBookParser".equals(p.getClass().toString())) {
                fictIndex = i;
            }
            if ("class org.apache.tika.parser.xml.DcXMLParser".equals(p.getClass().toString())) {
                dcxmlIndex = i;
            }
            i++;
        }

        assertNotEquals(rtfIndex, fictIndex);
        assertNotEquals(fictIndex, dcxmlIndex);
        assertTrue(rtfIndex < fictIndex);
        assertTrue(fictIndex > dcxmlIndex);
    }
}
