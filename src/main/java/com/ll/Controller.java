package com.ll;

import java.util.List;
import java.util.Scanner;

class Controller {
    Scanner scanner = new Scanner(System.in);
    Service service = new Service();
    int lastId;

    Controller(int lastId){
        this.lastId = lastId;
    }

    public void run(){
        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();
            if (cmd.equals("종료")) {
                break;
            }
            else if (cmd.equals("등록")) {
                upload();
            }
            else if (cmd.startsWith("삭제")){
                delete(idInCmd(cmd));
            }
            else if (cmd.startsWith("수정")){
                update(idInCmd(cmd));
            }
            else if (cmd.startsWith("목록")){
                read(cmd);
            }
            else if (cmd.equals("빌드")){
                build();
            }
        }
        scanner.close();
    }

    void upload(){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        ++lastId;
        WiseSaying wiseSaying = new WiseSaying(lastId, content, author);

        service.createWiseSaying(wiseSaying);
        service.saveLastId(lastId);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(lastId));
    }

    void delete(int id){
        if(service.deleteWiseSaying(id)){
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        }else{
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    void update(int id){
        if(service.updatable(id)){
            WiseSaying wiseSaying = service.readWiseSaying(id);
            System.out.print("명언(기존) : %s\n명언 : ".formatted(wiseSaying.getContent()));
            String content = scanner.nextLine();
            System.out.print("작가(기존) : %s\n작가 : ".formatted(wiseSaying.getAuthor()));
            String author = scanner.nextLine();
            wiseSaying = new WiseSaying(id, content, author);
            service.updateWiseSaying(wiseSaying);
            System.out.println("%d번 명언이 수정되었습니다.".formatted(id));
        }else{
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    void read(String cmd){
        if(cmd.length()>2){
            String readMenu = cmd.split("\\?")[1].split("=")[0];
            if(readMenu.equals("keywordType")){
                String keywordType = cmd.split("=")[1].split("&")[0];
                String keyword = cmd.split("=")[2];
                List<String> list = service.keywordListDesc(keywordType, keyword);
                System.out.println("----------------------");
                System.out.println("검색타입 : %s".formatted(keywordType));
                System.out.println("검색어 : %s".formatted(keyword));
                service.pageView(list,1);
                // TODO 페이지 넘버 입력받는 경우 ...
            }
            else{ // equals page
                int pageNo = Integer.parseInt(cmd.split("=")[1]);
                service.pageView(service.jsonDesc(),pageNo);
            }
        }
        else{
            service.pageView(service.jsonDesc(),1);
        }
    }

    void build(){
        service.buildData();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    int idInCmd(String cmd){
        return Integer.parseInt(cmd.split("=")[1]);
    }
}
