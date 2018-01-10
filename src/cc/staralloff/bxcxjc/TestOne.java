package cc.staralloff.bxcxjc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestOne {

    public static void main(String[] args) {
        List<List<String>> fatherList = new LinkedList<>();
        List<String> childList = new ArrayList<>();
        childList.add("before add child list");
        fatherList.add(childList);
        childList.add("after add child list");
        childList.add("the last add");

        System.out.println(fatherList);
    }
}
