package org.karpukhin.report.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifiedReportEntry implements Serializable {

    private int id;
    private String fullName;
}
