package org.karpukhin.report.converter;

import org.karpukhin.report.model.Report;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class ListToReportConverter implements Converter<List<?>, Report> {

    public Report convert(List<?> source) {
        Report result = new Report();
        result.setData(source);
        return result;
    }
}
