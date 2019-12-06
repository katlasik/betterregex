package io.plainregex;

import com.google.re2j.Matcher;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

class SplittingSpliterator extends Spliterators.AbstractSpliterator<String> {

    private final Matcher matcher;
    private String text;
    private int index = 0;

    SplittingSpliterator(Matcher matcher, String text) {
        super(Long.MAX_VALUE, Spliterator.ORDERED | Spliterator.SIZED);
        this.matcher = matcher;
        this.text = text;
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> consumer) {
        if (matcher.find()) {
            consumer.accept(text.substring(index, matcher.start()));
            index = matcher.end();
            return true;
        } else {
            consumer.accept(text.substring(index));
            return false;
        }
    }
}
