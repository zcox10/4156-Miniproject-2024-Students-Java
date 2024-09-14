package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MyFileDatabase} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link MyFileDatabase}
 * class methods. Extends from InitializeDatabase that has all test courses, departments, and db
 * init.
 */
public class MyFileDatabaseTests extends InitializeDatabase {

  /** Tests if the MyFileDatabase constructor initializes departmentMapping as an empty HashMap. */
  @Test
  public void constructorCorrectValidation() {
    assertTrue(
        db.getDepartmentMapping().isEmpty(),
        "Expected departmentMapping to be initialized as an empty HashMap.");
  }

  /**
   * Tests if the MyFileDatabase constructor throws an IllegalArgumentException when given an
   * invalid flag.
   */
  @Test
  public void constructorWithInvalidFlag() {
    IllegalArgumentException thrown =
        assertThrows(IllegalArgumentException.class, () -> new MyFileDatabase(2, FILE_PATH));
    assertEquals(
        "Invalid flag value: 2",
        thrown.getMessage(),
        "Expected exception message for invalid flag value.");
  }

  /** Tests if the setMapping() method sets the department mapping correctly. */
  @Test
  public void setMappingExpectedValueValidation() {
    db.setMapping(testDepartmentMapping);
    assertEquals(
        testDepartmentMapping,
        db.getDepartmentMapping(),
        "Expected departmentMapping to match testDepartmentMapping.");
  }

  /**
   * Tests if the saveContentsToFile() method correctly writes and serializes the department mapping
   * to a file.
   */
  @Test
  public void saveContentsToFileValidation() throws IOException, ClassNotFoundException {
    db.setMapping(testDepartmentMapping);
    db.saveContentsToFile();

    // basically deSerializeObjectFromFile()
    ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
    @SuppressWarnings("unchecked")
    HashMap<String, Department> deserializedMapping = (HashMap<String, Department>) in.readObject();

    // ensure output from file matches the provided output in testDepartmentMapping
    assertEquals(
        testDepartmentMapping.toString(),
        deserializedMapping.toString(),
        "Expected testDepartmentMapping and deserializedMapping to have the same string output.");
  }

  /** Tests if the saveContentsToFile() method creates the file at the specified path. */
  @Test
  public void saveContentsToFileEnsureFileExists() {
    // set mapping for departments, then save to FILE_PATH
    db.setMapping(testDepartmentMapping);
    db.saveContentsToFile();

    // Ensure file exists
    Path filePath = Paths.get(FILE_PATH);
    assertTrue(Files.exists(filePath), "Expected file at " + filePath + " to exist.");
  }

  /**
   * Tests if the toString() method of MyFileDatabase produces the expected formatted string output.
   */
  @Test
  public void toStringExpectedValueValidation() {
    db.setMapping(testDepartmentMapping);

    // Verify that the toString method produces the correct output
    String expected =
        """
            For the COMS department:\s
            COMS 1004:\s
            Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55
            COMS 3251:\s
            Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40
            For the ECON department:\s
            ECON 1105:\s
            Instructor: Waseem Noor; Location: 309 HAV; Time: 2:40-3:55
            ECON 2257:\s
            Instructor: Tamrat Gashaw; Location: 428 PUP; Time: 10:10-11:25
            """;
    assertEquals(
        expected, db.toString(), "Expected toString() output to match the formatted string.");
  }
}
