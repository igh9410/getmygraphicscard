package com.GetMyGraphicsCard.productservice.controller;

import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebClientController {

    @Autowired
    private WebClientService webClientService;

    @GetMapping("/products")
    public List<Item> listProducts(){
        return webClientService.getProducts().block().getItems();
    }
}
