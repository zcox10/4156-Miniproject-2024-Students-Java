package dev.coms4156.project.individualproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;

/**
 * Used to instantiate a database for other tests such as MyFileDatabaseTests and
 * IndividualProjectApplicationTests.
 */
public abstract class InitializeDatabase {
  protected static final String FILE_PATH = "src/test/resources/testData.txt";
  protected HashMap<String, Department> testDepartmentMapping;
  protected MyFileDatabase db;

  /**
   * Set up the myFileDatabase unit tests by initializing a test ECON and COMS department with 2
   * test courses per each department.
   */
  @BeforeEach
  public void initializeDatabaseSetUp() throws IOException {
    // delete FILE_PATH (data.txt) if it exists, for fresh start
    Files.deleteIfExists(Paths.get(FILE_PATH));

    // init test department mapping
    testDepartmentMapping = new HashMap<>();

    // CS courses
    Course csCourse1 = new Course("Adam Cannon", "417 IAB", "11:40-12:55", 30);
    Course csCourse2 = new Course("Tony Dear", "402 CHANDLER", "1:10-3:40", 125);
    HashMap<String, Course> csCourses = new HashMap<>();
    csCourses.put("1004", csCourse1);
    csCourses.put("3251", csCourse2);
    Department csDepartment = new Department("COMS", csCourses, "Luca Carloni", 2700);

    // ECON courses
    Course econCourse1 = new Course("Waseem Noor", "309 HAV", "2:40-3:55", 210);
    Course econCourse2 = new Course("Tamrat Gashaw", "428 PUP", "10:10-11:25", 125);
    HashMap<String, Course> econCourses = new HashMap<>();
    econCourses.put("1105", econCourse1);
    econCourses.put("2257", econCourse2);
    Department econDepartment = new Department("ECON", econCourses, "Michael Woodford", 2345);

    testDepartmentMapping.put("COMS", csDepartment);
    testDepartmentMapping.put("ECON", econDepartment);
    db = new MyFileDatabase(1, FILE_PATH);
  }
}
