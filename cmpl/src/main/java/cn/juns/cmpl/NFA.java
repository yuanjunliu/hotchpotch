package cn.juns.cmpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Thompson算法
 */
public class NFA {
    static final char EPS = '\0';

    Node start;
    Node end;

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
}
