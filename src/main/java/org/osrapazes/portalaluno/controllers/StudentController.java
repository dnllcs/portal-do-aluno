package org.osrapazes.portalaluno.controllers;

import org.osrapazes.portalaluno.models.Student;
import org.osrapazes.portalaluno.models.StudentResponseDTO;
import org.osrapazes.portalaluno.repositories.StudentRepository;
import org.osrapazes.portalaluno.services.PdfGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final PdfGeneratorService pdfGeneratorService;

    @GetMapping
    public List<StudentResponseDTO> getAll(){
        List<StudentResponseDTO> studentList = studentRepository.findAll().stream().map(StudentResponseDTO::new).toList();
        return studentList;
    }

    @PostMapping("/find-by-email")
    public Long getStudentByEmail(@RequestBody String email) {
        return studentRepository.findByEmail(email).get().getStudentId();
    }

    @GetMapping("/enrollment/{id}")
    public ResponseEntity<String> printStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        String filePath = "src/test/Comprovante de Matricula - "+ student.getStudentId() +".pdf";
        pdfGeneratorService.generateEnrollmentStatement(student, filePath);
        return ResponseEntity.ok("PDF gerado com sucesso!");
    }
}
