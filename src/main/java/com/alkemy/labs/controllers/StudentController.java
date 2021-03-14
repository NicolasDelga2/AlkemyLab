package com.alkemy.labs.controllers;

import com.alkemy.labs.models.Student;
import com.alkemy.labs.models.Subject;
import com.alkemy.labs.models.User;
import com.alkemy.labs.services.StudentService;
import com.alkemy.labs.services.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("student")
public class StudentController {

    private static Logger logger =  LoggerFactory.getLogger(StudentController.class);

    @Autowired private StudentService studentService;
    @Autowired private SubjectService subjectService;
    @Autowired private PasswordEncoder passwordEncoder;


    @GetMapping("")
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "student";
    }

    @GetMapping("/findById/")
    @ResponseBody
    public Optional<Student> getStudent(int id) {
        return studentService.findById(id);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT, RequestMethod.GET})
    public String update( Student student) {
        studentService.save(student);
        return "redirect:/student";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(int id) {
        studentService.delete(id);
        return "redirect:/student";
    }


    @PostMapping("/addNewStudent")
    public String saveOrUpdate(Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/error";
        } else {
            User user = new User();
            user.setUsername(student.getDni());
            user.setPassword(passwordEncoder.encode(student.getLegajo()));
            user.setRol(1);
            user.setActivo(1);
            student.setUser(user);

            studentService.save(student);
            return "redirect:/student";
        }
    }

    // Add Subject to student
    @GetMapping(value = "/addToSubject")
    public String addToSubject(int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Student student = studentService.getStudents().stream()
                .filter((student1) -> student1.getDni().equals(name))
                .findFirst()
                .orElse(null);

        logger.info(name);
        Subject findSubject = subjectService.getSubjects().stream()
                .filter(subject1 -> subject1.getId() == id)
                .findFirst()
                .orElse(null);

        findSubject.setInscriptionVacancy(findSubject.getInscriptionVacancy() - 1); // Probar esto

        List<Subject> subjectList = student.getSubject();
        subjectList.add(findSubject);
        student.setSubject(subjectList);
        studentService.save(student);

        return "redirect:/menu";
    }
    // Remove Subject in a student
    @GetMapping(value = "/removeSubject")
    public String removeSubject(int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Student student = studentService.getStudents().stream()
                .filter((student1) -> student1.getDni().equals(name))
                .findFirst()
                .orElse(null);

        logger.info(name);
        Subject findSubject = subjectService.getSubjects().stream()
                .filter(subject1 -> subject1.getId() == id)
                .findFirst()
                .orElse(null);

        findSubject.setInscriptionVacancy(findSubject.getInscriptionVacancy() + 1); // Probar esto

        List<Subject> subjectList = student.getSubject();
        subjectList.remove(findSubject);
        student.setSubject(subjectList);
        studentService.save(student);

        return "redirect:/menu";
    }


}
