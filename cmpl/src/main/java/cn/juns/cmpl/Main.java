package cn.juns.cmpl;

public class Main {
    public static void main(String[] args) {
//        NFA nfa = new NFA();
//        nfa.start = new Node(0, false, new Node.Side('a', 1));
//        nfa.end = new Node(9, true);
        // a(b|c)*
//        nfa.addNode(new Node(1, false, new Node.Side(EPS, 2)));
////        nfa.addNode(new Node(2, false, new Node.Side(EPS, 3)));
//        nfa.addNode(new Node(2, false, new Node.Side(EPS, 3), new Node.Side(EPS, 9)));
//        nfa.addNode(new Node(3, false, new Node.Side(EPS, 4), new Node.Side(EPS, 6)));
//        nfa.addNode(new Node(4, false, new Node.Side('b', 5)));
//        nfa.addNode(new Node(6, false, new Node.Side('c', 7)));
//        nfa.addNode(new Node(5, false, new Node.Side(EPS, 8)));
//        nfa.addNode(new Node(7, false, new Node.Side(EPS, 8)));
//        nfa.addNode(new Node(8, false, new Node.Side(EPS, 3), new Node.Side(EPS, 9)));
//        nfa.addNode(nfa.end);
        String reg = "(h|H)(t|T)(a(b|c)*)";
        NFA nfa = new NFA(RegExpParser.parse(reg), 0);

        System.out.println(nfa.accept("hta"));
        System.out.println(nfa.accept("htabbbbb"));
        System.out.println(nfa.accept("htabcegg"));

        DFA dfa = new DFA(nfa);
        System.out.println(dfa.match("hTab"));
        System.out.println(dfa.match("htabbbbb"));
        System.out.println(dfa.match("htabcegg"));

        dfa.search("htcabhtabcbcbbbbgf", true).forEach(System.out::println);
    }


}
