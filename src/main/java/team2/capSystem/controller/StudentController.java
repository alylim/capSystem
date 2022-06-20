package team2.capSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import team2.capSystem.exceptions.RequestException;
import team2.capSystem.helper.courseDetailSearchQuery;
import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.StudentCourseRepository;
import team2.capSystem.repo.StudentRepository;
import team2.capSystem.services.CourseService;
import team2.capSystem.services.StudentService;

@Controller
@RequestMapping(path = "/student")
public class StudentController {
    
    @Autowired StudentRepository sRepo;
    @Autowired StudentCourseRepository scRepo;
    @Autowired CourseDetailRepository cdRepo;
    
    @Autowired 
    private StudentService studService;
    // @Autowired
    // private CourseService courseService;

    
    @RequestMapping("/student-dashboard")
    public String showDashboard(){
        return "students/student-dashboard";
    }

    @RequestMapping(path = "/course-history")
    public String showCourseHistory(HttpSession session, Model model){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        List<StudentCourse> current = studService.findStudentCoursesUngraded(usd.getUserId());
        List<StudentCourse> hist = studService.findStudentCoursesGraded(usd.getUserId());
        Double getAverageGPA=studService.getAverageGPA(usd.getUserId());
        System.out.println(getAverageGPA);
        model.addAttribute("studCourse", current);
        model.addAttribute("studHist", hist);
        model.addAttribute("avgGPA", getAverageGPA);
        return "students/student-course";

    }

    @RequestMapping(path = "/enroll*")
    public String showAvailbleCourses(HttpSession session, Model model, @ModelAttribute("CourseDetailSearchQuery") courseDetailSearchQuery search){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        List<CourseDetail> enrollCourses = studService.getStudentAvailCourses(usd, search);
        model.addAttribute("enrollCourses", enrollCourses);

        return "students/student-enroll-course";

    }
    
    @RequestMapping("/enrollCourse/" )
    public String enrollCourse(@RequestParam("cdId") int id, Model model, HttpSession session) {
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        try {
            studService.studentEnrollCourse(usd,id);
        } catch (RequestException e) {
            //TODO: handle exception
        }
        
    	return "redirect:/student/course-history";
    }

    @RequestMapping("/profile")
    public String displayStudentProfile(Model model, HttpSession session){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        Student student = studService.getStudentProfile(usd);
        model.addAttribute("student", student);
        return "students/student-profile";
    }

    @RequestMapping("")
    public String editStudentProfile(){
        return "";
    }


}
