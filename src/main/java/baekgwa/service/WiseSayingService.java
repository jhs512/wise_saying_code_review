package baekgwa.service;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindSayingInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WiseSayingService {

    Long registerWiseSaying(RequestDto.Register data) throws IOException;

    @Deprecated(since = "13단계", forRemoval = false)
    List<ResponseDto.FindList> findAllWiseSaying() throws IOException;

    void deleteWiseSaying(Long id) throws IOException;

    FindSayingInfo findWiseSayingInfo(Long id) throws IOException;

    void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo) throws IOException;

    void createDirectories() throws IOException;

    void build() throws IOException;

    List<ResponseDto.FindList> search(Map<String, String> orders) throws IOException;
}
