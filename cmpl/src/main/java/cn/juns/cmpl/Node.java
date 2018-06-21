package cn.juns.cmpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    int state;
    boolean accept;
    List<Side> sides = new ArrayList<>();

    public Node(int state, boolean accept, Side... sides) {
        this.state = state;
        this.accept = accept;
        if (sides != null) {
            for (int i = 0; i < sides.length; i++) {
                addSide(sides[i].c, sides[i].state);
            }
        }
    }

    public void addSide(char c, int state) {
        sides.add(new Side(c, state));
    }

    public int getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return state == node.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    static class Side {
        char c;
        int state;

        Side(char c, int state) {
            this.c = c;
            this.state = state;
        }
    }
}
