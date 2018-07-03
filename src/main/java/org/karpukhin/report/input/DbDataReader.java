package org.karpukhin.report.input;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DatePrinter;
import org.apache.commons.lang3.time.FastDateFormat;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.job.ReportConfiguration;
import org.karpukhin.report.model.ReportEntry;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
public class DbDataReader implements DataReader<List<ReportEntry>> {

    private static final DatePrinter FULL = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public List<ReportEntry> read() {
        Date fromDate = JobContext.get(ReportConfiguration.FROM_DATE);
        Date toDate = JobContext.get(ReportConfiguration.TO_DATE);

        log.info("Getting data from DB for interval {} to {}", FULL.format(fromDate), FULL.format(toDate));

        return Arrays.asList(
                new ReportEntry(1, "Bill", "Gates"),
                new ReportEntry(2, "Steve", "Ballmer"),
                new ReportEntry(3, "Satya", "Nadella")
        );
    }
}
