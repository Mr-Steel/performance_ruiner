package net.steel.memory.linkedlist;

import org.github.jamm.MemoryMeter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryMeasure {

    private static int initialCapacity = 16;
    private static int targetCapacity = 250;

    public static void main(String[] args) {
        MemoryMeter meter = new MemoryMeter();

        List<Integer> integerArrayList = new ArrayList<>(initialCapacity);
        List<Integer> integerLinkedList = new LinkedList<>();


        for (int i = 0; i < targetCapacity; i++) {
            integerArrayList.add(i+500);
            integerLinkedList.add(i+500);
        }

        long measureIntegerArrayList = meter.measure(integerArrayList);
        long measureDeepIntegerArrayList = meter.measureDeep(integerArrayList);

        long measureIntegerLinkedList = meter.measure(integerLinkedList);
        long measureDeepIntegerLinkedList = meter.measureDeep(integerLinkedList);


        System.out.println("ArrayList size norm: " + measureIntegerArrayList);
        System.out.println("ArrayList size deep: " + measureDeepIntegerArrayList);

        System.out.println("LinkedList size norm: " + measureIntegerLinkedList);
        System.out.println("LinkedList size deep: " + measureDeepIntegerLinkedList);
    }

}
