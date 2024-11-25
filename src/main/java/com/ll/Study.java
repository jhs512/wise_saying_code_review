package com.ll;

import java.util.Arrays;

class Study {
    public static void main(String[] args) {

        int[] A = new int[]{1,2,3};
        Object[] B = Arrays.stream(A)
                .filter(num -> num % 2 != 0)
                .mapToObj(e -> e + "번")
                .toArray();
        System.out.println(Arrays.toString(B));

        int[] C = new int[]{1,2,3};
        String[] D = Arrays.stream(A)
                .filter(num -> num % 2 != 0)
                .mapToObj(e -> e + "번")
                .toArray(String[]::new); // 그냥 외워래
        System.out.println(Arrays.toString(B));
    }
}
