package org.karpukhin.report.converter;

import lombok.extern.slf4j.Slf4j;
import org.karpukhin.report.model.FileData;
import org.springframework.core.convert.converter.Converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FileDataToZipConverter implements Converter<FileData, byte[]> {

    @Override
    public byte[] convert(FileData source) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(outputStream)) {
            zip.putNextEntry(new ZipEntry(source.getFileName()));
            zip.write(source.getContent());
            zip.closeEntry();
        } catch (IOException e) {
            throw new IllegalStateException(MessageFormat.format("Could not put file {0} to zip", source.getFileName()));
        }
        log.info("File {} was converted to array of bytes as ZIP file", source.getFileName());
        return outputStream.toByteArray();
    }
}
