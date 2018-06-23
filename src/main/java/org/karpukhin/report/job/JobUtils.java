package org.karpukhin.report.job;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public abstract class JobUtils {

    public static String[] getReportTypes(Map<String, String> data) {
        String value = data.get(ReportConfiguration.REPORT_TYPE);
        return StringUtils.isNotBlank(value) ? StringUtils.split(value, ',') : new String[0];
    }
}
