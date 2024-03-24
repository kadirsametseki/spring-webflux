package com.folksdev.webflux.service;

import com.folksdev.webflux.dto.CourseDto;
import com.folksdev.webflux.dto.StudentDto;
import com.folksdev.webflux.dto.StudentListDto;
import com.folksdev.webflux.model.Student;
import com.folksdev.webflux.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public Flux<Student> findAll() {
        return studentRepository.findAll();
    }

    public Mono<StudentListDto> findAllWithCourses() {
        return studentRepository.findAll()
                .flatMap(
                        student -> {
                            List<Mono<CourseDto>> courseDtoList = student.getCourses()
                                    .stream()
                                    .map(courseId -> courseService.findById(UUID.fromString(courseId)))
                                    .collect(Collectors.toList());

                            return Flux.combineLatest(courseDtoList, objects -> {
                                List<CourseDto> courses = Arrays.stream(objects)
                                        .map(object -> (CourseDto) object)
                                        .collect(Collectors.toList());

                                return new StudentDto(student.getName(), student.getEmail(), courses);
                            });
                        })
                .collectList()
                .map(StudentListDto::new);
    }
}