Feature: Withdraw
  As a bank client I want to make a withdrawal from my account

  Scenario: Withdrawing money from an account
    Given There is an account with the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |
    When I withdraw an amount of 42
    Then The operation should be accepted
    Then The account should have the following operations
     | date       | operation   | amount    |
     | 2021-10-21 | WITHDRAW    | 42        |
     | 2021-10-21 | DEPOSIT     | 100       |


  Scenario: Withdrawing money from an account without sufficient funds
    Given There is an account with the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |
    When I withdraw an amount of 101
    Then The operation should be rejected
    Then The account should have the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |


  Scenario: Withdrawing a negative amount of money shouldn't work
    Given There is an account with the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |
    When I withdraw an amount of -42
    Then The operation should be rejected
    Then The account should have the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |

