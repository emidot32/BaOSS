package edu.baoss.offerservice.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.stream.Stream;

public class StreamUtil {
    public static <T> Stream<T> toStream(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection)
                ? Stream.empty()
                : collection.stream();
    }
}
