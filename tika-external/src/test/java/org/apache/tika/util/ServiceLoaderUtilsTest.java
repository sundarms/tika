package org.apache.tika.util;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.Parser;
import org.junit.Test;

public class ServiceLoaderUtilsTest {
	@Test
    public void testOrdering() throws Exception {
        //make sure that non Tika parsers come last
        //which means that they'll overwrite Tika parsers and
        //be preferred.
        DefaultParser defaultParser = new DefaultParser();
        int vorbisIndex = -1;
        int fictIndex = -1;
        int dcxmlIndex = -1;
        int i = 0;
        for (Parser p : defaultParser.getAllComponentParsers()) {
        	if ("class org.gagravarr.tika.VorbisParser".equals(p.getClass().toString())) {
        		vorbisIndex = i;
            }
            if ("class org.apache.tika.parser.xml.FictionBookParser".equals(p.getClass().toString())) {
                fictIndex = i;
            }
            if ("class org.apache.tika.parser.xml.DcXMLParser".equals(p.getClass().toString())) {
                dcxmlIndex = i;
            }
            i++;
        }

        assertNotEquals(vorbisIndex, fictIndex);
        assertNotEquals(fictIndex, dcxmlIndex);
        assertTrue(vorbisIndex > fictIndex);
        assertTrue(fictIndex > dcxmlIndex);
    }
}
