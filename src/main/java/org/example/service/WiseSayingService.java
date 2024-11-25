package org.example.service;

import org.example.common.Parameter;
import org.example.dto.RequestPageDto;
import org.example.entity.WiseSaying;
import org.example.pagination.Page;
import org.example.pagination.Pageable;
import org.example.repository.WiseSayingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository){
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void flush(){
        wiseSayingRepository.flush();
    }

    public Map<Integer, WiseSaying> getWiseSayingMap(){
        return wiseSayingRepository.getWiseSayingMap();
    }

    public Page<WiseSaying> getWiseSayingPage(Map<Parameter, String> params){
        String pageStr = params.get((Parameter.PAGE));
        Integer page = pageStr == null ? null : Integer.parseInt(pageStr);
        String keywordTypeText = params.get(Parameter.KEYWORDTYPE);
        String keyword = params.get(Parameter.KEYWORD);

        RequestPageDto requestPageDto = new RequestPageDto(keywordTypeText, keyword);
        return new Page<>(new Pageable(page), getWiseSayingList(requestPageDto));
    }

    private List<WiseSaying> getWiseSayingList(RequestPageDto requestPageDto) {
        return getWiseSayingMap().values().stream()
                .filter(wiseSaying -> {
                    Optional<RequestPageDto.KeywordType> optionalKeywordType = requestPageDto.getKeywordType();
                    Optional<String> optionalKeyword = requestPageDto.getKeyword();

                    if(optionalKeywordType.isEmpty() && optionalKeyword.isEmpty()){
                        return true;
                    }else if(optionalKeywordType.isEmpty() ^ optionalKeyword.isEmpty()) {
                        throw new IllegalStateException();
                    }else{
                        RequestPageDto.KeywordType keywordType = optionalKeywordType.get();
                        String keyword = optionalKeyword.get();

                        if (keywordType == RequestPageDto.KeywordType.AUTHOR) {
                            return wiseSaying.getAuthor().contains(keyword);
                        } else if (keywordType == RequestPageDto.KeywordType.CONTENT) {
                            return wiseSaying.getContent().contains(keyword);
                        }

                        return false;
                    }
                }).collect(Collectors.toList());
    }


    public int registerWiseSaying(WiseSaying wiseSaying){
        return wiseSayingRepository.registerWiseSaying(wiseSaying);
    }

    public Optional<WiseSaying> getWiseSayingById(int id){
        return wiseSayingRepository.getWiseSayingById(id);
    }

    public int modifyWiseSaying(Scanner scanner, Map<Parameter, String> params) throws NumberFormatException{
        int id = Integer.parseInt(params.get(Parameter.ID));
        Optional<WiseSaying> optionalWiseSaying = getWiseSayingById(id);
        if(optionalWiseSaying.isPresent()){
            WiseSaying existingWiseSaying = optionalWiseSaying.get();
            System.out.printf("명언(기존) : %s\n명언 : ", existingWiseSaying.getContent());
            wiseSayingRepository.modifyWiseSaying(existingWiseSaying, scanner.nextLine());
            return id;
        }else {
            return -id;
        }
    }

    public int deleteWiseSaying(Map<Parameter, String> params) throws NumberFormatException{
        int id = Integer.parseInt(params.get(Parameter.ID));
        if(wiseSayingRepository.deleteWiseSaying(id)){
            return id;
        }else {
            return -id;
        }
    }
}
