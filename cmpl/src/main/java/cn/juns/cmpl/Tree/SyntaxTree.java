package cn.juns.cmpl.Tree;

import cn.juns.cmpl.RegExpParser;

public class SyntaxTree {
    private String regexp;
    Element root;

    public SyntaxTree(String regexp) {
        this.regexp = regexp;
        root = RegExpParser.parse(regexp);
    }


}
