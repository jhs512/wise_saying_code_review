package com.ll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ll.Repository.DataJson;

public class Service {
    Repository repository = new Repository();

    String makeJson(WiseSaying wiseSaying){
        String json = "{\n" +
                "  \"id\": " + wiseSaying.id + ",\n" +
                "  \"content\": \"" + wiseSaying.content + "\",\n" +
                "  \"author\": \""+ wiseSaying.author + "\"\n" +
                "}";
        return json;
    }

    void createWiseSaying(WiseSaying wiseSaying){
        String json = makeJson(wiseSaying);
        String fileName = repository.makeJsonName(wiseSaying.id);
        repository.saveFile(fileName,json);
    }

    void updateWiseSaying(WiseSaying wiseSaying){
        String json = makeJson(wiseSaying);
        String fileName = repository.makeJsonName(wiseSaying.id);
        repository.updateFile(fileName,json);
    }

    boolean deleteWiseSaying(int id){
        String fileName = repository.makeJsonName(id);
        if(repository.isFile(fileName)){
            repository.deleteFile(fileName);
            return true;
        }
        else return false;
    }

    boolean updatable(int id){
        String fileName = repository.makeJsonName(id);
        if(repository.isFile(fileName)){
            return true;
        }
        else return false;
    }

    WiseSaying readWiseSaying(int id){
        String fileName = repository.makeJsonName(id);
        WiseSaying wiseSaying = repository.parseWiseSaying(repository.jsonToString(fileName));
        return wiseSaying;
    }

    List<String> jsonDesc(){
        List<String> list = new ArrayList<>();
        String[] files = repository.jsonsInDirectory();
        for(String file : files){
            if(file == DataJson){
                continue;
            }
            String json = repository.jsonToString(file);
            WiseSaying wiseSaying = repository.parseWiseSaying(json);
            list.add(printForm(wiseSaying));
        }
        Collections.reverse(list);
        return list;
    }

    String printForm(WiseSaying wiseSaying){
        String s = wiseSaying.id + " / " + wiseSaying.author + " / " + wiseSaying.content;
        return s;
    }

    // 내부 코드 Repository로 이동 필요 (파일 만드는 과정)
    void buildData(){
        List<String> list = new ArrayList<>();
        String[] files = repository.jsonsInDirectory();
        String data = "[\n";
        for(String file : files){
            if(file == DataJson){
            continue;
            }
            if(data != "[\n"){
                data += ",\n";
            }
            String json = repository.jsonToString(file);
            WiseSaying wiseSaying = repository.parseWiseSaying(json);
            data += "  {\n" +
                    "    \"id\": " + wiseSaying.id + ",\n" +
                    "    \"content\": \"" + wiseSaying.content + "\",\n" +
                    "    \"author\": \""+ wiseSaying.author + "\"\n" +
                    "  }";
        }
        data += "\n]";
        repository.saveFile(DataJson,data);
    }

    List<String> keywordListDesc(String keywordType, String keyword){
        List<String> list = new ArrayList<>();
        String[] files = repository.jsonsInDirectory();
        for(String file : files){
            if(file == DataJson){
                continue;
            }
            String json = repository.jsonToString(file);
            WiseSaying wiseSaying = repository.parseWiseSaying(json);
            if(keywordType.equals("author")){
                if(wiseSaying.author.contains(keyword)){
                    list.add(printForm(wiseSaying));
                }
            }else if(keywordType.equals("content")){
                if(wiseSaying.content.contains(keyword)){
                    list.add(printForm(wiseSaying));
                }
            }
        }
        Collections.reverse(list);
        return list;
    }

    void printList(List<String> list) {
        for (String str : list) {
            System.out.println(str);
        }
    }

    void pageView(List<String> list, int pageNo){
        final int MAX_WINDOW = 5;
        int page = (list.size()+4) / MAX_WINDOW;
        if(page < pageNo){
            System.out.println("페이지가 존재하지 않습니다");
        }
        else{
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");
            for(int i=0; i<5;i++){
                int l = (pageNo-1) * 5 + i;
                if(l >= list.size()) break;
                System.out.println(list.get(l));
            }
            System.out.println("----------------------");
            printPageNumbers(pageNo,page);
        }
    }

    void printPageNumbers(int pageNo, int page){
        System.out.print("페이지 :");
        for(int i=1; i<page; i++){
            printNorm(pageNo, i);
            System.out.print(" /");
        }
        printNorm(pageNo,page);
        System.out.print("\n");
    }

    void printNorm(int pageNo, int page){
        if(page == pageNo){
            System.out.print(" [%d]".formatted(page));
        }
        else{
            System.out.print(" %d".formatted(page));
        }
    }

    void saveLastId(int lastId){
        repository.saveFile("lastId.txt",String.valueOf(lastId));
    }
}
