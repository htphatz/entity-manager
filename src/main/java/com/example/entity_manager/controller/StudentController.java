package com.example.entity_manager.controller;

import com.example.entity_manager.entity.Student;
import com.example.entity_manager.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentRepository.createStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findById(@PathVariable("id") String id) {
        Student student = studentRepository.findById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        studentRepository.updateStudent(id, student);
        return ResponseEntity.ok("Update successful");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {
        studentRepository.deleteStudent(id);
        return ResponseEntity.ok("Delete successful");
    }
}
