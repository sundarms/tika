package org.apache.tika.parser.html;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.utils.CharsetUtils;

/**
 * Character encoding detector for determining the character encoding of a
 * HTML document based on the user-defined charset in Metadata.
 *
 * @author jingyidai
 */
public class HtmlEncodingDetectorMetadataCharset implements EncodingDetector {

	@Override
	public Charset detect(final InputStream input, final Metadata metadata) throws IOException {
		// Check if there is user defined encoding in metadata
		if (metadata != null && metadata.get(Metadata.CONTENT_ENCODING) != null) {
			try {
				return CharsetUtils.forName(metadata.get(Metadata.CONTENT_ENCODING));
			} catch (final Exception e) {
				// ignore any exception
			}
		}
		return null;
	}
}
