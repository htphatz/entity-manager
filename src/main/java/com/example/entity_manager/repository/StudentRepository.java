package com.example.entity_manager.repository;

import com.example.entity_manager.entity.Student;
import com.example.entity_manager.exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Student createStudent(Student student) {
        student.setId(UUID.randomUUID().toString());
        entityManager.persist(student);
        return student;
    }

    public Student findById(String id) {
       Student student = entityManager.find(Student.class, id);
       if (student != null) {
           return student;
       }
       throw new NotFoundException("Student with id " + id + " not found");
    }

    public void updateStudent(String id, Student student) {
        Student existingStudent = findById(id);
        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setAddress(student.getAddress());
            entityManager.merge(existingStudent);
        }
    }

    public void deleteStudent(String id) {
        Student existingStudent = findById(id);
        if (existingStudent != null) {
            entityManager.remove(existingStudent);
        }
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT s FROM Student s";
        Query query = entityManager.createQuery(sql);
        return (List<Student>) query.getResultList();
    }
}
