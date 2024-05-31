Veterinary Management System is a RESTful API that helps manage the operations of a veterinary clinic. This API provides endpoints to manage various resources, including veterinary doctors, customers, animals, vaccines, and appointments.


Main Features

- Managing veterinarians: saving, updating, viewing and deleting functions are available.
- Managing the available days of doctors: saving, updating, viewing and deleting functions are available.
- Managing customers: saving, updating, viewing, deleting functions are available.
- Managing animals belonging to customers: saving, updating, viewing deleting functions are available.
- Managing vaccines applied to animals: saving, updating, viewing and deleting functions are available.
- Creating appointments for animals to veterinarians: saving, updating, viewing and deleting functions are available.

Installation

1. Clone the repository.
2. Inside the project, create the postgre database for using the `vetapp.sql` file.
3. Open the `src/main/resources/application.properties` file and update the database connection information.
4. You may use the Postman collection which located in project file.
5. Use the requests in the Postman collection to test the API.
6. Optionally, Swagger is totally integrated and ready to use as well. 

Contributing

This project is open sourced and contributions are always welcome. To contribute, please follow the steps below:

1. Fork the project
2. Create a Feature Branch in your own Fork (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push the Branch (`git push origin feature/AmazingFeature`)
5. Create a Pull Request

