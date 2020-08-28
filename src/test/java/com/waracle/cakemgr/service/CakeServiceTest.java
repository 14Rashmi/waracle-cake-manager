package com.waracle.cakemgr.service;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.repository.CakeRepository;
import com.waracle.cakemgr.service.impl.CakeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CakeServiceTest {

    @Mock
    CakeRepository cakeRepository;

    @InjectMocks    CakeService cakeService = new CakeServiceImpl();


    @Test(expected = CakeManagerException.class)
    public void testExceptionThrownOnDuplicateSave() {
        Cake cake = new Cake();
        cake.setCakeId(1);
        cake.setTitle("Victoria Cake");
        cake.setDesc("Victoria Cake Description");
        cake.setImage("http://cake.com");

        Mockito.when(cakeRepository.saveAndFlush(cake)).thenReturn(cake).thenThrow(DataIntegrityViolationException.class);
        cakeService.saveCake(cake);
        cakeService.saveCake(cake);
    }

    @Test
    public void retrieveCakesTest() {
        List<Cake> cakesInDB = new ArrayList<>();
        Cake cake = new Cake();
        cake.setCakeId(1);
        cake.setTitle("Victoria Cake");
        cake.setDesc("Victoria Cake Description");
        cake.setImage("http://cake.com");
        cakesInDB.add(cake);

        cake = new Cake();
        cake.setCakeId(2);
        cake.setTitle("Black Forest");
        cake.setDesc("Black Forest Description");
        cake.setImage("http://cake1.com");
        cakesInDB.add(cake);

        Mockito.when(cakeRepository.findAll()).thenReturn(cakesInDB);
        List<Cake> cakesRetrieved = cakeService.retrieveCakes();
        Assert.assertEquals(cakesInDB, cakesRetrieved);
    }

    public void initializeCakesTest() {

    }
}
