package com.alkemy.labs.services;

import com.alkemy.labs.models.Student;
import com.alkemy.labs.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired private StudentRepository studentRepository;

    public List<Student> getStudents (){
        return studentRepository.findAll();
    }

    public Optional<Student> findById(int id){
        return studentRepository.findById(id);
    }

    public void save(Student student){
        studentRepository.save(student);
    }

    public void delete (int id){
        studentRepository.deleteById(id);
    }
}
