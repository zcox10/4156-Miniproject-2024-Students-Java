package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Department} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Department} class
 * methods.
 */
public class DepartmentTests {

  /** The test Department instance used for testing. */
  private Department department;

  /** The test Course instances used for testing. */
  private Course course1;

  private Course course2;

  /** The map of course IDs to Course instances for testing. */
  private Map<String, Course> courses;

  /** An empty Department instance used for testing. */
  private Department emptyDepartment;

  /** Sets up the Department unit tests by initializing test Course and Department instances. */
  @BeforeEach
  public void departmentSetUp() {
    course1 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 30);
    course2 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    courses = new HashMap<>();
    courses.put("1004", course1);
    courses.put("3251", course2);
    department = new Department("COMS", courses, "Luca Carloni", 2700);
  }

  /**
   * Tests if the Department constructor correctly creates a department instance with valid
   * parameters.
   */
  @Test
  public void constructorTestValidConstructor() {
    assertNotNull(department, "Expected to identify a non-null department.");
  }

  /**
   * Tests if the Department constructor throws an IllegalArgumentException for an empty department
   * code.
   */
  @Test
  public void constructorTestEmptyStringDeptCode() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department("", new HashMap<>(), "Luca Carloni", 2345),
        "Expected IllegalArgumentException for empty department code.");
  }

  /**
   * Tests if the Department constructor throws an IllegalArgumentException for a null department
   * code.
   */
  @Test
  public void constructorTestNullDeptCode() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department(null, new HashMap<>(), "Luca Carloni", 2345),
        "Expected IllegalArgumentException for null department code.");
  }

  /**
   * Tests if the Department constructor throws an IllegalArgumentException for a negative number of
   * majors.
   */
  @Test
  public void constructorTestNegativeNumberOfMajors() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Department("COMS", new HashMap<>(), "Luca Carloni", -3),
        "Expected IllegalArgumentException for negative number of majors.");
  }

  /** Tests if getNumberOfMajors() returns the correct number of majors. */
  @Test
  public void getNumberOfMajorsInputValidation() {
    assertEquals(2700, department.getNumberOfMajors(), "Expected number of majors to be 2700.");
  }

  /** Tests if getDepartmentChair() returns the correct department chair name. */
  @Test
  public void getDepartmentChairInputValidation() {
    assertEquals(
        "Luca Carloni",
        department.getDepartmentChair(),
        "Expected department chair to be 'Luca Carloni'.");
  }

  /** Tests if getDepartmentCode() returns the correct department code. */
  @Test
  public void getDepartmentCodeInputValidation() {
    assertEquals("COMS", department.getDepartmentCode(), "Expected department code to be 'COMS'.");
  }

  /** Tests if getCourseSelection() returns the correct map of courses. */
  @Test
  public void getCourseSelectionInputValidation() {
    assertEquals(
        courses,
        department.getCourseSelection(),
        "Expected course selection to match the initialized map.");
  }

  /** Tests if addPersonToMajor() correctly increments the number of majors. */
  @Test
  public void addPersonToMajorIncreasesNumberOfMajors() {
    department.addPersonToMajor();
    assertEquals(
        2701,
        department.getNumberOfMajors(),
        "Expected the number of majors to be incremented by 1.");
  }

  /** Tests if dropPersonFromMajor() correctly decrements the number of majors. */
  @Test
  public void dropPersonFromMajorDecreaseNumberOfMajors() {
    department.dropPersonFromMajor();
    assertEquals(
        2699,
        department.getNumberOfMajors(),
        "Expected the number of majors to be decremented by 1.");
  }

  /** Tests if dropPersonFromMajor() throws an IllegalArgumentException when there are no majors. */
  @Test
  public void dropPersonFromMajorWhenNoMajorsThrowsException() {
    emptyDepartment = new Department("COMS", new HashMap<>(), "Luca Carloni", 0);

    assertThrows(
        IllegalArgumentException.class,
        () -> emptyDepartment.dropPersonFromMajor(),
        "Expected IllegalArgumentException when attempting to drop a person from a department "
            + "with 0 majors.");
  }

  /** Tests if addCourse() correctly adds a course with a valid course ID. */
  @Test
  public void addCourseValidCourseIdAddsCourse() {
    assertEquals(
        course1,
        department.getCourseSelection().get("1004"),
        "Expected to retrieve course associated with courseId '1004'.");
  }

  /** Tests if addCourse() throws an IllegalArgumentException for a null course ID. */
  @Test
  public void addCourseInvalidNullCourseIdThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse(null, course1),
        "Expected IllegalArgumentException for null course ID.");
  }

  /** Tests if addCourse() throws an IllegalArgumentException for an empty string course ID. */
  @Test
  public void addCourseInvalidEmptyStringCourseIdThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse("", course1),
        "Expected IllegalArgumentException for empty string as course ID.");
  }

  /** Tests if addCourse() throws an IllegalArgumentException for a null course. */
  @Test
  public void addCourseNullCourseThrowsException() {
    assertThrows(
        IllegalArgumentException.class,
        () -> department.addCourse("3445", null),
        "Expected IllegalArgumentException for null course.");
  }

  /** Tests if createCourse() creates and adds a course correctly. */
  @Test
  public void createCourseCreatesAndAddsCourse() {
    // values to test
    String courseId = "3134";
    String instructorName = "Brian Borowski";
    String courseLocation = "301 URIS";
    String courseTimeSlot = "14:00-15:00";
    int capacity = 250;

    // create course to test against
    department.createCourse(courseId, instructorName, courseLocation, courseTimeSlot, capacity);
    Course newCourse = department.getCourseSelection().get(courseId);

    // ensure proper input validation
    assertEquals(
        3, department.getCourseSelection().size(), "Expected course selection size to be 3.");
    assertNotNull(
        department.getCourseSelection().get(courseId),
        "Expected new course to be present in course selection.");
    assertNotNull(newCourse, "Expected new course to be non-null.");
    assertEquals(
        instructorName,
        newCourse.getInstructorName(),
        "Expected instructor name to be 'Brian Borowski'.");
    assertEquals(
        courseLocation,
        newCourse.getCourseLocation(),
        "Expected course location to be '301 URIS'.");
    assertEquals(
        courseTimeSlot,
        newCourse.getCourseTimeSlot(),
        "Expected course time slot to be '14:00-15:00'.");
    assertEquals(
        capacity, newCourse.getEnrollmentCapacity(), "Expected course capacity to be 250.");
  }

  /**
   * Tests if toString() returns the correct string representation of the department and its
   * courses.
   */
  @Test
  public void toStringReturnsDepartmentRepresentation() {
    String course1ExpectedResult = "COMS 1004: " + course1.toString() + "\n";
    String course2ExpectedResult = "COMS 3251: " + course2.toString() + "\n";
    String expectedResult = course1ExpectedResult + course2ExpectedResult;
    assertEquals(
        expectedResult,
        department.toString(),
        "Expected to return the department representation including all courses with their "
            + "details.");
  }
}
