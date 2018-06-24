package cn.juns.cmpl.Tree;

public class More extends Element {
    final int min;
    final int max;

    public More(int min, int max, Element left, Element right) {
        super(left, right);
        this.min = min;
        this.max = max;
    }
}
