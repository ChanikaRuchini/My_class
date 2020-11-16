package com.example.demo.controller;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping
    public List<Student> getAllStudent(){
        return this.studentRepository.findAll();
    }

    // get student by id
    @GetMapping("/{id}")
    public Student getUserById(@PathVariable(value = "id") long studentId) {
        return this.studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + studentId));
    }

    //create student
    @PostMapping("/students")
    public Student createUser(@RequestBody Student student) {
        return this.studentRepository.save(student);
    }



    // update user
    @PutMapping("/{id}")
    public Student updateStudent(@RequestBody Student student,@PathVariable(value = "id") long studentId){
        Student existingStudent = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + studentId));
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        return this.studentRepository.save(existingStudent);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity< Student > deleteStudent(@PathVariable("id") long studentId) {
        Student existingStudent = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + studentId));
        this.studentRepository.delete(existingStudent);
        return ResponseEntity.ok().build();
    }



//


}
