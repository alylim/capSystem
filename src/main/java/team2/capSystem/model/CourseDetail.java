package team2.capSystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @ManyToMany
    private List<Lecturer> lecturers = new ArrayList<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentCourse> student_course = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    public CourseDetail(LocalDate startDate, LocalDate endDate, Course course) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.course = course;
    }
    public void addLecturer(Lecturer lecturer){
        lecturers.add(lecturer);
    }

    
}
