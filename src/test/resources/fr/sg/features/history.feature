Feature: Statement printing
  As a bank client In order to check my operations I want to see the history (operation, date, amount, balance) of my operations

  Scenario: Statement printing of an empty account
    Given There is an account with the following operations
      | date       | operation   | amount    | balance |
    When The user prints it's statements
    Then The operation should be accepted
    Then The statement printing should have the following lines
      | date       | operation   | amount    | balance |


  Scenario: Statement printing of a non empty account
    Given There is an account with the following operations
      | date       | operation   | amount    | balance |
      | 2021-10-21 | WITHDRAW    | 100       | 142     |
      | 2021-10-21 | DEPOSIT     | 100       | 242     |
      | 2021-10-21 | DEPOSIT     | 42        | 142     |
      | 2021-10-21 | DEPOSIT     | 100       | 100     |
    When The user prints it's statements
    Then The operation should be accepted
    Then The statement printing should have the following lines
      | date       | operation   | amount    | balance |
      | 2021-10-21 | WITHDRAW    | 100       | 142     |
      | 2021-10-21 | DEPOSIT     | 100       | 242     |
      | 2021-10-21 | DEPOSIT     | 42        | 142     |
      | 2021-10-21 | DEPOSIT     | 100       | 100     |