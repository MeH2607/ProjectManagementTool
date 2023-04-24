package com.example.projectmanagementtool.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("PRT")
public class PrtController {

@GetMapping("")
public String index(){
return "index";
}
}
