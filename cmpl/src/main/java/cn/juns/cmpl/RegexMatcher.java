package cn.juns.cmpl;

import java.util.List;

public class RegexMatcher {
    private String regexp;
    private DFA dfa;

    public RegexMatcher(String regexp) {
        this.regexp = regexp;
        this.dfa = new DFA(new NFA(RegexParser.parse(regexp), 0));
    }

    public boolean match(String str) {
        return dfa.match(str);
    }

    public List<String> search(String str) {
        return dfa.search(str, true);
    }

    public List<String> search(String str, boolean greed) {
        return dfa.search(str, greed);
    }
}
