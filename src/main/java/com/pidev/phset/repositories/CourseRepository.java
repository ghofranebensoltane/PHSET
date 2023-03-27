package com.pidev.phset.repositories;

import com.pidev.phset.entities.Course;
import com.pidev.phset.entities.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {

    Set<Course> getCoursesByTechnologyIs(Technology technology);
}
