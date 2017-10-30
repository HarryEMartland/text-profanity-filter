package com.gitlab.harryemartland.textprofanityfilter;

public class SplitWordTreeProfanityFilter implements SingleWordProfanityFilter {

    private final WordTree profanityWordTree;

    public SplitWordTreeProfanityFilter(WordTree wordTree) {
        this.profanityWordTree = wordTree;
    }

    @Override
    public boolean containsProfanity(String sentence) {
        String[] split = sentence.toUpperCase().split(" ");
        for (String word : split) {
            if (profanityWordTree.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
