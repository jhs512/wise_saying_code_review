package baekgwa.service;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindSayingInfo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WiseSayingService {

    Long registerWiseSaying(RequestDto.Register data);

    @Deprecated(since = "13단계", forRemoval = false)
    List<ResponseDto.FindList> findAllWiseSaying();

    Boolean deleteWiseSaying(Long id);

    Optional<FindSayingInfo> findWiseSayingInfo(Long id);

    void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo);

    void createDirectories() throws IOException;

    void build();

    List<ResponseDto.FindList> search(Map<String, String> orders);
}
