package dev.coms4156.project.individualproject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Represents a department within an educational institution. This class stores information about
 * the department, including its code, courses offered, department chair, and number of majors.
 */
public class Department implements Serializable {
  @Serial private static final long serialVersionUID = 234567L;
  private final Map<String, Course> courses;
  private final String departmentChair;
  private final String deptCode;
  private int numberOfMajors;

  /**
   * Constructs a new Department object with the given parameters.
   *
   * @param deptCode The code of the department.
   * @param courses A Map containing courses offered by the department.
   * @param departmentChair The name of the department chair.
   * @param numberOfMajors The number of majors in the department.
   */
  public Department(
      String deptCode, Map<String, Course> courses, String departmentChair, int numberOfMajors) {

    // check department code for null/empty-string
    if (deptCode == null || deptCode.trim().isEmpty()) {
      throw new IllegalArgumentException("Department code cannot be null or empty.");
    }

    // check department chair for null/empty-string
    if (departmentChair == null || departmentChair.trim().isEmpty()) {
      throw new IllegalArgumentException("Department chair cannot be null or empty.");
    }

    // ensure number of majors is non-negative
    if (numberOfMajors < 0) {
      throw new IllegalArgumentException("Number of majors must be a positive number.");
    }

    this.deptCode = deptCode;
    this.courses = courses;
    this.departmentChair = departmentChair;
    this.numberOfMajors = numberOfMajors;
  }

  /** Gets the department code. */
  public String getDepartmentCode() {
    return this.deptCode;
  }

  /**
   * Gets the number of majors in the department.
   *
   * @return The number of majors.
   */
  public int getNumberOfMajors() {
    return this.numberOfMajors;
  }

  /**
   * Gets the name of the department chair.
   *
   * @return The name of the department chair.
   */
  public String getDepartmentChair() {
    return this.departmentChair;
  }

  /**
   * Gets the courses offered by the department.
   *
   * @return A Map containing courses offered by the department.
   */
  public Map<String, Course> getCourseSelection() {
    return this.courses;
  }

  /** Increases the number of majors in the department by one. */
  public void addPersonToMajor() {
    this.numberOfMajors++;
  }

  /** Decreases the number of majors in the department by one if it's greater than zero. */
  public void dropPersonFromMajor() {
    if (this.numberOfMajors <= 0) {
      throw new IllegalArgumentException(
          "Can only remove a major from a department if there is at least 1 major in a"
              + "department.");
    }
    this.numberOfMajors--;
  }

  /**
   * Creates and adds a new course to the department's course selection.
   *
   * @param courseId The ID of the new course.
   * @param instructorName The name of the instructor teaching the course.
   * @param courseLocation The location where the course is held.
   * @param courseTimeSlot The time slot of the course.
   * @param capacity The maximum number of students that can enroll in the course.
   */
  public void createCourse(
      String courseId,
      String instructorName,
      String courseLocation,
      String courseTimeSlot,
      int capacity) {
    Course newCourse = new Course(instructorName, courseLocation, courseTimeSlot, capacity);
    addCourse(courseId, newCourse);
  }

  /**
   * Adds a new course to the department's course selection.
   *
   * @param courseId The ID of the course to add.
   * @param course The Course object to add.
   */
  public void addCourse(String courseId, Course course) {
    // ensure courseId is not null/empty-string
    if (courseId == null || courseId.trim().isEmpty()) {
      throw new IllegalArgumentException("courseId cannot be null or empty.");
    }
    // ensure course is not null
    if (course == null) {
      throw new IllegalArgumentException("Course cannot be null.");
    }
    courses.put(courseId, course);
  }

  /**
   * Returns a string representation of the department, including its code and the courses offered.
   *
   * @return A string representing the department.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    if (!courses.isEmpty()) {
      for (Map.Entry<String, Course> entry : courses.entrySet()) {
        String key = entry.getKey();
        Course value = entry.getValue();
        result
            .append(deptCode)
            .append(" ")
            .append(key)
            .append(": ")
            .append(value.toString())
            .append("\n");
      }
    }
    return result.toString();
  }
}
