package dev.coms4156.project.individualproject;

import java.util.HashMap;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This class contains all the API routes for the system. */
@RestController
public class RouteController {
  private static final String DEPT_CODE = "deptCode";
  private static final String COURSE_CODE = "courseCode";
  private static final String DEPARTMENT_NOT_FOUND = "Department Not Found";
  private static final String COURSE_NOT_FOUND = "Course Not Found";
  private static final String ATTRIBUTE_UPDATED_SUCCESSFULLY =
      "Attribute was updated successfully.";

  /**
   * Helper function to determine if a department exists.
   *
   * @param deptCode A {@code String} representing the department the user wishes to retrieve.
   * @return A {@code boolean} determining if department exists or not.
   */
  protected boolean doesDepartmentExist(String deptCode) {
    return retrieveDepartment(deptCode).getStatusCode() == HttpStatus.OK;
  }

  /**
   * Helper function to determine if a course exists.
   *
   * @param deptCode A {@code String} representing the department the user wishes to retrieve.
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code boolean} determining if a course exists or not.
   */
  protected boolean doesCourseExist(String deptCode, int courseCode) {
    return retrieveCourse(deptCode, courseCode).getStatusCode() == HttpStatus.OK;
  }

  /**
   * Helper function to retrieve a department's mapping.
   *
   * @return A hashmap containing all departments and their designated properties.
   */
  protected HashMap<String, Department> retrieveDeptMapping() {
    return IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
  }

  /**
   * Redirects to the homepage.
   *
   * @return A String containing the name of the html file to be loaded.
   */
  @GetMapping({"/", "/index", "/home"})
  public String index() {
    return """
        Welcome, in order to make an API call direct your browser or Postman to an endpoint \


        This can be done using the following format:\s

        http:127.0.0.1:8080/endpoint?arg=value""";
  }

  /**
   * Returns the details of the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the Department and an
   *     HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveDepartment(@RequestParam(value = DEPT_CODE) String deptCode) {
    try {
      if (!retrieveDeptMapping().containsKey(deptCode.toUpperCase(Locale.ENGLISH))) {
        return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(
            retrieveDeptMapping().get(deptCode.toUpperCase(Locale.ENGLISH)).toString(),
            HttpStatus.OK);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the details of the requested course to the user or displays the proper error message
   * in response to the request.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the course
   *     in.
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the details of the course and an HTTP
   *     200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/retrieveCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> retrieveCourse(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesDepartmentExist(deptCode)) {
        HashMap<String, Course> coursesMapping =
            retrieveDeptMapping().get(deptCode).getCourseSelection();

        if (!coursesMapping.containsKey(Integer.toString(courseCode))) {
          return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } else {
          return new ResponseEntity<>(
              coursesMapping.get(Integer.toString(courseCode)).toString(), HttpStatus.OK);
        }
      } else {
        return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays whether the course has at minimum reached its enrollmentCapacity.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the course
   *     in.
   * @param courseCode A {@code int} representing the course the user wishes to retrieve.
   * @return A {@code ResponseEntity} object containing either the requested information and an HTTP
   *     200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/isCourseFull", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isCourseFull(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // determine isCourseFull() when provided a deptCode and courseCode
        return new ResponseEntity<>(
            retrieveDeptMapping()
                .get(deptCode)
                .getCourseSelection()
                .get(Integer.toString(courseCode))
                .isCourseFull(),
            HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the number of majors in the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find number of
   *     majors for.
   * @return A {@code ResponseEntity} object containing either number of majors for the specified
   *     department and an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/getMajorCountFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getMajorCtFromDept(@RequestParam(value = DEPT_CODE) String deptCode) {
    try {
      if (doesDepartmentExist(deptCode)) {
        int numberOfMajors = retrieveDeptMapping().get(deptCode).getNumberOfMajors();
        return new ResponseEntity<>(
            "There are: " + numberOfMajors + " majors in the department", HttpStatus.OK);
      }
      return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the department chair for the specified department.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the
   *     department chair of.
   * @return A {@code ResponseEntity} object containing either department chair of the specified
   *     department and an HTTP 200 response or, an appropriate message indicating the proper
   *     response.
   */
  @GetMapping(value = "/idDeptChair", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> identifyDeptChair(@RequestParam(value = DEPT_CODE) String deptCode) {
    try {
      if (doesDepartmentExist(deptCode)) {
        String departmentChair = retrieveDeptMapping().get(deptCode).getDepartmentChair();
        return new ResponseEntity<>(departmentChair + " is the department chair.", HttpStatus.OK);
      }
      return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the location for the specified course.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the course
   *     in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *     about.
   * @return A {@code ResponseEntity} object containing either the location of the course and an
   *     HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseLocation(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // retrieves the courseLocation via a specified deptCode and courseCode
        String courseLocation =
            retrieveDeptMapping()
                .get(deptCode)
                .getCourseSelection()
                .get(Integer.toString(courseCode))
                .getCourseLocation();
        return new ResponseEntity<>(
            courseLocation + " is where the course " + "is located.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the instructor for the specified course.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the course
   *     in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *     about.
   * @return A {@code ResponseEntity} object containing either the course instructor and an HTTP 200
   *     response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseInstructor", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseInstructor(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // retrieve instructorName via a specified deptCode and courseCode
        String instructorName =
            retrieveDeptMapping()
                .get(deptCode)
                .getCourseSelection()
                .get(Integer.toString(courseCode))
                .getInstructorName();
        return new ResponseEntity<>(
            instructorName + " is the instructor for the course.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Displays the time the course meets at for the specified course.
   *
   * @param deptCode A {@code String} representing the department the user wishes to find the course
   *     in.
   * @param courseCode A {@code int} representing the course the user wishes to find information
   *     about.
   * @return A {@code ResponseEntity} object containing either the details of the course timeslot
   *     and an HTTP 200 response or, an appropriate message indicating the proper response.
   */
  @GetMapping(value = "/findCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findCourseTime(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // retrieve courseTimeSlot via a specified deptCode and courseCode
        String courseTimeSlot =
            retrieveDeptMapping()
                .get(deptCode)
                .getCourseSelection()
                .get(Integer.toString(courseCode))
                .getCourseTimeSlot();
        return new ResponseEntity<>("The course meets at: " + courseTimeSlot + " ", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to add a student to the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/addMajorToDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addMajorToDept(@RequestParam(value = DEPT_CODE) String deptCode) {
    try {
      if (doesDepartmentExist(deptCode)) {
        // add person to specified deptCode major
        retrieveDeptMapping().get(deptCode).addPersonToMajor();
        return new ResponseEntity<>(ATTRIBUTE_UPDATED_SUCCESSFULLY, HttpStatus.OK);
      }
      return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to remove a student from the specified department.
   *
   * @param deptCode A {@code String} representing the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/removeMajorFromDept", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> removeMajorFromDept(@RequestParam(value = DEPT_CODE) String deptCode) {
    try {
      if (doesDepartmentExist(deptCode)) {
        // drop person to specified deptCode major
        retrieveDeptMapping().get(deptCode).dropPersonFromMajor();
        return new ResponseEntity<>("Attribute was updated or is at minimum", HttpStatus.OK);
      }
      return new ResponseEntity<>(DEPARTMENT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Attempts to drop a student from the specified course.
   *
   * @param deptCode A {@code String} representing the department.
   * @param courseCode A {@code int} representing the course within the department.
   * @return A {@code ResponseEntity} object containing an HTTP 200 response with an appropriate
   *     message or the proper status code in tune with what has happened.
   */
  @PatchMapping(value = "/dropStudentFromCourse", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> dropStudent(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        boolean isStudentDropped =
            retrieveDeptMapping()
                .get(deptCode)
                .getCourseSelection()
                .get(Integer.toString(courseCode))
                .dropStudent();

        if (isStudentDropped) {
          return new ResponseEntity<>("Student has been dropped.", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Student has not been dropped.", HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates the enrollment count for a specified course within a given department.
   *
   * <p>This method handles a PATCH request to update the enrollment count of a course. If the
   * course exists, the enrollment count is updated; otherwise, an error response is returned.
   *
   * @param deptCode Department code to which the course belongs.
   * @param courseCode Code of the course whose enrollment count is to be updated.
   * @param count New enrollment count to be set for the specified course.
   * @return A {@link ResponseEntity} with a success message and 200 HTTP status if the course
   *     exists and the enrollment count is updated, or an error message with 404 HTTP status if the
   *     course does not exist.
   */
  @PatchMapping(value = "/setEnrollmentCount", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> setEnrollmentCount(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode,
      @RequestParam(value = "count") int count) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // setEnrolledStudentCount via specified deptCode and courseCode
        retrieveDeptMapping()
            .get(deptCode)
            .getCourseSelection()
            .get(Integer.toString(courseCode))
            .setEnrolledStudentCount(count);

        return new ResponseEntity<>(ATTRIBUTE_UPDATED_SUCCESSFULLY, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Endpoint for changing the time of a course. This method handles PATCH requests to change the
   * time of a course identified by department code and course code.If the course exists, its time
   * is updated to the provided time.
   *
   * @param deptCode the code of the department containing the course
   * @param courseCode the code of the course to change the time for
   * @param time the new time for the course
   * @return a ResponseEntity with a success message if the operation is successful, or an error
   *     message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTime", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTime(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode,
      @RequestParam(value = "time") String time) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // reassignTime() based on designated courseCode, deptCode, and time
        retrieveDeptMapping()
            .get(deptCode)
            .getCourseSelection()
            .get(Integer.toString(courseCode))
            .reassignTime(time);

        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * This method handles PATCH requests to change the instructor of a course identified by
   * department code and course code. If the course exists, its instructor is updated to the
   * provided instructor.
   *
   * @param deptCode Code of the department containing the course
   * @param courseCode Code of the course to change the instructor for
   * @param teacher New instructor for the course
   * @return a ResponseEntity with a success message if the operation is successful; or an error
   *     message if the course is not found
   */
  @PatchMapping(value = "/changeCourseTeacher", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseTeacher(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode,
      @RequestParam(value = "teacher") String teacher) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // reassignInstructor based on specified deptCode, courseCode, and new instructor
        retrieveDeptMapping()
            .get(deptCode)
            .getCourseSelection()
            .get(Integer.toString(courseCode))
            .reassignInstructor(teacher);

        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Changes the location of a specified course within a given department.
   *
   * <p>This method handles a PATCH request to update the location of a course. If the course
   * exists, the location is updated to the new value; otherwise, an error response is returned.
   *
   * @param deptCode Department code to which the course belongs.
   * @param courseCode Code of the course whose location is to be updated.
   * @param location New location to be assigned to the specified course.
   * @return A {@link ResponseEntity} with a success message and 200 HTTP status if the course
   *     exists and the location is updated; or an error message with 404 HTTP status if the course
   *     does not exist.
   */
  @PatchMapping(value = "/changeCourseLocation", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> changeCourseLocation(
      @RequestParam(value = DEPT_CODE) String deptCode,
      @RequestParam(value = COURSE_CODE) int courseCode,
      @RequestParam(value = "location") String location) {
    try {
      if (doesCourseExist(deptCode, courseCode)) {
        // reassignLocation based on deptCode, courseCode, and new location
        retrieveDeptMapping()
            .get(deptCode)
            .getCourseSelection()
            .get(Integer.toString(courseCode))
            .reassignLocation(location);
        return new ResponseEntity<>("Attributed was updated successfully.", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Handles exceptions that occur during the execution of requests. Logs the exception details to
   * the console and returns a generic error response 200 status. This method is used to provide a
   * generic error response for exceptions encountered in IndividualProject.
   *
   * @param e The exception that was thrown and needs to be handled.
   * @return A {@link ResponseEntity} containing a generic error message HTTP 200 status.
   */
  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
  }
}
