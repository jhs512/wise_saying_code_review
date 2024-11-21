package baekgwa.service;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindSayingInfo;
import baekgwa.global.data.domain.Pageable;
import baekgwa.global.data.domain.PageableResponse;
import baekgwa.global.data.domain.Search;
import java.io.IOException;
import java.util.List;

public interface WiseSayingService {

    Long registerWiseSaying(RequestDto.Register data) throws IOException;

    @Deprecated(since = "13단계", forRemoval = false)
    List<ResponseDto.FindList> findAllWiseSaying() throws IOException;

    void deleteWiseSaying(Long id) throws IOException;

    FindSayingInfo findWiseSayingInfo(Long id) throws IOException;

    void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo) throws IOException;

    void createDirectories() throws IOException;

    void build() throws IOException;

    PageableResponse<ResponseDto.FindList> search(Search searchParams, Pageable pageable) throws IOException;
}
