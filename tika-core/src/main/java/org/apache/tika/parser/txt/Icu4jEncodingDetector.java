package org.apache.tika.parser.txt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.tika.detect.EncodingDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.utils.CharsetUtils;

public class Icu4jEncodingDetector implements EncodingDetector {

    public Charset detect(InputStream input, Metadata metadata)
            throws IOException {
        if (input == null) {
            return null;
        }

        CharsetDetector detector = new CharsetDetector();

        String incomingCharset = metadata.get(Metadata.CONTENT_ENCODING);
        String incomingType = metadata.get(Metadata.CONTENT_TYPE);
        if (incomingCharset == null && incomingType != null) {
            // TIKA-341: Use charset in content-type
            MediaType mt = MediaType.parse(incomingType);
            if (mt != null) {
                incomingCharset = mt.getParameters().get("charset");
            }
        }

        if (incomingCharset != null) {
            String cleaned = CharsetUtils.clean(incomingCharset);
            if (cleaned != null) {
                detector.setDeclaredEncoding(cleaned);
            } else {
                // TODO: log a warning?
            }
        }

        // TIKA-341 without enabling input filtering (stripping of tags)
        // short HTML tests don't work well
        detector.enableInputFilter(true);

        detector.setText(input);

        for (CharsetMatch match : detector.detectAll()) {
            try {
                return CharsetUtils.forName(match.getName());
            } catch (Exception e) {
                // ignore
            }
        }

        return null;
    }

}
