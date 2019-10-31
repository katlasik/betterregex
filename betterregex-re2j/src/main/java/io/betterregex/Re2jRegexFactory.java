package io.betterregex;

import com.google.re2j.Pattern;

public class Re2jRegexFactory implements RegexFactory {

    @Override
    public RegexFlagResolver resolver() {
        return flag -> {
            switch (flag) {
                case DOTALL:
                    return Pattern.DOTALL;
                case CASE_INSENSITIVE:
                    return Pattern.CASE_INSENSITIVE;
                case MULTILINE:
                    return Pattern.MULTILINE;
                case DISABLE_UNICODE_GROUPS:
                    return Pattern.DISABLE_UNICODE_GROUPS;
                default:
                    throw new UnsupportedOperationException(String.format("Re2j regex can't handle flag: %s.", flag));
            }
        };
    }

    @Override
    public Regex create(String pattern, int flags) {
        return new Re2jRegex(pattern, flags);
    }
}
