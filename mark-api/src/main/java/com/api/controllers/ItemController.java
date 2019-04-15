package com.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="item",produces=MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
	
}
