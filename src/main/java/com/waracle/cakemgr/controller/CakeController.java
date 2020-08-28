package com.waracle.cakemgr.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.exception.CakeManagerException;
import com.waracle.cakemgr.service.CakeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CakeController {

    private static final Logger LOGGER = Logger.getLogger(CakeController.class);

    @Autowired
    CakeService cakeService;

    @GetMapping("/")
    public String getCakesAsHumanReadable(Model model) {
        List<Cake> cakes = cakeService.retrieveCakes();
        LOGGER.info("Retrieved all the cakes");

        model.addAttribute("cakes", cakes);
        model.addAttribute("cakeModel", new Cake());
        return "index";
    }

    @GetMapping(path = "/cakes")
    public @ResponseBody
    ResponseEntity<String> cakeDataAsJSON() {
        List<Cake> cakes = cakeService.retrieveCakes();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cakes);
            LOGGER.info("Converted cakes to json format");
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while processing the json", e);
            return new ResponseEntity<>("Error while processing the json", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(jsonInString, HttpStatus.OK);
    }

    @PostMapping("/addCake")
    public String addCake(@ModelAttribute("cakeModel") Cake cake, Model model) {
        try {
            cakeService.saveCake(cake);
            model.addAttribute("message", "Cake added successfully");
        } catch (CakeManagerException ex) {
            model.addAttribute("message", ex.getMessage());
        }
        return getCakesAsHumanReadable(model);
    }

    @PostMapping(path = "/cakes", consumes = "application/json", produces = "application/json")
    public @ResponseBody
    ResponseEntity<String> postCakes(@RequestBody Cake cake) {
        try {
            cakeService.saveCake(cake);
        } catch (CakeManagerException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(cake.toString(), HttpStatus.CREATED);
    }

}
