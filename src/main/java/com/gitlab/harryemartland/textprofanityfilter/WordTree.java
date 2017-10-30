package com.gitlab.harryemartland.textprofanityfilter;

import java.util.HashMap;
import java.util.Map;

public class WordTree {

    private static final int START = 0;
    private Map<Character, WordTree> children = new HashMap<>();
    private boolean isWord = false;

    public WordTree() {
    }

    public boolean contains(String word){
        return contains(word, START);
    }

    public boolean contains(String word, int offset) {
        if (word.length() == offset) {
            return isWord;
        }
        char firstLetter = word.charAt(offset);
        if (isWord && ' ' == firstLetter) {
            return true;
        }
        WordTree wordTree = children.get(firstLetter);
        if (wordTree == null) {
            return false;
        }
        return wordTree.contains(word, offset+1);
    }

    public void add(String word) {

        if (word.isEmpty()) {
            isWord = true;
            return;
        }

        char firstLetter = word.charAt(0);
        String substring = word.substring(1);
        WordTree wordTree = children.get(firstLetter);
        if (wordTree == null) {
            wordTree = new WordTree();
            children.put(firstLetter, wordTree);
        }
        wordTree.add(substring);
    }
}
