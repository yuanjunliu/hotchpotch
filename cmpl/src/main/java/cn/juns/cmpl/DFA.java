package cn.juns.cmpl;

import java.util.*;

/**
 * 子集构造算法
 * 不动点，工作表算法
 */
public class DFA {
    SubState start;
    Set<SubState> subStates = new HashSet<>();
    static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',};

    public DFA(NFA nfa) {
        Set<Node> q0 = new HashSet<>();
        q0.add(nfa.start);
        this.start = new SubState(nfa.empty(q0));
        subStates.add(start);

        Stack<SubState> queue = new Stack<>();
        queue.push(this.start);

        while (!queue.isEmpty()) {
            SubState curState = queue.pop();
            for (char c : CHARS) {
                Set<Node> ns = new HashSet<>();
                curState.nodes.forEach(node -> ns.addAll(nfa.empty(nfa.destinations(c, node))));

                if (ns.isEmpty()) continue;
                SubState ss = exists(ns);
                if (ss == null) {
                    ss = new SubState(ns);
                    queue.add(ss);
                    subStates.add(ss);
                }
                curState.addSubSide(new SubSide(c, ss));
            }
        }
    }

    void minimal() {
        // 先将subStates分成两类
        //[x y z] 0
        //[a b] 1
        //map(c, ns) x,y,z
        //map(c, ns) a   map(c, ns) b

        // 对每一类进行split，如果不能切分开，则为一个状态

        // 如果可以切分开，增加新的类别
    }

    SubState exists(Set<Node> nodes) {
        return subStates.stream().filter(subState -> subState.nodes.equals(nodes)).findFirst().orElse(null);
    }

    static class SubState {
        static int i = 0;
        int state;
        Set<Node> nodes;
        List<SubSide> subSides = new ArrayList<>();

        public SubState(Set<Node> nodes) {
            this.state = i++;
            this.nodes = nodes;
        }

        void addSubSide(SubSide subSide) {
            this.subSides.add(subSide);
        }
    }

    static class SubSide {
        char c;
        SubState subState;

        public SubSide(char c, SubState subState) {
            this.c = c;
            this.subState = subState;
        }
    }
}
