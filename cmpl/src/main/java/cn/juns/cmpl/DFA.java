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
        minimal(nfa.end);
    }

    void minimal(Node end) {
        Map<SubState, Integer> stateTargetMap = new HashMap<>();
        // 先将subStates分成两类
        Map<Set<SubState>, Integer> finalMap = new HashMap<>();
        int newStateId = 0;
        int startNewState = newStateId++;
        int endNewState = newStateId++;

        Set<SubState> initSet = new HashSet<>();
        Set<SubState> finalSet = new HashSet<>();

        this.subStates.forEach(subState -> {
            if (subState.nodes.contains(end)) {
                finalSet.add(subState);
                stateTargetMap.put(subState, endNewState);
            } else {
                initSet.add(subState);
                stateTargetMap.put(subState, startNewState);
            }
        });
        finalMap.put(initSet, startNewState);
        finalMap.put(finalSet, endNewState);

        //map(c, ns) x,y,z
        //map(c, ns) a   map(c, ns) b
        // 对每一类进行split，如果不能切分开，则为一个状态
        Stack<Set<SubState>> queue = new Stack<>();
        queue.push(initSet);
        queue.push(finalSet);


        while (!queue.isEmpty()) {
            Map<Map<Character, Integer>, Set<SubState>> invertMap = new HashMap<>();

            Set<SubState> curSub = queue.pop();
            if (curSub.size() == 1) continue;

            curSub.forEach(sub -> {
                Map<Character, Integer> map = new HashMap<>();
                sub.subSides.forEach(side -> {
                    map.put(side.c, stateTargetMap.get(side.subState));
                });
                Set<SubState> set = invertMap.get(map);
                if (set == null) {
                    set = new HashSet<>();
                    invertMap.put(map, set);
                }
                set.add(sub);
            });

            if (invertMap.size() == 1) continue;

            boolean first = true;
            for (Map.Entry<Map<Character, Integer>, Set<SubState>> entry
                    : invertMap.entrySet()) {
                if (first) {
                    first = false;
                    finalMap.put(entry.getValue(), finalMap.get(curSub));
                    finalMap.remove(curSub);
                } else {
                    finalMap.put(entry.getValue(), newStateId++);
                    queue.push(entry.getValue());
                }
            }
        }

        for (Map.Entry<Set<SubState>, Integer> entry : finalMap.entrySet()) {
            Set<SubState> key = entry.getKey();
            Integer state = entry.getValue();

        }

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
