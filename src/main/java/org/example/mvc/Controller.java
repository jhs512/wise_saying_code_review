
package org.example.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("== 명언 앱 ==");

        Service service = new Service(new Repository());

        while(true) {
            System.out.print("명령) ");
            String command = br.readLine();

            if(command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                service.register();
            } else if (command.startsWith("삭제")) {
                int id = Integer.parseInt(command.substring(6));
                service.delete(id);
            } else if (command.startsWith("수정")) {
                int id = Integer.parseInt(command.substring(6));
                service.update(id);
            } else if (command.equals("목록")) {
                service.getList();
            } else if (command.equals("빌드")) {
                service.build();
            }
        }
    }
}

