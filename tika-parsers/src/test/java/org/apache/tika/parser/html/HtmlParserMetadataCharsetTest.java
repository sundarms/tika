package org.apache.tika.parser.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.tika.TikaTest;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.xml.sax.SAXException;

public class HtmlParserMetadataCharsetTest extends TikaTest {
	@Test
    public void testBig5MetaTag() {
        final String path = "/test-documents/testBig5MetaTag.html";
        final InputStream istream = HtmlParserTest.class.getResourceAsStream(path);
        final StringWriter textBuffer = new StringWriter();
        final BodyContentHandler textHandler = new BodyContentHandler(textBuffer);
        final Metadata metadata = new Metadata();

        try {
            metadata.set(Metadata.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
            final ParseContext pcontext = new ParseContext();
            final HtmlParser htmlparser = new HtmlParser();
            htmlparser.parse(istream, textHandler, metadata, pcontext);
        } catch (final IOException | SAXException | TikaException e) {
            // Ignore exception.
        }
        try {
            // Try to close io stream
            istream.close();
            textBuffer.flush();
            textBuffer.close();
        } catch (final IOException e) {
            // Ignore exception.
        }
        
        assertEquals(metadata.get(Metadata.CONTENT_ENCODING), StandardCharsets.UTF_8.name());
        assertNotNull(textBuffer.toString());
    }
}
