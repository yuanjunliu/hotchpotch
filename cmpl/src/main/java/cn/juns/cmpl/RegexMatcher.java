package cn.juns.cmpl;

public class RegexMatcher {
    private String regexp;
    private DFA dfa;

    public RegexMatcher(String regexp) {
        this.regexp = regexp;
        this.dfa = new DFA(new NFA(RegExpParser.parse(regexp), 0));
    }

    public boolean match(String str) {
        return dfa.match(str);
    }
}
