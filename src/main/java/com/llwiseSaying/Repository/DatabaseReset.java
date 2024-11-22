package com.llwiseSaying.Repository;

import java.io.File;

import static com.llwiseSaying.Repository.WiseSayingRepository.DBdirectoryPath;


public class DatabaseReset {

    public void reset() {

        File directory = new File(DBdirectoryPath);

        if (deleteDirectory(directory)) {
            System.out.println("데이터베이스가 성공적으로 삭제되었습니다!");
        } else {
            System.out.println("데이터베이스 삭제에 실패했습니다.");
        }
    }

    public boolean deleteDirectory(File directory) {



        // 디렉토리나 파일이 존재하지 않는 경우
        if (!directory.exists()) {
            System.out.println("삭제할 디렉토리나 파일이 존재하지 않습니다.");
            return false;
        }

        // 디렉토리 내부 파일 및 하위 디렉토리 삭제
        if (directory.isDirectory()) {
            File[] files = directory.listFiles(); // 디렉토리 내부 파일 목록 가져오기
            if (files != null) { // 파일이 있으면 반복적으로 삭제
                for (File file : files) {
                    if(file.isFile()) {
                        file.delete();
                    } else if(file.isDirectory()) {
                        deleteDirectory(file);
                    }

                }
            }
        }




        return true;

    }
}
