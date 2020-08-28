package com.waracle.cakemgr.service;

import com.waracle.cakemgr.domain.Cake;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CakeService {

    List<Cake> retrieveCakes();

    void saveCake(Cake cake);
}
