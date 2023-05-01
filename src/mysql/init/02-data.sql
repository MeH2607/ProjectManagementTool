USE pmt_db;

INSERT INTO Users (Name, Role)
VALUES ("John Smith", "Project Manager"),
       ("Sarah Lee", "Developer"),
       ("Michael Johnson", "Designer"),
       ("Emily Davis", "QA Analyst"),
       ("Tom Wilson", "Developer");

INSERT INTO Projects (Name, Description, AllocatedTime, OwnerID, ProjectMembers, Deadline)
VALUES ("E-commerce Website", "Building an online store for a clothing brand", 100, 1, "1,2,3,4,5", "2023-10-31"),
       ("Mobile App", "Creating a social media app for iOS and Android", 120, 1, "1,2,3,4", "2023-08-31"),
       ("Web App", "Developing a project management tool for web", 80, 2, "2,3,4", "2023-12-31");

INSERT INTO SubProjects (Name, Description, AllocatedTime, OwnerID, SubProjectMembers, Deadline, ProjectID)
VALUES ("Design Phase", "Creating wireframes and mockups for the website", 20, 1, "1,3", "2023-06-30", 1),
       ("Backend Development", "Setting up the server and database for the app", 30, 2, "2,4", "2023-08-31", 2),
       ("Testing Phase", "Performing QA tests on the web app", 15, 3, "2,3,4", "2023-12-01", 3);

INSERT INTO Tasks (Name, Description, AllocatedTime, OwnerID, Deadline, SubprojectID)
VALUES
       ("Homepage Design", "Creating the design for the website's homepage", 5, 3, "2023-06-10", 1),
       ("Shopping Cart Functionality", "Implementing the shopping cart feature for the website", 10, 2, "2023-08-15", 1),
       ("User Authentication", "Creating the login and signup functionality for the mobile app", 15, 1, "2023-08-31", 2),
       ("Bug Fixing", "Fixing bugs in the web app's user interface", 5, 3, "2023-11-30", 3),
       ("Integration with Payment Gateway", "Implementing the ability to process payments using a third-party payment gateway", 20, 5, "2023-10-31", 4),
       ("Revamp Product Listings Page", "Redesigning and updating the product listings page of the website", 8, 2, "2023-07-15", 1),
       ("Market Research for Campaign", "Conducting market research and analysis to determine the target audience and messaging for a new marketing campaign", 30, 7, "2023-09-30", 5),
       ("User Manual for App", "Creating a user manual and documentation for a new software application", 12, 4, "2023-12-31", 6),
       ("QA Testing for Website", "Conducting comprehensive quality assurance testing of the website's functionality and user experience", 25, 6, "2023-11-15", 1);
