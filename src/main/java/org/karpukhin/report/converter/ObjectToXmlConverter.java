package org.karpukhin.report.converter;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class ObjectToXmlConverter implements Converter<Object, byte[]> {

    private final Charset charset = StandardCharsets.UTF_8;

    private final XStream xStream;

    public ObjectToXmlConverter() {
        this(Collections.<Class, String>emptyMap());
    }

    public ObjectToXmlConverter(Map<Class, String> aliases) {
        xStream = new XStream();
        for (Map.Entry<Class, String> entry : aliases.entrySet()) {
            xStream.alias(entry.getValue(), entry.getKey());
        }
    }

    public byte[] convert(Object o) {
        log.info("Object was converted to array of bytes as XML document");
        return xStream.toXML(o).getBytes(charset);
    }
}
