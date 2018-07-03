package org.karpukhin.report.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReportEntry {

    private int id;
    private String firstName;
    private String lastName;
}
