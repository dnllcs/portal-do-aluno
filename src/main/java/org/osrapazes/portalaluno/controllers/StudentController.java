package org.osrapazes.portalaluno.controllers;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.StudentRequestDTO;
import org.osrapazes.portalaluno.models.StudentResponseDTO;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.services.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody StudentRequestDTO data){
        Student studentData = new Student(data);
        repository.save(studentData);
        return;
    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<StudentResponseDTO> getAll(){

        List<StudentResponseDTO> studentList = repository.findAll().stream().map(StudentResponseDTO::new).toList();
        return studentList;
    }
    @GetMapping("/print/{cpf}")
    public ResponseEntity<String> printFoodById(@PathVariable Long id) {
        Student student = repository.findById(id).orElse(null);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        String filePath = "src/test/arquivo"+ student.getStudentId() +".pdf";
        pdfGeneratorService.generateEnrollmentStatement(student, filePath);
        return ResponseEntity.ok("PDF gerado com sucesso!");
    }
}
