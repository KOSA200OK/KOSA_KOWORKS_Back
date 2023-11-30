package com.my.stuff.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.FindException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.service.StuffService;

@RestController
@RequestMapping("/stuff")
public class StuffController {
    @Autowired
    private StuffService service;
    @GetMapping("/list")
    public List<StuffDTO> findAll() throws FindException{
    	return service.findAll();
    }
}
