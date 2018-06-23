package org.karpukhin.report.output;

public interface DataWriter<T> {

    void write(T data);
}
