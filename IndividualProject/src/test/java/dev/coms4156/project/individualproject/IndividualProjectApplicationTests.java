package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link IndividualProjectApplication} class.
 *
 * <p>This class contains test cases to validate the functionality of the {@link
 * IndividualProjectApplication} class methods.
 */
public class IndividualProjectApplicationTests extends InitializeDatabase {

  private IndividualProjectApplication ipa;
  private MyFileDatabase mockDb;

  /**
   * Sets up the test environment by initializing an instance of IndividualProjectApplication and a
   * mock MyFileDatabase.
   */
  @BeforeEach
  void individualProjectApplicationSetUp() {
    ipa = new IndividualProjectApplication();
    mockDb = mock(MyFileDatabase.class);
  }

  /** Tests if the overrideDatabase() method correctly sets the static myFileDatabase field. */
  @Test
  void overrideDatabaseValidation() {
    IndividualProjectApplication.overrideDatabase(mockDb);
    assertEquals(
        mockDb,
        IndividualProjectApplication.myFileDatabase,
        "Expected myFileDatabase to be set by overrideDatabase()");
  }

  /**
   * Tests if the run() method with the "setup" argument initializes the myFileDatabase field and
   * verifies that saveContentsToFile() is not called.
   */
  @Test
  void runMethodValidationWithSetupArgument() {
    String[] args = {"setup"};
    ipa.run(args);

    assertNotNull(
        IndividualProjectApplication.myFileDatabase, "Expected myFileDatabase to be initialized");

    // Ensure saveContentsToFile is not called
    verify(mockDb, times(0)).saveContentsToFile();
  }

  /**
   * Tests if the run() method without any arguments initializes the myFileDatabase field and
   * verifies that saveContentsToFile() is not called.
   */
  @Test
  void runMethodValidationWithoutSetupArgument() {
    String[] args = {};
    ipa.run(args);

    assertNotNull(
        IndividualProjectApplication.myFileDatabase, "Expected myFileDatabase to be initialized");

    // Ensure saveContentsToFile is not called
    verify(mockDb, times(0)).saveContentsToFile();
  }

  /**
   * Tests if the resetDataFile() method correctly sets the department mapping using the
   * setMapping() method on the mock database.
   */
  @Test
  void resetDataFileHasProperDepartmentMapping() {
    IndividualProjectApplication.myFileDatabase = mockDb;
    IndividualProjectApplication.myFileDatabase.setMapping(testDepartmentMapping);
    ipa.resetDataFile();

    // Verify setMapping is called with correct data
    verify(mockDb, times(1)).setMapping(testDepartmentMapping);
  }

  /** Tests if the onTermination() method calls saveContentsToFile() when saveData is true. */
  @Test
  void onTerminationSaveDataTrue() {
    IndividualProjectApplication.myFileDatabase = mockDb;
    ipa.onTermination();

    // Verify saveContentsToFile is called
    verify(mockDb, times(1)).saveContentsToFile();
  }

  /**
   * Tests if the onTermination() method does not call saveContentsToFile() when saveData is false.
   */
  @Test
  void onTerminationSaveDataFalse() {
    // overrideDatabase sets saveData to false
    IndividualProjectApplication.overrideDatabase(mockDb);
    ipa.onTermination();

    // Verify saveContentsToFile is not called
    verify(mockDb, times(0)).saveContentsToFile();
  }
}
