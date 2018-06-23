package org.karpukhin.report.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.model.FileData;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
@Slf4j
public class ByteArrayToFileDataConverter implements Converter<byte[], FileData> {

    @NonNull
    private final String fileName;

    @Override
    public FileData convert(byte[] source) {
        FileData result = new FileData();
        result.setFileName(JobContext.replace(fileName));
        result.setContent(source);
        log.info("Array of bytes was converted to file {}", result.getFileName());
        return result;
    }
}
