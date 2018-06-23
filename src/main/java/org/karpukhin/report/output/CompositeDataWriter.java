package org.karpukhin.report.output;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class CompositeDataWriter<T> implements DataWriter<T> {

    @NonNull
    private final Collection<DataWriter<T>> dataWriters;

    @Override
    public void write(T data) {
        for (DataWriter<T> dataWriter : dataWriters) {
            dataWriter.write(data);
        }
    }
}
