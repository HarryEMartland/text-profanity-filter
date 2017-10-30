package com.gitlab.harryemartland.textprofanityfilter;

import org.apache.commons.lang3.StringUtils;

public class StartWordTreeProfanityFilter implements MultiWordProfanityFilter {

    private static final char SPACE = ' ';
    private final WordTree profanityWordTree;

    public StartWordTreeProfanityFilter(WordTree wordTree) {
        this.profanityWordTree = wordTree;
    }

    @Override
    public boolean containsProfanity(String sentence) {
        sentence = sentence.toUpperCase() + SPACE;

        int offset = 0;
        int index = 0;

        int length = sentence.length();
        do {
            boolean contains = profanityWordTree.contains(sentence, index);
            if (contains) {
                return true;
            }
            index = StringUtils.indexOf(sentence, SPACE, offset) + 1;
            offset = index;
        } while (index != -1 && index < length);

        return false;
    }
}
