package org.karpukhin.report.config;

import org.karpukhin.report.converter.ByteArrayToFileDataConverter;
import org.karpukhin.report.converter.ReportEntryToModifiedReportEntryConverter;
import org.karpukhin.report.converter.CompositeConverter;
import org.karpukhin.report.converter.ListItemConverter;
import org.karpukhin.report.converter.ListToReportConverter;
import org.karpukhin.report.converter.ObjectToXmlConverter;
import org.karpukhin.report.input.DataReader;
import org.karpukhin.report.input.DbDataReader;
import org.karpukhin.report.job.DailyJob;
import org.karpukhin.report.model.FileData;
import org.karpukhin.report.model.ModifiedReportEntry;
import org.karpukhin.report.model.Report;
import org.karpukhin.report.model.ReportEntry;
import org.karpukhin.report.output.CompositeDataWriter;
import org.karpukhin.report.output.DataWriter;
import org.karpukhin.report.output.FileDataWriter;
import org.karpukhin.report.output.MailDataWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Config {

    @Value("${report.folder:/reports}")
    private String folder;

    @Bean
    public DailyJob airDailyJob() {
        DataReader<List<ReportEntry>> reader = new DbDataReader();

        Map<Class, String> aliases = new HashMap<>();
        aliases.put(Report.class, "report");
        aliases.put(ModifiedReportEntry.class, "entry");

        List<Converter<?, ?>> delegates = new ArrayList<Converter<?, ?>>();
        delegates.add(new ListItemConverter<>(new ReportEntryToModifiedReportEntryConverter()) );
        delegates.add(new ListToReportConverter());
        delegates.add(new ObjectToXmlConverter(aliases));
        delegates.add(new ByteArrayToFileDataConverter("${agent}-${date-year-first}.xml"));
        Converter<ReportEntry, FileData> converter = new CompositeConverter<>(delegates);

        DataWriter<FileData> writer = new CompositeDataWriter<>(Arrays.asList(
                new FileDataWriter(Paths.get(folder, "daily").toString()),
                new MailDataWriter("${email}")
        ));

        return new DailyJob(reader, converter, writer);
    }
}
