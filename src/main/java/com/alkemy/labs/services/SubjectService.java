package com.alkemy.labs.services;

import com.alkemy.labs.models.Subject;
import com.alkemy.labs.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired private SubjectRepository subjectRepository;


    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubject(int id) {
        return subjectRepository.findById(id);
    }

    public void save(Subject subject){
        subjectRepository.save(subject);
    }

    public void delete(int id){
        subjectRepository.deleteById(id);
    }

    public Page<Subject> findPaginated (int pageNo,
                                        int pageSize,
                                        String sortField,
                                        String sortDirection){
        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                                                             : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo -1 , pageSize,sort);
        return this.subjectRepository.findAll(pageable);
    }



}
