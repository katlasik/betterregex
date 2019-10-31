package io.betterregex;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MatchedResult<S> implements Result<S> {

    static <T> MatchedResult<T> empty() {
        return new MatchedResult<>(Stream.empty());
    }

    private final Stream<S> stream;

    MatchedResult(Stream<S> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<S> stream() {
        return stream;
    }

    @Override
    public List<S> list() {
        return stream.collect(Collectors.toList());
    }

}
