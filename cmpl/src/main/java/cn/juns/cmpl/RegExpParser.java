package cn.juns.cmpl;

import cn.juns.cmpl.Tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RegExpParser {
    private String regexp;

    public static void main(String[] args) {
        String s = "(xx(x))";
//        int idx = RegExpParser.findRBracketIndexindRBracketIndex(s, 3, s.length());
//        System.out.println(idx);
        Element e = RegExpParser.parse("(ab|n*jh+)?");
        System.out.println(e);
    }
    /**
     * a  -> a
     * ab -> a concat b
     * a|b -> a or b
     * a*  -> a zero_more
     * a+  -> a one_more
     * a?  -> a zero_one
     * a(b|c)*  a concat ((b or c) zero_more)
     *
     * 碰到一个字母 构造一个"Char节点"，看看其前继节点，如果有，构造成"连接节点"，替换前继节点
     *
     *
     */
    public static Element parse(String regexp) {
        if (regexp == null || regexp.length() == 0) {
            throw new RuntimeException("表达式为空");
        }

        int index = 0;
        int len = regexp.length();

        Element root = null;
        Element curr = null;
        Element e;
        char nx;
        while (index < len) {
            char c = regexp.charAt(index++);
            switch (c) {
                case '(':
                    int rbi = findRBracketIndex(regexp, index, len);
                    e = new Bracket(parse(regexp.substring(index, rbi)), null);
                    index = rbi + 1;
                    if (index < len) {
                        nx = regexp.charAt(index++);
                        if (nx == '*') {
                            e = new More(0, -1, e, null);
                        } else if (nx == '+') {
                            e = new More(1, -1, e, null);
                        } else if (nx == '?') {
                            e = new More(0, 1, e, null);
                        } else {
                            index--;
                        }
                    }

                    if (curr == null) {
                        curr = e;
                        root = e;
                    } else if (curr.getRight() == null) {
                        root = new Concat(curr, e);
                        curr = root;
                    } else {
                        Element ne = new Concat(curr.getRight(), e);
                        curr.setRight(ne);
                        curr = ne;
                    }
                    break;
                case '|':
                    if (curr == null) throw new RuntimeException("表达式错误");
                    return new Or(root, parse(regexp.substring(index)));
                default:
                    e = new LChar(c);
                    if (index < len) {
                        nx = regexp.charAt(index++);
                        if (nx == '*') {
                            e = new More(0, -1, e, null);
                        } else if (nx == '+') {
                            e = new More(1, -1, e, null);
                        } else if (nx == '?') {
                            e = new More(0, 1, e, null);
                        } else {
                            index--;
                        }
                    }

                    if (curr == null) {
                        curr = e;
                        root = e;
                    } else if (curr.getRight() == null) {
                        root = new Concat(curr, e);
                        curr = root;
                    } else {
                        Element ne = new Concat(curr.getRight(), e);
                        curr.setRight(ne);
                        curr = ne;
                    }
                    break;
            }
        }
        return root;
    }

     static int findRBracketIndex(String s, int from, int end) {
        Stack<Character> stack = new Stack<>();
        int idx = from;
        while (++idx < end) {
            char c = s.charAt(idx);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return idx;
                } else {
                    stack.pop();
                }
            }
        }
        throw new RuntimeException("表达式错误");
    }

    static Element testNext(){
        return null;
    }

}
