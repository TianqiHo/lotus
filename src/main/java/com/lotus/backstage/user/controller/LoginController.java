package com.lotus.backstage.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping(value="/signIn",method = {RequestMethod.POST,RequestMethod.GET})
	public String singIn() {
		return "index";
	}
}
