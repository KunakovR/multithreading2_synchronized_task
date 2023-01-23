package com.company;

import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();
    private static final int size = 1000;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                String text = generateRoute("RLRFR", 100);
//              System.out.println("Количество команд поворота направо -> " + countRight(text));
                synchronized (sizeToFreq) {
                    int right = countRight(text);
                    if (sizeToFreq.containsKey(right) == true) {
                        int count = sizeToFreq.get(right) + 1;
                        sizeToFreq.put(right, count);
                    } else sizeToFreq.put(right, 1);
                }
            }).start();
        }

        Thread.sleep(1000);

        Map.Entry<Integer, Integer> map = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println("Самое частое количество повторений " + map.getKey() + " (встретилось " + map.getValue() + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> mp : sizeToFreq.entrySet()) {
            System.out.println("- " + mp.getKey() + " (" + mp.getValue() + " раз)");
        }
    }

    public static int countRight(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) == 'R') {
                count++;
            }
        }
        return count;
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
