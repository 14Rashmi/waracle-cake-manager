package com.waracle.cakemgr.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.CakeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CakeServiceImpl implements CakeService {

    private static final Logger LOGGER = Logger.getLogger(CakeServiceImpl.class);

    @Autowired
    CakeRepository repository;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Cake> retrieveCakes() {
        LOGGER.info("Retrieving all the cakes");
        return repository.findAll();
    }

    @Override
    public void saveCake(Cake cake) {
        try {
            repository.saveAndFlush(cake);
            LOGGER.info(String.format("Saved the cake %s : ", cake.getTitle()));
        } catch (Exception ex) {
            LOGGER.error(String.format("Exception occurred while saving cake: %s : ", cake.getTitle()), ex);
            throw new CakeManagerException(String.format("Exception occurred while saving cake: %s", cake.getTitle()));
        }

    }
}
