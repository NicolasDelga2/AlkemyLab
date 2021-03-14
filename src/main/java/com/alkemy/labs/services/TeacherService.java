package com.alkemy.labs.services;

import com.alkemy.labs.models.Teacher;
import com.alkemy.labs.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired private TeacherRepository teacherRepository;

    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacher(Integer id){
        return teacherRepository.findById(id);
    }

    public void save(Teacher teacher){
        teacherRepository.save(teacher);
    }

    public void delete(Integer id){
        teacherRepository.deleteById(id);
    }

}
