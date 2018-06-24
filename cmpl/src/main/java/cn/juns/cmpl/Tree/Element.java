package cn.juns.cmpl.Tree;

public abstract class Element {
    Element left;
    Element right;

    public Element(Element left, Element right) {
        this.left = left;
        this.right = right;
    }

    public Element getLeft() {
        return left;
    }

    public void setLeft(Element left) {
        this.left = left;
    }

    public Element getRight() {
        return right;
    }

    public void setRight(Element right) {
        this.right = right;
    }
}
