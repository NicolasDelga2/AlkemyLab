package com.alkemy.labs.controllers;

import com.alkemy.labs.models.Subject;
import com.alkemy.labs.models.Teacher;
import com.alkemy.labs.services.SubjectService;
import com.alkemy.labs.services.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TeacherService teacherService;
    private static Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @GetMapping("")
    public String getSubjects(Model model) {
        List<Teacher> teacherList = teacherService.getTeachers().stream()
                .filter(teacher -> teacher.getIsActive() != 0).collect(Collectors.toList());

        model.addAttribute("subjects", subjectService.getSubjects());
        model.addAttribute("teachers", teacherList);
        return "subject";
    }

    @GetMapping("/findById/")
    @ResponseBody
    public Optional<Subject> getSubject(int id) {
        return subjectService.getSubject(id);
    }

    @PostMapping("/addNew")
    public String addNew(Subject subject,
                         Integer idTeacher) {
        Teacher teacher = teacherService.getTeachers().stream()
                .filter(teacher1 -> teacher1.getId() == idTeacher)
                .findFirst().orElse(null);

        if (teacher == null) {
            subjectService.save(subject);
            return "redirect;/subject";
        }
        subject.setTeachers(List.of(teacher));
        subjectService.save(subject);

        return "redirect:/subject";
    }

    @PostMapping(value = "/update")
    public String update(Subject subject,
                         Integer newTeacher,
                         Integer deleteTeacher) {
        Subject subject1 = subjectService.getSubject(subject.getId()).orElse(null);

        if (newTeacher > 0) {
            Teacher newTeacher1 = teacherService.getTeachers().stream()
                    .filter(teacher1 -> teacher1.getId() == newTeacher)
                    .findFirst().orElse(null);

            if (newTeacher1 == null) {
                subjectService.save(subject);
            } else {
                List<Teacher> teacherList = subject1.getTeachers();
                if (teacherList.isEmpty()) {
                    subject.setTeachers(List.of(newTeacher1));
                } else {
                    teacherList.add(newTeacher1);
                    subject.setTeachers(teacherList);
                }
                subjectService.save(subject);
            }
        }
        if (deleteTeacher > 0) {
            List<Teacher> teacherList1 = subject1.getTeachers();
            Teacher removeTeacher = teacherList1.stream().filter(t -> t.getId() == deleteTeacher)
                    .findFirst()
                    .orElse(null);

            if (removeTeacher == null) {
                subjectService.save(subject);
            } else {
                teacherList1.remove(removeTeacher);
                subject.setTeachers(teacherList1);
                subjectService.save(subject);
            }
        }
        return "redirect:/subject";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(int id) {
        subjectService.delete(id);
        return "redirect:/subject";
    }


}
