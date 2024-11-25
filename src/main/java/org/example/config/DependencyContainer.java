package org.example.config;

import org.example.controller.WiseSayingController;
import org.example.repository.CreateBuildData;
import org.example.repository.FileParser;
import org.example.repository.QueryStringParser;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

public class DependencyContainer {

    public ConfigReader createConfigReader() {
        return new ConfigReader("src/main/resources/config.properties");
    }

    public FileParser createFileParser() {
        return new FileParser();
    }

    public QueryStringParser createQueryStringParser() {
        return new QueryStringParser();
    }

    public CreateBuildData createBuildData() {
        return new CreateBuildData(createConfigReader());
    }

    public WiseSayingRepository createWiseSayingRepository() {
        ConfigReader configReader = createConfigReader();
        FileParser fileToWiseSaying = createFileParser();
        return new WiseSayingRepository(configReader, fileToWiseSaying);
    }

    public WiseSayingService createWiseSayingService() {
        WiseSayingRepository wiseSayingRepository = createWiseSayingRepository();
        CreateBuildData createBuildData = createBuildData();
        return new WiseSayingService(wiseSayingRepository, createBuildData, createQueryStringParser());
    }

    public WiseSayingController createWiseSayingController() {
        return new WiseSayingController(createWiseSayingService(), createQueryStringParser());
    }

}
