package org.karpukhin.report;

import org.karpukhin.report.job.DailyJob;
import org.karpukhin.report.job.JobExecutor;
import org.karpukhin.report.job.ReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class ReportApplication implements ApplicationRunner {

    @Autowired
    private DailyJob dailyJob;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(ReportConfiguration.DATE, "16.06.2018");
        data.put(ReportConfiguration.AGENT, "some-agent");
        data.put(ReportConfiguration.EMAIL, "test@email.com");

        JobExecutor executor = new JobExecutor(dailyJob);
        executor.execute(data);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }
}
