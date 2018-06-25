package cn.juns.cmpl;

import cn.juns.cmpl.Tree.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Thompson算法
 */
public class NFA {
    static final char EPS = '\0';

    Node start;
    Node end;
    int stateId;

    public NFA(Element root, int stateId) {
        this.stateId = stateId;
        if (root instanceof LChar) {
            start = createNode(false);
            end = createNode(true);
            start.addSide(((LChar) root).getC(), end.state);
        } else if (root instanceof Concat) {
            NFA left = new NFA(root.getLeft(), this.stateId);
            NFA right = new NFA(root.getRight(), left.end.state + 1);
            left.end.addSide(EPS, right.start.state);
            left.end.accept = false;
            this.start = left.start;
            this.end = right.end;
            this.nodes.putAll(left.nodes);
            this.nodes.putAll(right.nodes);
        } else if (root instanceof Or) {
            this.start = createNode(false);
            NFA left = new NFA(root.getLeft(), this.stateId);
            NFA right = new NFA(root.getRight(), left.end.state + 1);
            this.stateId = right.end.state + 1;
            this.end = createNode(true);
            this.start.addSide(EPS, left.start.state);
            this.start.addSide(EPS, right.start.state);

            left.end.accept = false;
            left.end.addSide(EPS, this.end.state);
            right.end.accept = false;
            right.end.addSide(EPS, this.end.state);
            this.nodes.putAll(left.nodes);
            this.nodes.putAll(right.nodes);
        } else if (root instanceof More) {
            this.start = createNode(false);
            NFA inner = new NFA(root.getLeft(), this.stateId);
            inner.end.accept = false;
            this.start.addSide(EPS, inner.start.state);
            this.stateId = inner.end.state + 1;
            this.end = createNode(true);
            inner.end.addSide(EPS, this.end.state);

            More el = (More) root;
            if (el.getMin() == 0) {
                this.start.addSide(EPS, this.end.state);
            }
            if (el.getMax() == -1) {
                inner.end.addSide(EPS, inner.start.state);
            }
            this.nodes.putAll(inner.nodes);
        } else if (root instanceof Bracket) {
            NFA inner = new NFA(root.getLeft(), stateId);
            this.start = inner.start;
            this.nodes = inner.nodes;
            this.end = inner.end;
            this.stateId = inner.stateId;
        }
    }

    Set<Node> destinations(char c, Node node) {
        return node.sides.stream()
                .filter(s -> s.c == c)
                .map(s -> getNodeOf(s.state))
                .collect(Collectors.toSet());
    }

    Set<Node> empty(Set<Node> nodes) {
        Set<Node> result = new HashSet<>(nodes);

        Stack<Node> stack = new Stack<>();
        stack.addAll(nodes);

        while (!stack.empty()) {
            Node node = stack.pop();
            Set<Node> r = destinations(EPS, node);
            r.forEach(n -> {
                if (!result.contains(n)) {
                    result.add(n);
                    stack.push(n);
                }
            });
        }
        return result;
    }

    Set<Node> move(Set<Node> nodes, char c) {
        Set<Node> result = new HashSet<>();
        nodes.forEach(n -> result.addAll(destinations(c, n)));
        return result;
    }

    public boolean accept(String input) {
        Set<Node> nodes = new HashSet<>();
        nodes.add(start);
        nodes = empty(nodes);

        for (int j = 0; j < input.length() && !nodes.isEmpty(); j++) {
            nodes = empty(move(nodes, input.charAt(j)));
        }
        return nodes.contains(end);
    }

    Map<Integer, Node> nodes = new HashMap<>();

    void addNode(Node node) {
        nodes.put(node.getState(), node);
    }

    Node getNodeOf(int state) {
        return nodes.get(state);
    }

    Node createNode(boolean accept) {
        Node node = new Node(stateId++, accept);
        addNode(node);
        return node;
    }

    void visit(LChar lChar) {

    }

    void visit(Concat concat) {

    }

    void visit(Or or) {

    }

    void visit(Bracket bracket) {

    }

    void visit(More more) {

    }
}
