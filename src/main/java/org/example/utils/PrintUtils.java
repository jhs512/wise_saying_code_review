package org.example.utils;

public class PrintUtils {

    // 페이지 번호와 현재 페이지 정보 출력
    public static void printPages(int page_num, int cur_page) {
        System.out.println("------------------");

        System.out.print("페이지 : ");
        for(int i=1; i<page_num; i++) {
            if (cur_page == i) {
                System.out.print("[" + i+ "] / ");
            } else {
                System.out.print(i+ " / ");
            }
        }
        if(page_num == cur_page) {
            System.out.println("[" + page_num + "]");
        }
        else {
            System.out.println(page_num);
        }
    }
}
