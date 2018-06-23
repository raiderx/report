package org.karpukhin.report.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListItemConverter passes each element of source through another converter
 *
 * @param <S> type of source list element
 * @param <R> type of result list element
 */
@RequiredArgsConstructor
public class ListItemConverter<S, R> implements Converter<List<S>, List<R>> {

    @NonNull
    private final Converter<S, R> delegate;

    public List<R> convert(List<S> source) {
        return CollectionUtils.collect(source, new Transformer<S, R>() {
            public R transform(S input) {
                return delegate.convert(input);
            }
        }, new ArrayList<R>());
    }
}
