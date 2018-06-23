package org.karpukhin.report.output;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.model.FileData;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Slf4j
public class FileDataWriter implements DataWriter<FileData> {

    @NonNull
    private final String folder;

    public void write(FileData data) {
        Path folderPath = Paths.get(JobContext.replace(folder));
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create directories " + folderPath, e);
        }

        Path filePath = folderPath.resolve(data.getFileName());
        try (OutputStream output = Files.newOutputStream(filePath)) {
            output.write(data.getContent());
            log.info("File {} was written to folder {}", data.getFileName(), folderPath);
        } catch (IOException e) {
            throw new IllegalStateException("Could not write data to file " + filePath, e);
        }
    }
}
