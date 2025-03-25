package com.epam.parser;

import com.epam.dto.ResourceMetaDataDto;
import com.epam.exception.InvalidResourceDataException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

@Component
public class ResourceMetaDataParser {

    private static final String NAME = "dc:title";

    private static final String ARTIST = "xmpDM:artist";

    private static final String ALBUM = "xmpDM:album";

    private static final String LENGTH = "xmpDM:duration";

    private static final String YEAR = "xmpDM:releaseDate";

    public ResourceMetaDataDto parse(Integer resourceId, byte[] payload) {
        InputStream inputStream = new ByteArrayInputStream(payload);
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new org.apache.tika.metadata.Metadata();
        ParseContext parseContext = new ParseContext();
        Parser parser = new Mp3Parser();
        try {
            parser.parse(inputStream, handler, metadata, parseContext);
        } catch (IOException | TikaException | SAXException exception) {
            throw new InvalidResourceDataException(
                    MessageFormat.format("ResourceId={0}: Invalid resource data", resourceId));
        }
        return new ResourceMetaDataDto(
                resourceId,
                metadata.get(NAME),
                metadata.get(ARTIST),
                metadata.get(ALBUM),
                formatDuration(metadata.get(LENGTH)),
                metadata.get(YEAR)
        );
    }

    private String formatDuration(String duration) {
        if (duration == null) {
            return null;
        }
        try {
            double seconds = Double.parseDouble(duration);
            long minutes = (long) (seconds / 60);
            long remainingSeconds = (long) seconds % 60;
            return String.format("%02d:%02d", minutes, remainingSeconds);
        } catch (NumberFormatException e) {
            throw new InvalidResourceDataException(
                    MessageFormat.format("Invalid duration format: {0}", duration));
        }
    }
}
