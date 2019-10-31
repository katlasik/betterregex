package io.betterregex;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.regex.Matcher;

class FindingSpliterator extends Spliterators.AbstractSpliterator<Match> {
    private final Matcher matcher;

    FindingSpliterator(Matcher matcher) {
        super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.SIZED);
        this.matcher = matcher;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Match> consumer) {
        if (matcher.find()) {
            consumer.accept(new Match(matcher.group(), matcher.start(), matcher.end()));
            return true;
        } else {
            return false;
        }
    }

}
