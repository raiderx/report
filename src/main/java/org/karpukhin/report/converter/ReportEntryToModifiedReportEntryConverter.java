package org.karpukhin.report.converter;

import lombok.extern.slf4j.Slf4j;
import org.karpukhin.report.model.ReportEntry;
import org.karpukhin.report.model.ModifiedReportEntry;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class ReportEntryToModifiedReportEntryConverter implements Converter<ReportEntry, ModifiedReportEntry> {

    public ModifiedReportEntry convert(ReportEntry reportEntry) {
        ModifiedReportEntry result = new ModifiedReportEntry();
        result.setId(reportEntry.getId());
        result.setFullName(reportEntry.getFirstName() + " " + reportEntry.getLastName());
        log.info("ReportEntry was converted to ModifiedReportEntry");
        return result;
    }
}
