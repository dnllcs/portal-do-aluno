package org.osrapazes.portalaluno.models;

import java.util.List;

public record StudentResponseDTO(Long id, String name, String cpf, String rg, String email) {
    public StudentResponseDTO(Student student){
        this(student.getStudentId(),student.getName(), student.getCpf(), student.getRg(),student.getEmail());
    }
}
