package com.alkemy.labs.controllers;

import com.alkemy.labs.models.Student;
import com.alkemy.labs.models.Subject;
import com.alkemy.labs.models.User;
import com.alkemy.labs.services.StudentService;
import com.alkemy.labs.services.SubjectService;
import com.alkemy.labs.services.TeacherService;
import com.alkemy.labs.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class TemplateController {

    @Autowired private SubjectService subjectService;
    @Autowired private StudentService studentService;
    @Autowired private UserService userService;
    @Autowired private TeacherService teacherService;

    private static Logger logger =  LoggerFactory.getLogger(StudentController.class);

    @GetMapping("")
    public String getLogin (){
        return "login";
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/menu")
    public String getIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Student student = studentService.getStudents().stream()
                .filter((student1) -> student1.getDni().equals(name))
                .findFirst()
                .orElse(null);

        User user = userService.getUsers().stream().filter(u -> u.getUsername().equals(name))
                .findFirst().orElse(null);

        if (student == null) {
            model.addAttribute("username",user.getUsername());

            model.addAttribute("subjects", subjectService.getSubjects());
            model.addAttribute("countStudents", studentService.getStudents().size());
            model.addAttribute("countTeacher", teacherService.getTeachers().size());
            model.addAttribute("countUsers", userService.getUsers().size());
            model.addAttribute("countSubjects", subjectService.getSubjects().size());
            return "menu";
        }

        findPaginated(1, "name", "asc", model);
        return "menu";
    }

    @GetMapping("/menu/{pageNo}")
    public String findPaginated(@PathVariable int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Student student = studentService.getStudents().stream()
                .filter((student1) -> student1.getDni().equals(name))
                .findFirst()
                .orElse(null);

        int pageSize = subjectService.getSubjects().size();

        Page<Subject> page = subjectService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Subject> subjects = page.getContent();

        List<Subject> newSubjectList = subjects.stream().collect(Collectors.toList());
        newSubjectList.removeAll(student.getSubject());

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("subjects",newSubjectList);
        model.addAttribute("studentSubjects", student.getSubject());

        // Username
        String username = student.getName()+ " " +student.getLastname();
        model.addAttribute("username",username);

        return "menu";
    }

    @GetMapping("/loginError")
    public String loginError(){
        return "login";
    }
}
