package cn.juns.cmpl;
import static cn.juns.cmpl.NFA.EPS;

public class Main {
    public static void main(String[] args) {
        NFA nfa = new NFA();
        nfa.start = new Node(0, false, new Node.Side('a', 1));
        nfa.end = new Node(9, true);

        nfa.addNode(new Node(1, false, new Node.Side(EPS, 2)));
        nfa.addNode(new Node(2, false, new Node.Side(EPS, 3)));
        nfa.addNode(new Node(3, false, new Node.Side(EPS, 4), new Node.Side(EPS, 6)));
        nfa.addNode(new Node(4, false, new Node.Side('b', 5)));
        nfa.addNode(new Node(6, false, new Node.Side('c', 7)));
        nfa.addNode(new Node(5, false, new Node.Side(EPS, 8)));
        nfa.addNode(new Node(7, false, new Node.Side(EPS, 8)));
        nfa.addNode(new Node(8, false, new Node.Side(EPS, 3), new Node.Side(EPS, 9)));

        nfa.addNode(nfa.end);

        System.out.println(nfa.accept("a"));
        System.out.println(nfa.accept("b"));
        System.out.println(nfa.accept("ab"));
        System.out.println(nfa.accept("abc"));
        System.out.println(nfa.accept("ac"));
        System.out.println(nfa.accept("acc"));
        System.out.println(nfa.accept("acbcbc"));
        System.out.println(nfa.accept("acababac"));

        DFA dfa = new DFA(nfa);
        System.out.println(dfa.subStates.size());
    }
}
