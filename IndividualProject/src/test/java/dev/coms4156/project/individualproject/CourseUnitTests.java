package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Course} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link Course} class
 * methods.
 */
public class CourseUnitTests {

  /** The test course instance used for testing. */
  private Course testCourse;

  /** Sets up the test environment by initializing a Course instance. */
  @BeforeEach
  public void courseSetUp() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  /** Tests if the Course constructor correctly creates a Course instance with valid parameters. */
  @Test
  public void constructorTestValidConstructor() {
    Course course = new Course("Gail Kaiser", "417 IAB", "11:40-12:55", 30);
    assertNotNull(course, "Expected Course instance to be created.");
  }

  /**
   * Tests if the Course constructor throws an IllegalArgumentException when the instructor name is
   * null.
   */
  @Test
  public void constructorTestInvalidInstructorName() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Course(null, "417 IAB", "11:40-12:55", 30),
        "Expected IllegalArgumentException for null instructor name.");
  }

  /**
   * Tests if the Course constructor throws an IllegalArgumentException when the course location is
   * empty.
   */
  @Test
  public void constructorTestInvalidCourseLocation() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Course("Gail Kaiser", "", "11:40-12:55", 30),
        "Expected IllegalArgumentException for empty course location.");
  }

  /**
   * Tests if the Course constructor throws an IllegalArgumentException when the time slot is null.
   */
  @Test
  public void constructorTestInvalidTimeSlot() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Course("Gail Kaiser", "11:40-12:55", null, 30),
        "Expected IllegalArgumentException for null time slot.");
  }

  /**
   * Tests if the Course constructor throws an IllegalArgumentException when the capacity is
   * negative.
   */
  @Test
  public void constructorTestInvalidCapacity() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Course("Gail Kaiser", "417 IAB", "11:40-12:55", -1),
        "Expected IllegalArgumentException for negative capacity.");
  }

  /** Tests if the toString() method returns the correct string output of the course parameters. */
  @Test
  public void toStringReturnsCourseParamsAsString() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(
        expectedResult,
        testCourse.toString(),
        "Expected toString() output to match course parameters.");
  }

  /** Tests if enrollStudent() correctly enrolls a student when the course is not full. */
  @Test
  public void enrollStudentWhenCourseIsNotFull() {
    testCourse.setEnrolledStudentCount(0);
    assertTrue(
        testCourse.enrollStudent(),
        "Expected enrollStudent() to return true when the course is not full.");
    assertEquals(
        1, testCourse.getEnrolledStudentCount(), "Expected enrolled student count to be updated.");
  }

  /** Tests if enrollStudent() returns false when the course is full . */
  @Test
  public void enrollStudentWhenCourseIsFull() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(
        testCourse.enrollStudent(),
        "Expected enrollStudent() to return false when the course is full.");
  }

  /** Tests if setEnrolledStudentCount() correctly updates the enrolled student count. */
  @Test
  public void setEnrollStudentCountUpdateEnrolledStudentCount() {
    testCourse.setEnrolledStudentCount(200);
    assertEquals(
        200,
        testCourse.getEnrolledStudentCount(),
        "Expected enrolled student count to be updated.");
  }

  /** Tests if setEnrolledStudentCount() throws an IllegalArgumentException for negative values. */
  @Test
  public void setEnrollStudentCountNonNegative() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.setEnrolledStudentCount(-1),
        "Expected IllegalArgumentException for negative enrolled student count.");
  }

  /** Tests if dropStudent() correctly drops a student when students are enrolled. */
  @Test
  public void dropStudentWhenStudentsAreEnrolled() {
    testCourse.setEnrolledStudentCount(1);
    assertTrue(
        testCourse.dropStudent(),
        "Expected dropStudent() to return true when students are enrolled.");
    assertEquals(
        0, testCourse.getEnrolledStudentCount(), "Expected enrolled student count to be updated.");
  }

  /** Tests if dropStudent() returns false when no students are enrolled. */
  @Test
  public void dropStudentWhenStudentsAreNotEnrolled() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(
        testCourse.dropStudent(),
        "Expected dropStudent() to return false when no students are enrolled.");
  }

  /** Tests if reassignInstructor() correctly updates the instructor's name. */
  @Test
  public void reassignInstructorUpdateInstructorName() {
    testCourse.reassignInstructor("Gail Kaiser");
    assertEquals(
        "Gail Kaiser", testCourse.getInstructorName(), "Expected instructor name to be updated.");
  }

  /** Tests if reassignInstructor() throws an IllegalArgumentException for empty string input. */
  @Test
  public void reassignInstructorExceptionForEmptyStringInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignInstructor(""),
        "Expected IllegalArgumentException due to empty string value for instructor name.");
  }

  /** Tests if reassignInstructor() throws an IllegalArgumentException for null input. */
  @Test
  public void reassignInstructorExceptionForNullInput() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignInstructor(null),
        "Expected IllegalArgumentException due to null value for instructor name.");
  }

  /** Tests if reassignLocation() throws an IllegalArgumentException for empty string value. */
  @Test
  public void reassignLocationExceptionForEmptyStringValue() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignLocation(""),
        "Expected IllegalArgumentException due to empty string value for location.");
  }

  /** Tests if reassignLocation() throws an IllegalArgumentException for null value. */
  @Test
  public void reassignLocationExceptionForNullValue() {
    assertThrows(
        IllegalArgumentException.class,
        () -> testCourse.reassignLocation(null),
        "Expected IllegalArgumentException due to null value for location.");
  }

  /** Tests if reassignTime() correctly updates the time slot with a valid format. */
  @Test
  public void reassignTimeValidTimeFormat() {
    testCourse.reassignTime("2:40-4:55");
    assertEquals(
        "2:40-4:55", testCourse.getCourseTimeSlot(), "Expected course time slot to be updated.");
  }

  /** Tests if reassignTime() throws an IllegalArgumentException for an invalid time format. */
  @Test
  public void reassignTimeInvalidTimeFormat() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> testCourse.reassignTime("invalid-time"));
    String expectedMessage =
        "Invalid time format.  "
            + "Expected format: 'H:MM-H:MM', 'H:MM-HH:MM', 'HH:MM-H:MM', or 'HH:MM-HH:MM'.  "
            + "Also ensure valid hours (00-23) and minutes (00-59).";
    assertEquals(
        expectedMessage,
        exception.getMessage(),
        "Expected specific message for invalid time format.");
  }

  /** Tests if isValidTimeSlot() correctly identifies valid time slots. */
  @Test
  public void isValidTimeSlotTestValidTimeSlot() {
    assertTrue(
        Course.isValidTimeSlot("9:00-17:30"), "Expected '9:00-17:30' to be a valid time slot.");
    assertTrue(
        Course.isValidTimeSlot("09:00-17:30"), "Expected '09:00-17:30' to be a valid time slot.");
    assertTrue(
        Course.isValidTimeSlot("9:00-9:30"), "Expected '9:00-9:30' to be a valid time slot.");
    assertTrue(
        Course.isValidTimeSlot("09:00-09:30"), "Expected '09:00-09:30' to be a valid time slot.");
  }

  /** Tests if isValidTimeSlot() correctly identifies invalid time slots. */
  @Test
  public void isValidTimeSlotTestInvalidTimeSlot() {
    assertFalse(
        Course.isValidTimeSlot("9:00-25:00"),
        "Expected '9:00-25:00' to be an invalid time slot (hour out of range).");
    assertFalse(
        Course.isValidTimeSlot("09:00-17:60"),
        "Expected '09:00-17:60' to be an invalid time slot (minute out of range).");
    assertFalse(
        Course.isValidTimeSlot("9:00-17:30-20:00"),
        "Expected '9:00-17:30-20:00' to be an invalid time slot (incorrect format).");
    assertFalse(Course.isValidTimeSlot(""), "Expected empty string to be an invalid time slot.");
    assertFalse(Course.isValidTimeSlot(null), "Expected null input to be an invalid time slot.");
  }
}
