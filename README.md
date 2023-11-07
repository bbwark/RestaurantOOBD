# RestaurantOOBD

This project is the product of the work done at the university following the Database and Object Oriented Programming course.

The objective is to produce an application with a graphical interface capable of tracking contacts between customers and waiters within restaurants.

The application allows the inclusion of new customers in the customer list every time a booking is made, allowing total management of the booking itself, inserting customers into the system, adding customers already present in the system to bookings, assigning waiters to the tables.

The database was built in PostgreSQL and has more than 15 procedures and as many triggers, it was then connected to the application written in Java through the corresponding JDBC library, with due care also to avoid possible injection into the database by intended users to break the correct functioning of the program.
