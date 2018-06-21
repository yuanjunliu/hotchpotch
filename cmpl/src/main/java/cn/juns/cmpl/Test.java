package cn.juns.cmpl;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Map<Set<Integer>, Integer> map = new HashMap<>();
        map.put(new HashSet<>(), 1);
        map.put(new HashSet<>(), 2);
        System.out.println(map);

        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);

        Set<Integer> set2 = new HashSet<>();
        set2.add(2);
        set2.add(1);
        System.out.println(set1.equals(set2));

        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(1);
        System.out.println(list1.equals(list2));
    }
}
