package org.karpukhin.report.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DatePrinter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.karpukhin.report.input.DataReader;
import org.karpukhin.report.output.DataWriter;
import org.springframework.core.convert.converter.Converter;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class DailyJob implements Runnable {

    private static final FastDateFormat DAY_FIRST = FastDateFormat.getInstance("dd.MM.yyyy");
    private static final DatePrinter YEAR_FIRST = FastDateFormat.getInstance("yyyy.MM.dd");

    private final DataReader<?> dataReader;
    private final Converter<?, ?> converter;
    private final DataWriter<?> dataWriter;

    @Override
    public void run() {
        Date date = getDate();
        JobContext.set(ReportConfiguration.DATE + "-year-first", YEAR_FIRST.format(date));

        JobContext.set(ReportConfiguration.FROM_DATE, DateUtils.addDays(date, -1));
        JobContext.set(ReportConfiguration.TO_DATE, date);

        Object data = dataReader.read();
        Object converted = convert(converter, data);
        write(dataWriter, converted);
    }

    private <T> Object convert(Converter<T, ?> converter, Object data) {
        return converter.convert((T) data);
    }

    private <T> void write(DataWriter<T> writer, Object data) {
        writer.write((T) data);
    }

    private Date getDate() {
        Object dateObj = JobContext.get(ReportConfiguration.DATE);
        Date date;
        if (dateObj instanceof Date) {
            date = (Date) dateObj;
        } else if (dateObj instanceof String) {
            try {
                date = DAY_FIRST.parse((String)dateObj);
            } catch (ParseException e) {
                throw new IllegalStateException("Could not parse date from " + dateObj);
            }
        } else if (dateObj == null) {
            date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            JobContext.set(ReportConfiguration.DATE, DAY_FIRST.format(date));
        } else {
            throw new IllegalStateException(MessageFormat.format("Unexpected instance of {0} for job parameter ''{1}''",dateObj.getClass(), ReportConfiguration.DATE));
        }
        return date;
    }
}
