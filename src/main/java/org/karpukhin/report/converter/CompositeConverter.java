package org.karpukhin.report.converter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;

/**
 * Converter passes source through a sequence of converters
 *
 * @param <S> type of source
 * @param <R> type of result
 */
@RequiredArgsConstructor
public class CompositeConverter<S, R> implements Converter<S, R> {

    @NonNull
    private final Collection<Converter<?, ?>> delegates;

    public R convert(S source) {
        Object result = source;
        for (Converter<?, ?> converter : delegates) {
            if (result == null) {
                return null;
            }
            result = convertItem(converter, result);
        }

        return (R) result;
    }

    private <T> Object convertItem(Converter<T, ?> processor, Object source) {
        return processor.convert((T) source);
    }
}
