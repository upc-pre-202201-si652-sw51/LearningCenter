Feature: Student Adding
  As a Developer
  I want to add an Student through API
  So that It can be available to applications.

  Background:
    Given The Endpoint "http://localhost:%d/api/v1/posts" is available

  @student-adding
  Scenario: Add Student
    When a Post Request is sent with values "John Doe", 35, "Anywhere"
    Then A Response with Status 200 is received
    And an Student Resource with values 1, "John Doe", 35, "Anywhere" is included in Response Body

  @student-duplicated
  Scenario: Add Student with existing Name
    Given an Student Resource with values "John Doe", 35, "Anywhere" is already stored
    When A Post Request is sent with values "John Doe", 37, "Another place"
    Then A Response with Status 400 is received
    And A Message with value "An Student with the same Name already exists." is included in Response Body

