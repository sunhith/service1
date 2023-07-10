package com.service2.web.rest;

import com.service2.domain.Course;
import com.service2.repository.CourseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

//@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200", "http://localhost:8081" })
@RestController
@RequestMapping("/courseresource")
public class CourseResource {

	@Autowired
	private CourseJpaRepository courseRepository;

	@GetMapping("/instructors/{username}/courses")
	public List<Course> getAllCourses(@PathVariable String username) {
		return courseRepository.findAll();
	}

	@GetMapping("/instructors/courses/{id}")
	public Course getCourse(@PathVariable long id) {
		return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course Not Found with id " + id));
	}

	@DeleteMapping("/instructors/{username}/courses/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable String username, @PathVariable long id) {

		courseRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/instructors/{username}/courses/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable String username, @PathVariable long id,
			@RequestBody Course course) {

		course.setUsername(username);

		Course courseUpdated = courseRepository.save(course);

		return new ResponseEntity<Course>(courseUpdated, HttpStatus.OK);
	}

	@PostMapping("/instructors/{username}/courses")
	public ResponseEntity<Void> createCourse(@PathVariable String username, @RequestBody Course course) {

		course.setUsername(username);

		Course createdCourse = courseRepository.save(course);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}
