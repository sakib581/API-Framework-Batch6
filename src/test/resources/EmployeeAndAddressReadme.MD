# Spring Data REST and JPA (MySQL DB) Example

This project depicts the Spring Boot Example with Spring Data REST and JPA - MySQL DB

## Description

This Project shows the list of Users which are stored in the MySQL. Using the following endpoints, different operations
can be achieved:

# EMPLOYEE

- `/employees/all` - This returns all employees in the Employees table
- `/employees/{id}` - This returns the details of the employee passed in URL
- `/employees/delete/{id}` - Delete's an employee based on the id provided
- `/employees/update/{id}` - Updates's an employee based on the id provided. Have to give the json data with updated
  data
- `/employees/add` - Add new employee/employees using the Employee model. Have to give the json file with new data eg.
  `[
  {
  "name": "rohan",
  "permanent": 1,
  "phone_number": 3482934,
  "role": "qa",
  "city": "NJ"
  }, {
  "name": "zan",
  "permanent": 0,
  "phone_number": 46453,
  "role": "dev",
  "city": "NY"
  }

]`

# ADDRESS

- `/address/all` - This returns all address in the address table
- `/address/{id}` - This returns the details of the address passed in URL
- `/address/delete/{id}` - Delete's an address based on the id provided
- `/address/update/{id}` - Updates's an address based on the id provided. Have to give the json data with updated data
- `/address/add` - Add new address/addresses using the Address model. Have to give the json file with new data eg.

`[
{
"employee_id": 1,
"street": "3343 102nd st",
"city": "Rego Park",
"state": "NY",
"apt": "g23",
"zipcode": 11374 }, {
"employee_id": 2,
"street": "sff",
"city": "43fvgd",
"state": null,
"apt": "g55",
"zipcode": 2423 }
]`
