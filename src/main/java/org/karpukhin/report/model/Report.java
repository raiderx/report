package org.karpukhin.report.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement
public class Report {

    private List<?> data;
}
