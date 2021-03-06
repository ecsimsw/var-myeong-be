package com.ecsimsw.springelk.domain;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum VariablePattern {

    JAVA(Language.JAVA, Pattern.compile("(?<=([a-zA-Z0-9_>\\]][\\s]))([a-zA-Z0-9_]*)(?=(\\s=))|(?<!return\\s)(?<!=\\s)(?<=[a-zA-Z0-9_>\\]]\\s)([a-zA-Z0-9_]*)(?=(;))|(?<=\\()([a-zA-Z0-9_]*)(?=(\\sinstanceof))|(?<=[a-zA-Z0-9_>\\]][\\s])([a-zA-Z0-9_]*)(?=(\\s:))"));

    private final Language language;
    private final Pattern pattern;

    VariablePattern(Language language, Pattern pattern) {
        this.language = language;
        this.pattern = pattern;
    }

    public static VariablePattern of(Language language) {
        return Arrays.stream(values())
                .filter(pattern -> pattern.language == language)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid language"));
    }

    public Matcher matcher(String input) {
        return pattern.matcher(input);
    }

    public boolean matches(String input) {
        return pattern.matcher(input).find();
    }

    public String getFirstMatched(String input) {
        final Matcher matcher = pattern.matcher(input);
        if(matcher.find()){
            return matcher.group();
        }
        throw new IllegalArgumentException("Unmatched input");
    }
}
