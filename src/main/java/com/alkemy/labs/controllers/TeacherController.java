package com.alkemy.labs.controllers;

import com.alkemy.labs.models.Subject;
import com.alkemy.labs.models.Teacher;
import com.alkemy.labs.services.SubjectService;
import com.alkemy.labs.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired private TeacherService teacherService;


    @GetMapping("")
    public String getTeachers(Model model){
        model.addAttribute("teachers", teacherService.getTeachers());
        return "teacher";
    }

    @GetMapping("/findById/")
    @ResponseBody
    public Optional<Teacher> getTeacher(int id){
        return teacherService.getTeacher(id);
    }

    @PostMapping("/addNew")
    public String addNew(Teacher teacher, Integer idSubject){
        teacherService.save(teacher);
        return "redirect:/teacher";
    }

    @RequestMapping(value = "/update", method = {RequestMethod.PUT,RequestMethod.GET})
    public String update(Teacher teacher){

        teacherService.save(teacher);
        return "redirect:/teacher";
    }


    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(int id){
        teacherService.delete(id);
        return "redirect:/teacher";
    }

}
