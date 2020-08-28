package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.repository.CakeRepository;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Service
public class CakeInitializeService {

    private static final Logger LOGGER = Logger.getLogger(CakeInitializeService.class);

    @Value("${cake.url}")
    String cakeUrl;

    @Autowired
    CakeRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void initializeCakes() {
        BasicConfigurator.configure();
        try (InputStream inputStream = new URL(cakeUrl).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder buffer = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            List<Cake> cakes = mapper.readValue(buffer.toString(), new TypeReference<List<Cake>>() {
            });
            saveAllCakes(cakes);
            LOGGER.info("Saved the initial set of cakes");
        } catch (Exception ex) {
            LOGGER.error("Error occurred while saving initial set of cakes : ", ex);
        }
    }

    private void saveAllCakes(List<Cake> cakes) {
        for (Cake cake : cakes) {
            try {
                repository.save(cake);
            } catch (Exception ex) {
                LOGGER.warn(String.format("Exception occurred while saving cake: %s : ", cake.getTitle()));
            }
        }
    }
}
