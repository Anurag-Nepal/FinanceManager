# Finance Manager API Documentation

## Introduction

Welcome to the Finance Manager API documentation. This document provides detailed information about the endpoints available for managing income, expenses, and user accounts. Each section includes the endpoint details, request and response formats, and other relevant information.

## Income Management

### Add Income
**Endpoint:** `POST /income/add`  
**Description:** Adds a new income entry to the database.  
**Request Body:**
<pre>
{
  "amount": 1000.00,
  "source": "Salary",
  "date": "2024-08-01"
}
</pre>
**Response:**
<pre>
Status Code: 200 OK
Body: No content
</pre>

### Get Total Balance
**Endpoint:** `GET /income/totalbalance`  
**Description:** Retrieves the total income balance.  
**Response:**
<pre>
Status Code: 200 OK
Body:
5000.00
</pre>

### Get Weekly Income
**Endpoint:** `GET /income/weekbalance`  
**Description:** Retrieves a list of income entries from the last week.  
**Response:**
<pre>
Status Code: 200 OK
Body:
[
  {
    "amount": 200.00,
    "source": "Freelance",
    "date": "2024-08-10"
  },
  ...
]
</pre>

### Get Monthly Income
**Endpoint:** `GET /income/monthbalance`  
**Description:** Retrieves a list of income entries from the last month.  
**Response:**
<pre>
Status Code: 200 OK
Body:
[
  {
    "amount": 1200.00,
    "source": "Bonus",
    "date": "2024-07-20"
  },
  ...
]
</pre>

### Get Income Progress Card
**Endpoint:** `GET /income/incomecard`  
**Description:** Retrieves a card showing the progress of income.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Total Income",
  "amount": 5000.00
}
</pre>

### Get Salary Card
**Endpoint:** `GET /income/salarycard`  
**Description:** Retrieves a card showing the total salary income.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Salary",
  "amount": 3000.00
}
</pre>

### Get Investment Card
**Endpoint:** `GET /income/investmentcard`  
**Description:** Retrieves a card showing the total income from investments.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Investments",
  "amount": 1500.00
}
</pre>

### Get Others Income Card
**Endpoint:** `GET /income/otherscard`  
**Description:** Retrieves a card showing income from other sources.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Others",
  "amount": 500.00
}
</pre>

### Get Real Estate Card
**Endpoint:** `GET /income/realstatecard`  
**Description:** Retrieves a card showing income from real estate.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Real Estate",
  "amount": 2000.00
}
</pre>

### Delete Income
**Endpoint:** `DELETE /income/delete/{iid}`  
**Description:** Deletes an income entry by ID.  
**Path Variable:**
<pre>iid</pre> - ID of the income entry to delete  
**Response:**
<pre>
Status Code: 204 No Content
</pre>

## Expense Management

### Add Expense
**Endpoint:** `POST /expense/add`  
**Description:** Adds a new expense entry to the database.  
**Request Body:**
<pre>
{
  "amount": 50.00,
  "category": "Groceries",
  "description": "Grocery shopping",
  "date": "2024-08-01"
}
</pre>
**Response:**
<pre>
Status Code: 200 OK
Body: No content
</pre>

### Get Total Expenses
**Endpoint:** `GET /expense/totalexpense`  
**Description:** Retrieves the total expenses.  
**Response:**
<pre>
Status Code: 200 OK
Body:
2000.00
</pre>

### Get Weekly Expenses
**Endpoint:** `GET /expense/weekexpense`  
**Description:** Retrieves a list of expense entries from the last week.  
**Response:**
<pre>
Status Code: 200 OK
Body:
[
  {
    "amount": 30.00,
    "category": "Bills",
    "description": "Electricity bill",
    "date": "2024-08-10"
  },
  ...
]
</pre>

### Get Monthly Expenses
**Endpoint:** `GET /expense/monthexpense`  
**Description:** Retrieves a list of expense entries from the last month.  
**Response:**
<pre>
Status Code: 200 OK
Body:
[
  {
    "amount": 150.00,
    "category": "Entertainment",
    "description": "Movie tickets",
    "date": "2024-07-20"
  },
  ...
]
</pre>

### Get Expense Progress Card
**Endpoint:** `GET /expense/expensecard`  
**Description:** Retrieves a card showing the progress of expenses.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Total Expenses",
  "amount": 2000.00
}
</pre>

### Get Groceries Card
**Endpoint:** `GET /expense/groceriescard`  
**Description:** Retrieves a card showing total expenses for groceries.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Groceries",
  "amount": 500.00
}
</pre>

### Get Bills Card
**Endpoint:** `GET /expense/billscard`  
**Description:** Retrieves a card showing total expenses for bills.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Bills",
  "amount": 300.00
}
</pre>

### Get Entertainment Card
**Endpoint:** `GET /expense/entertaintmentcard`  
**Description:** Retrieves a card showing total expenses for entertainment.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Entertainment",
  "amount": 200.00
}
</pre>

### Get Others Expense Card
**Endpoint:** `GET /expense/otherscard`  
**Description:** Retrieves a card showing expenses in other categories.  
**Response:**
<pre>
Status Code: 200 OK
Body:
{
  "category": "Others",
  "amount": 100.00
}
</pre>

### Delete Expense
**Endpoint:** `DELETE /expense/delete/{eid}`  
**Description:** Deletes an expense entry by ID.  
**Path Variable:**
<pre>eid</pre> - ID of the expense entry to delete  
**Response:**
<pre>
Status Code: 200 OK
Body:
"Deleted Successfully"
</pre>

## User Management

### Register User
**Endpoint:** `POST /register`  
**Description:** Registers a new user.  
**Request Body:**
<pre>
{
  "username": "john_doe",
  "password": "securepassword",
  "email": "john.doe@example.com"
}
</pre>
**Response:**
<pre>
Status Code: 200 OK
Body:
"Registration successful"
</pre>

### Verify User
**Endpoint:** `POST /verify`  
**Description:** Verifies user registration with an OTP.  
**Request Body:**
<pre>
{
  "username": "john_doe",
  "otp": 123456
}
</pre>
**Response:**
<pre>
Status Code: 200 OK
Body:
"Verification successful"
</pre>

### User Login
**Endpoint:** `POST /login`  
**Description:** Authenticates a user and returns a login token.  
**Request Body:**
<pre>
{
  "username": "john_doe",
  "password": "securepassword"
}
</pre>
**Response:**
<pre>
Status Code: 200 OK
Body:
"Login successful"
</pre>



## Anurag Nepal

&copy; 2024 PennyWiseNepal API Documentation. All rights reserved.
