package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class CourseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_batch_id")
    private int id;

    private LocalDate startDate;
    private LocalDate endDate;
    
    //Child
    @ManyToMany(mappedBy = "courses")
    private List<Lecturer> lecturers = new ArrayList<>();

    //Parent
    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<StudentCourse> student_course = new ArrayList<>();

    //Child
    @ManyToOne 
    private Course course;

    private int maxSize = 5;

    public CourseDetail(LocalDate startDate, LocalDate endDate, Course course) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
    }

    
    public CourseDetail(LocalDate startDate, LocalDate endDate, Course course, int maxSize) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
        this.maxSize = maxSize;
    }

    //Ulility Methods 
    public void addLecturer(Lecturer lecturer){
        this.lecturers.add(lecturer);
        lecturer.getCourses().add(this);
    }

    public void removeLecturer(Lecturer lecturer){
        this.lecturers.remove(lecturer);
        lecturer.getCourses().remove(this);
    }
}
