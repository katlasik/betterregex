package io.plainregex;

import java.util.Objects;

public class Match {

    private String text;
    private int start;
    private int end;

    public Match(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public String text() {
        return text;
    }

    public long start() {
        return start;
    }

    public long end() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return start == match.start &&
                end == match.end &&
                Objects.equals(text, match.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, start, end);
    }

    @Override
    public String toString() {
        return "Match{" +
                "text='" + text + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
