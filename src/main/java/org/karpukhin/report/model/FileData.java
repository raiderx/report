package org.karpukhin.report.model;

import lombok.Data;

@Data
public class FileData {

    private String fileName;
    private byte[] content;
}
