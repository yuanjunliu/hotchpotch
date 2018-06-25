package cn.juns.cmpl;

import java.util.*;

/**
 * 子集构造算法
 * 不动点，工作表算法
 */
public class DFA {
    boolean[] fs;

    int[][] transitionTable;
    SubState start;
    Set<SubState> subStates = new HashSet<>();
    static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',};
    static final int COLUMN = 128;

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

    private void minimal(Node end) {
        Map<SubState, Integer> stateTargetMap = new HashMap<>();
        // 最终的状态图
        Set<Node> finalDFA = new HashSet<>();
        int newStateId = 0;
        int startNewState = newStateId++;
        int endNewState = newStateId++;

        Set<SubState> initSet = new HashSet<>();
        Set<SubState> endSet = new HashSet<>();

        this.subStates.forEach(subState -> {
            if (subState.nodes.contains(end)) {
                endSet.add(subState);
                stateTargetMap.put(subState, endNewState);
            } else {
                initSet.add(subState);
                stateTargetMap.put(subState, startNewState);
            }
        });
//        finalMap.put(initSet, startNewState);
//        finalMap.put(finalSet, endNewState);

        //map(c, ns) x,y,z
        //map(c, ns) a   map(c, ns) b
        // 对每一类进行split，如果不能切分开，则为一个状态
        Stack<Pair<Set<SubState>, Integer>> queue = new Stack<>();
        queue.push(new Pair<>(initSet, startNewState));
        queue.push(new Pair<>(endSet, endNewState));


        while (!queue.isEmpty()) {
            Map<Map<Character, Integer>, Set<SubState>> invertMap = new HashMap<>();
            Pair<Set<SubState>, Integer> pair = queue.pop();
            Set<SubState> curSub = pair.getK();
            Integer curState = pair.getV();

            curSub.forEach(sub -> {
                Map<Character, Integer> map = new HashMap<>();
                sub.subSides.forEach(side -> {
                    map.put(side.c, stateTargetMap.get(side.subState));
                });
                Set<SubState> s = invertMap.get(map);
                if (s == null) {
                    s = new HashSet<>();
                    invertMap.putIfAbsent(map, s);
                }
                s.add(sub);
                invertMap.putIfAbsent(map, new HashSet<>()).add(sub);
            });

            boolean first = true;
            for (Map.Entry<Map<Character, Integer>, Set<SubState>> entry
                    : invertMap.entrySet()) {
                if (first) {
                    first = false;
                    if (invertMap.size() == 1) {
                        Node node = new Node(curState, endSet.containsAll(entry.getValue()));
                        entry.getKey().forEach(node::addSide);
                        finalDFA.add(node);
                    } else {
                        queue.push(new Pair<>(entry.getValue(), curState));
                    }
                } else {
                    int state = newStateId++;
                    queue.push(new Pair<>(entry.getValue(), state));
                    entry.getValue().forEach(subState -> {
                        stateTargetMap.put(subState, state);
                    });
                }
            }
        }

        transitionTable = new int[finalDFA.size()][];
        this.fs = new boolean[finalDFA.size()];
        for (Node node : finalDFA) {
            // 转移表 哈希表 跳转表
            int[] columns = newColumn(COLUMN);

            node.sides.forEach(side -> columns[side.c] = side.state);
            transitionTable[node.state] = columns;

            if (node.accept) {
                this.fs[node.state] = true;
            }
        }
    }

    private int[] newColumn(int size) {
        int[] column = new int[size];
        for (int i = 0, len = column.length; i < len; i++) {
            column[i] = -1;
        }
        return column;
    }

    private SubState exists(Set<Node> nodes) {
        return subStates.stream().filter(subState -> subState.nodes.equals(nodes)).findFirst().orElse(null);
    }

    public boolean accept(String s) {
        int state = 0;
        for (int i = 0, len = s.length(); i < len; i++) {
            state = transitionTable[state][s.charAt(i)];
            if (state == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * str 待搜索字符串
     * greed 是否非贪婪匹配
     *
     * @param str
     */
    public List<String> search(String str, boolean greed) {
        int i = 0;
        int len = str.length();
        int tranIndex = 0;

        List<String> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        while (i < len) {
            char ch = str.charAt(i);
            int ns = transitionTable[tranIndex][ch];
            if (ns == -1) {
                if (tranIndex == 0) {
                    i++;
                } else {
                    if (greed && fs[tranIndex]) {
                        results.add(sb.toString());
                    }
                    sb.delete(0, sb.length());
                    tranIndex = 0;
                }
            } else {
                sb.append(ch);
                if (fs[ns] && !greed) {
                    // success finish
                    results.add(sb.toString());
                    sb.delete(0, sb.length());
                    tranIndex = 0;
                } else {
                    tranIndex = ns;
                }
                i++;
            }

        }
        return results;
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
