# Bugs

Almost all of my bugs were caught BEFORE I ran PMD as I was starting to write tests. The format does
not look clear compared to PMD output, but hopefully this is not too difficult to read through.

### Bugs/Updates in `Course`

- **Method Name**: `enrollStudent()`
    - **Type**: Bug
    - **Description**: `enrolledStudent()` only adds a student to a course, it does not check if a
      course is full or not. Additionally, it always returns false; it should return true if a
      student is successfully enrolled.
    - **Fix**: Add an "if" statement to `enrollStudent()` to check if a course is full. If it is not
      full, increment the `enrolledStudentCount` by 1; otherwise return `false` indicating the class
      is full.
- **Method Name**: `dropStudent()`
    - **Type**: Bug
    - **Description**: `dropStudent()` only decrements the `enrolledStudentCount`, it does not check
      to see if there are any students in the class. If `enrolledStudentCount == 0`, then the
      `enrolledStudentCount` would be updated to `-1`. Additionally, this method always returns
      `false`. It should return `true` if we successfully update the `enrolledStudentCount`.
    - **Fix**: Add an "if" statement to `dropStudent()` to check if a course has any students. If it
      has at least 1 student, decrement the `enrolledStudentCount` by 1; otherwise return `false`
      indicating the class has no students.
- **Method Name**: `getCourseTimeSlot()`, `getEnrollmentCapacity()`, and
  `getEnrolledStudentCount()`
    - **Type**: Additions
    - **Description**: Added the `getCourseTimeSlot()`, `getEnrollmentCapacity()`, and
      `getEnrolledStudentCount()` methods for ease of testing.
- **Method Name**: `reassignInstructor()`
    - **Type**: Bug
    - **Description**: `reassignInstructor()` does not check if the input is an empty string or
      null.
    - **Fix**: Throw an exception if the `newInstructorName` param is null or an empty string.
- **Method Name**: `reassignLocation()`
    - **Type**: Bug
    - **Description**: `reassignLocation()` Does not check if the input is an empty string or
      null.
    - **Fix**: Throw an exception if the `newLocation` param is null or an empty string.
- **Method Name**: `reassignTime()`
    - **Type**: Bug
    - **Description**: `reassignTime()` does not perform any input validation for the course time.
    - **Fix**: Validating the input with regex to ensure the course time matches `HH:MM-HH:MM`,
      `H:MM-HH:MM`, or `HH:MM-H:MM`.
- **Method Name**: `setEnrolledStudentCount()`
    - **Type**: Bug
    - **Description**: The method does not have a validation check to ensure the enrollment `count`
      is > 0.
    - **Fix**: Ensuring the input validation and that `count > 0`.
- **Method Name**: `isCourseFull()`
    - **Type**: Bug
    - **Description**: The method checks to see if the `enrollmentCapacity > enrolledStudentCount`,
      which would indicate that the course is not yet full.
    - **Fix**: Ensuring the proper conditional check to satisfy whether a course is full or not.

### Bugs/Updates in `Department`

- **Method Name**: `getNumberOfMajors()`
    - **Type**: Bug
    - **Description**: The method returns `-this.numberOfMajors` (negative value), and not the
      number of majors stored in `this.numberOfMajors`.
    - **Fix**: Change `-this.numberOfMajors` to `this.numberOfMajors`.
- **Method Name**: `getDepartmentChair()`
    - **Type**: Bug
    - **Description**: The method returns the string `"this.departmentChair"` and not the actual
      value `this.departmentChair`.
    - **Fix**: Remove double quotes of `"this.departmentChair"` and return the value stored in
      `this.departmentChair`.
- **Method Name**: `dropPersonFromMajor()`
    - **Type**: Bug
    - **Description**: There is no input validation if the number of majors is <= 0 in a department.
    - **Fix**: Throw an `IllegalArgumentException` is the number of majors is <= 0.
- **Method Name**: `addCourse()`
    - **Type**: Bug
    - **Description**: There is no input validation to check if the `courseId` is non-null and not
      an empty string. Additionally, no check to ensure that `course` is not null.
    - **Fix**: Throw an `IllegalArgumentException` if the `course` is null or `courseId` is
      null/empty string.
- **Method Name**: `Department.toString()`.
    - **Type**: Bug
    - **Description**: The method returns the string `"result.toString()"` and not the actual
      value `result.toString()`. Additionally, there is no check to ensure that courses is non-null.
    - **Fix**: Remove double quotes of `"result.toString()"` and return the value stored in
      `result.toString()`. Add a check to ensure courses is non-null.

### Bugs/Updates in `MyFileDatabase`

- **Variable Name**: `filePath`.
    - **Type**: Bug
    - **Description**: `filePath` variable should be final.
    - **Fix**: `private final String filePath;`
- **Method Name**: `MyFileDatabase constructor`
    - **Type**: Bug
    - **Description**: We only perform an action when flag is set to `0`.
    - **Fix**: When `flag==1`, initialize `this.departmentMapping` to an empty `HashMap` by default
      in the constructor; else throw `IllegalArgumentException`.
- **Method Name**: `deSerializeObjectFromFile()`.
    - **Type**: Bug
    - **Description**: if we cannot get a valid object input stream, we return `null` which could
      lead to `NullPointerException`.
    - **Fix**: return an empty `HashMap` instead.
- **Method Name**: `myFileDatabase.toString()`.
    - **Type**: Bug
    - **Description**: there is no check to see if `departmentMapping` is null or not.
    - **Fix**: add an if-statement to see if `departmentMapping` is null. If it is null, print
      `"No department data available."`; else print departments.

### Bugs/Updates in `RouteController`

- **Method Name**: `retrieveDepartment()`.
    - **Type**: Bug
    - **Description**: if the department code is not found, returns `HttpStatus.OK`, and if it is
      found, returns `HttpStatus.NOT_FOUND`.
    - **Fix**: These `HttpStatus` responses should be swapped. If the department code is not found,
      return `HttpStatus.NOT_FOUND`. If it is found, output `HttpStatus.OK`
- **Method Name**: `retrieveCourse()`.
    - **Type**: Bug
    - **Description**: if the courseCode is found, we return a status of `HttpStatus.FORBIDDEN`.
    - **Fix**: Ensure a successful lookup of the courseCode and its content returns an
      `HttpStatus.OK`.
- **Method Name**: `doesDepartmentExist()`.
    - **Type**: Addition
    - **Description**: Created a helper method, `doesDepartmentExist()` to utilize throughout
      various methods.
- **Method Name**: `doesCourseExist()`.
    - **Type**: Addition
    - **Description**: Created a helper method, `doesCourseExist()` to utilize throughout
      various methods.
- **Method Name**: `retrieveDeptMapping()`.
    - **Type**: Addition
    - **Description**: Created a helper method, `retrieveDeptMapping()` to utilize throughout
      various methods. Pulls from
      `IndividualProjectApplication.myFileDatabase.getDepartmentMapping()`.
- **Method Name**: `getMajorCtFromDept()`.
    - **Type**: Bug
    - **Description**: Prints the number of majors * -1.
    - **Fix**: Ensure the proper output for `getNumberOfMajors()`.
- **Method Name**: `getMajorCtFromDept()`.
    - **Type**: Bug
    - **Description**: When department not found, `HttpStatus` is `FORBIDDEN`.
    - **Fix**: Change `HttpStatus` to `NOT_FOUND`.

Originally, I used the PMD rulesets:

```
<ruleset>category/java/errorprone.xml</ruleset>
<ruleset>rulesets/java/maven-pmd-plugin-default.xml</ruleset>
```

These were defined as satisfactory rulesets according to this EdStem post ([#140](https://edstem.
org/us/courses/58089/discussion/5269277?comment=12209100)). However, after adding a
ruleset, `rulesets/java/quickstart.xml`, I received more violations. These were cleaned up
after the fact. I'm hoping that I will not be marked off points because the TA specified that
the `errorprone.xml` and `maven-pmd-plugin-default.xml` would be sufficient. Either way, here
are the violations corresponding to `rulesets/java/quickstart.xml` that have been resolved.

```
<file name="/Users/zcox/Library/CloudStorage/GoogleDrive-zsc2107@columbia.edu/My Drive/learning/school/columbia/semester_1/COMS4156_SWE/projects_COMS4156/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/Course.java">
<violation beginline="185" endline="185" begincolumn="17" endcolumn="25" rule="MissingOverride" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="Course" method="toString" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#missingoverride" priority="3">
The method 'toString()' is missing an @Override annotation.
</violation>
</file>
<file name="/Users/zcox/Library/CloudStorage/GoogleDrive-zsc2107@columbia.edu/My Drive/learning/school/columbia/semester_1/COMS4156_SWE/projects_COMS4156/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/Department.java">
<violation beginline="14" endline="14" begincolumn="17" endcolumn="40" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="Department" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="29" endline="29" begincolumn="7" endcolumn="30" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="Department" method="Department" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="82" endline="82" begincolumn="10" endcolumn="33" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="Department" method="getCourseSelection" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="143" endline="143" begincolumn="17" endcolumn="25" rule="MissingOverride" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="Department" method="toString" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#missingoverride" priority="3">
The method 'toString()' is missing an @Override annotation.
</violation>
</file>
<file name="/Users/zcox/Library/CloudStorage/GoogleDrive-zsc2107@columbia.edu/My Drive/learning/school/columbia/semester_1/COMS4156_SWE/projects_COMS4156/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/IndividualProjectApplication.java">
<violation beginline="34" endline="34" begincolumn="15" endcolumn="18" rule="MissingOverride" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="IndividualProjectApplication" method="run" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#missingoverride" priority="3">
The method 'run(String[])' is missing an @Override annotation.
</violation>
<violation beginline="36" endline="36" begincolumn="11" endcolumn="30" rule="LiteralsFirstInComparisons" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="IndividualProjectApplication" method="run" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#literalsfirstincomparisons" priority="3">
Position literals first in String comparisons
</violation>
<violation beginline="79" endline="79" begincolumn="5" endcolumn="28" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="IndividualProjectApplication" method="resetDataFile" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="89" endline="89" begincolumn="5" endcolumn="32" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="IndividualProjectApplication" method="resetDataFile" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
</file>
<file name="/Users/zcox/Library/CloudStorage/GoogleDrive-zsc2107@columbia.edu/My Drive/learning/school/columbia/semester_1/COMS4156_SWE/projects_COMS4156/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/MyFileDatabase.java">
<violation beginline="24" endline="24" begincolumn="11" endcolumn="38" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="MyFileDatabase" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="57" endline="57" begincolumn="11" endcolumn="38" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="MyFileDatabase" method="deSerializeObjectFromFile" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="76" endline="76" begincolumn="26" endcolumn="53" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="MyFileDatabase" method="setMapping" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="98" endline="98" begincolumn="10" endcolumn="37" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="MyFileDatabase" method="getDepartmentMapping" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
</file>
<file name="/Users/zcox/Library/CloudStorage/GoogleDrive-zsc2107@columbia.edu/My Drive/learning/school/columbia/semester_1/COMS4156_SWE/projects_COMS4156/4156-Miniproject-2024-Students-Java/IndividualProject/src/main/java/dev/coms4156/project/individualproject/RouteController.java">
<violation beginline="50" endline="50" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="isCourseFull" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="51" endline="51" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="isCourseFull" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="87" endline="87" begincolumn="13" endcolumn="40" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="RouteController" method="retrieveDeptMapping" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="116" endline="116" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="retrieveCourse" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="117" endline="117" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="retrieveCourse" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="120" endline="120" begincolumn="9" endcolumn="32" rule="LooseCoupling" ruleset="Best Practices" package="dev.coms4156.project.individualproject" class="RouteController" method="retrieveCourse" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_bestpractices.html#loosecoupling" priority="3">
Avoid using implementation types like 'HashMap'; use the interface instead
</violation>
<violation beginline="155" endline="155" begincolumn="60" endcolumn="79" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="retrieveDepartment" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="180" endline="180" begincolumn="60" endcolumn="79" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="getMajorCtFromDept" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="203" endline="203" begincolumn="59" endcolumn="78" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="identifyDeptChair" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="227" endline="227" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseLocation" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="228" endline="228" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseLocation" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="261" endline="261" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseInstructor" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="262" endline="262" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseInstructor" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="295" endline="295" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseTime" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="296" endline="296" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="findCourseTime" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="323" endline="323" begincolumn="56" endcolumn="75" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="addMajorToDept" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="344" endline="344" begincolumn="61" endcolumn="80" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="removeMajorFromDept" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="367" endline="367" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="dropStudent" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="368" endline="368" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="dropStudent" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="406" endline="406" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="setEnrollmentCount" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="407" endline="407" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="setEnrollmentCount" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="408" endline="408" begincolumn="20" endcolumn="37" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="setEnrollmentCount" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="440" endline="440" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTime" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="441" endline="441" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTime" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="442" endline="442" begincolumn="20" endcolumn="36" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTime" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="474" endline="474" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTeacher" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="475" endline="475" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTeacher" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="476" endline="476" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseTeacher" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="510" endline="510" begincolumn="20" endcolumn="39" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseLocation" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="511" endline="511" begincolumn="20" endcolumn="41" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseLocation" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
<violation beginline="512" endline="512" begincolumn="20" endcolumn="40" rule="UnnecessaryAnnotationValueElement" ruleset="Code Style" package="dev.coms4156.project.individualproject" class="RouteController" method="changeCourseLocation" externalInfoUrl="https://docs.pmd-code.org/pmd-doc-7.3.0/pmd_rules_java_codestyle.html#unnecessaryannotationvalueelement" priority="3">
Avoid the use of value in annotations when its the only element
</violation>
</file>
```