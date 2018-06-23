package org.karpukhin.report.output;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.model.FileData;

@RequiredArgsConstructor
@Slf4j
public class FileDataWriter implements DataWriter<FileData> {

    @NonNull
    private final String folder;

    public void write(FileData data) {
        log.info("File {} was written to folder {}", data.getFileName(), JobContext.replace(folder));
    }
}
