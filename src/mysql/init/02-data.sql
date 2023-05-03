USE pmt_db;
-- Add Users
INSERT INTO Users (Name, Role)
VALUES ("Amy Smith", "Project Manager"),
("James Lee", "Developer"),
("Alexa Johnson", "Designer"),
("Emma Davis", "QA Analyst"),
("Liam Wilson", "Developer"),
("Olivia Brown", "Project Manager"),
("William Davis", "Developer"),
("Sophia Miller", "Designer"),
("Ethan Jones", "QA Analyst"),
("Isabella Martin", "Developer");

-- Add Projects
INSERT INTO Projects (Name, Description, AllocatedTime, OwnerID, ProjectMembers, Deadline)
VALUES ("Marketing Campaign", "Creating a marketing campaign for a new product", 80, 1, "1,2,3,4,5", "2023-11-30"),
("Mobile Game Development", "Creating a multiplayer mobile game", 120, 6, "6,7,8,9,10", "2023-09-30"),
("Web Application Development", "Developing a web application for project management", 100, 1, "2,3,4,7,10", "2023-12-31");

-- Add Subprojects
INSERT INTO SubProjects (Name, Description, AllocatedTime, OwnerID, SubProjectMembers, Deadline, ProjectID)
VALUES ("Product Research", "Conducting research to determine the product to be marketed", 20, 1, "1,2,3", "2023-07-31", 1),
("Game Design", "Designing the game mechanics and interface", 30, 6, "6,7,8", "2023-06-30", 2),
("Backend Development", "Setting up the server and database for the web app", 40, 7, "7,3,4", "2023-09-30", 3),
("Social Media Marketing", "Creating and implementing a social media marketing strategy", 20, 1, "1,4,5", "2023-11-15", 1),
("Frontend Development", "Building the user interface and client-side functionality for the web app", 50, 10, "2,3,4,7", "2023-12-15", 3),
("Game Testing", "Performing QA tests on the mobile game", 20, 9, "9,10", "2023-09-15", 2),
("Web App Testing", "Conducting comprehensive QA testing of the web app's functionality and user experience", 30, 3, "2,7,10", "2023-12-15", 3);

INSERT INTO Tasks (Name, Description, AllocatedTime, OwnerID, Deadline, SubprojectID, Status)
VALUES
("Create User Profile API", "Develop and test API for user profile functionality", 10, 2, "2023-06-15", 1, 'TODO'),
("Implement User Profile UI", "Design and implement user profile UI elements", 20, 3, "2023-06-30", 1, 'Doing'),
("Add Profile Picture Upload", "Integrate profile picture upload functionality into user profile", 5, 4, "2023-07-05", 1, 'Done'),

("Create Database Schema", "Define database schema for storing social media posts", 15, 3, "2023-07-15", 2, 'TODO'),
("Create Post API", "Develop and test API for creating and updating social media posts", 10, 1, "2023-08-01", 2, 'Doing'),
("Implement Post Feed UI", "Design and implement UI elements for displaying social media post feed", 25, 5, "2023-08-15", 2, 'Done'),
   
("Create Login Screen", "Design and implement UI for user login screen", 10, 2, "2023-09-01", 3, 'TODO'),
("Add Password Reset Functionality", "Integrate password reset functionality into login screen", 10, 1, "2023-09-15", 3, 'Doing'),
("Test Login and Password Reset", "Perform comprehensive testing of login and password reset functionality", 15, 4, "2023-09-30", 3, 'Done'),
   
("Define Project Requirements", "Gather and document requirements for project management tool", 20, 4, "2023-10-01", 4, 'TODO'),
("Design Dashboard UI", "Design and implement UI for project dashboard", 25, 3, "2023-10-31", 4, 'Doing'),
("Add Task Creation Functionality", "Integrate functionality for creating new tasks into project dashboard", 10, 5, "2023-11-15", 4, 'Done'),
   
("Create Wireframes", "Create wireframes for website design", 15, 1, "2023-11-30", 5, 'TODO'),
("Design Homepage UI", "Design and implement UI for website homepage", 30, 3, "2023-12-15", 5, 'Doing'),
("Implement Responsive Design", "Integrate responsive design elements into website", 20, 2, "2023-12-31", 5, 'Done'),
   
("Define Test Plan", "Gather and document test requirements for web app", 15, 4, "2024-01-01", 6, 'TODO'),
("Perform Unit Tests", "Perform unit testing of web app functionality", 20, 2, "2024-01-15", 6, 'Doing'),
("Perform Integration Tests", "Perform integration testing of web app functionality", 30, 5, "2024-01-31", 6, 'Done'),
   
("Create Signup Form", "Design and implement UI for user signup form", 10, 1, "2024-02-01", 7, 'TODO'),
("Integration with Social Media API", "Implementing the ability to share content on social media platforms using their APIs", 18, 1, "2023-08-15", 7, 'Done'),
("Responsive Design for Mobile", "Optimizing the website for mobile devices and ensuring a smooth user experience", 12, 2, "2023-09-30", 7, 'Doing');







