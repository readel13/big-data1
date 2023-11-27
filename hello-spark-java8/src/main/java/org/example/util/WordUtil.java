package org.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {

    public static final Pattern WORD_PATTERN = Pattern.compile("\\w+");

    public static List<String> getWords(String commitMessage) {
        Matcher matcher = WORD_PATTERN.matcher(commitMessage);

        List<String> result = new ArrayList<>();

        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}
