package org.karpukhin.report.input;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DatePrinter;
import org.apache.commons.lang3.time.FastDateFormat;
import org.karpukhin.report.job.JobContext;
import org.karpukhin.report.job.ReportConfiguration;
import org.karpukhin.report.model.ReportEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class DbDataReader implements DataReader<List<ReportEntry>> {

    private static final DatePrinter FULL = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public List<ReportEntry> read() {
        Date fromDate = JobContext.get(ReportConfiguration.FROM_DATE);
        Date toDate = JobContext.get(ReportConfiguration.TO_DATE);

        log.info("Getting data from DB for interval {} to {}", FULL.format(fromDate), FULL.format(toDate));

        List<ReportEntry> result = new ArrayList<>();
        result.add(airReport(1, "Bill", "Gates"));
        result.add(airReport(2, "Steve", "Ballmer"));
        result.add(airReport(3, "Satya", "Nadella"));
        return result;
    }

    private ReportEntry airReport(int id, String firstName, String lastName) {
        ReportEntry result = new ReportEntry();
        result.setId(id);
        result.setFirstName(firstName);
        result.setLastName(lastName);
        return result;
    }
}
