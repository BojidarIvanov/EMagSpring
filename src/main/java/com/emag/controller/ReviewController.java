package com.emag.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comment")
public class ReviewController {

	@RequestMapping(value = "/save", method = RequestMethod.PUT)
	@ResponseBody
	public void saveComment() {
		
	}
}
