package cn.juns.cmpl.Tree;

import cn.juns.cmpl.RegexParser;

public class SyntaxTree {
    private String regexp;
    Element root;

    public SyntaxTree(String regexp) {
        this.regexp = regexp;
        root = RegexParser.parse(regexp);
    }


}
