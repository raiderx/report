package org.karpukhin.report.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.model.ReportEntry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ReportEntryListToXlsConverter implements Converter<List<ReportEntry>, byte[]> {

    @NonNull
    private final ResourceLoader resourceLoader;
    @NonNull
    private final String template;

    @Override
    public byte[] convert(List<ReportEntry> reportEntries) {
        String finalTemplate = JobContext.replace(template);
        try (InputStream stream = resourceLoader.getResource(finalTemplate).getInputStream();
             POIFSFileSystem fs = new POIFSFileSystem(stream);
             Workbook workbook = new HSSFWorkbook(fs)) {
            Sheet sheet = workbook.getSheetAt(0);

            int i = 0;
            for (ReportEntry entry : reportEntries) {
                Row row = sheet.getRow(1 + i);
                if (row == null) {
                    row = sheet.createRow(1 + i);
                }
                row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(entry.getId());
                row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(entry.getFirstName());
                row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(entry.getLastName());
                ++i;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            log.info("List of report entries was converted to array of bytes as XLS document");
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Could not open file " + finalTemplate, e);
        }
    }
}
