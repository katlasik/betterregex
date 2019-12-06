package io.plainregex;

import java.util.List;
import java.util.stream.Stream;

public interface Result<S> {

    Stream<S> stream();
    List<S> list();

}
