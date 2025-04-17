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

    /**
     * Creates a new Student entity and persists it in the database.
     *
     * @param student The Student object to be created
     * @return The persisted Student object with the generated `id`.
     */
    public Student createStudent(Student student) {
        student.setId(UUID.randomUUID().toString());
        entityManager.persist(student);
        return student;
    }

    /**
     * Finds a Student entity by its unique identifier.
     *
     * @param id The unique identifier of the Student to be retrieved.
     * @return The Student object if found.
     * @throws NotFoundException if no Student with the given id is found.
     */
    public Student findById(String id) {
       Student student = entityManager.find(Student.class, id);
       if (student != null) {
           return student;
       }
       throw new NotFoundException("Student with id " + id + " not found");
    }

    /**
     * Updates an existing Student entity with new values.
     *
     * @param id The unique identifier of the Student to be updated.
     * @param student The Student object containing the updated values for name and address.
     */
    public void updateStudent(String id, Student student) {
        Student existingStudent = findById(id);
        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setAddress(student.getAddress());
            entityManager.merge(existingStudent);
        }
    }

    /**
     * Deletes a Student entity by its unique identifier.
     *
     * @param id The unique identifier of the Student to be deleted.
     */
    public void deleteStudent(String id) {
        Student existingStudent = findById(id);
        if (existingStudent != null) {
            entityManager.remove(existingStudent);
        }
    }

    /**
     * Retrieves all Student entities from the database.
     *
     * @return A list of all Student objects.
     */
    public List<Student> getAllStudents() {
        String sql = "SELECT s FROM Student s";
        Query query = entityManager.createQuery(sql);
        return (List<Student>) query.getResultList();
    }
}
