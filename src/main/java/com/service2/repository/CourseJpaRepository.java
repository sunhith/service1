package com.service2.repository;

import com.service2.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course, Long>{
	List<Course> findByUsername(String username);
}
