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

        Map<Map<Set<Integer>, Integer>, Integer> bigMap = new HashMap<>();
        Map<Set<Integer>, Integer> map1 = new HashMap<>();
        map1.put(new HashSet<>(), 1);
        map1.put(new HashSet<>(), 2);
        Map<Set<Integer>, Integer> map2 = new HashMap<>();
        map2.put(new HashSet<>(), 1);
        map2.put(new HashSet<>(), 2);
        bigMap.put(map1, 1);
        bigMap.put(map2, 1);
        System.out.println(bigMap.size());

    }
}
