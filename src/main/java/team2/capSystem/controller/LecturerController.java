package team2.capSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import team2.capSystem.services.CourseService;
import team2.capSystem.services.LecturerService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/lecturer")
public class LecturerController {
    
    @Autowired
	private LecturerService lecturerService;
    
    // @Autowired
    // private CourseService courseService;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String displayDashboard() {
        return "/lecturer/dashboard";
    }
    
    
}