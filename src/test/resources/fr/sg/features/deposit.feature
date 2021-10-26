Feature: Deposit
  As a bank client I want to make a deposit in my account

  Scenario: Deposing money in an account
    Given There is an account with the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |
    When I depose an amount of 42
    Then The operation should be accepted
    Then The account should have the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 42        |
      | 2021-10-21 | DEPOSIT     | 100       |


  Scenario: Deposing a negative amount of money shouldn't work
    Given There is an account with the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |
    When I depose an amount of -42
    Then The operation should be rejected
    Then The account should have the following operations
      | date       | operation   | amount    |
      | 2021-10-21 | DEPOSIT     | 100       |

