DROP DATABASE IF EXISTS PMT_DB;
CREATE DATABASE PMT_DB;
USE PMT_DB;

CREATE TABLE Users
(
    ID   INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    Name VARCHAR(255) NOT NULL,
    Role VARCHAR(255)
);

CREATE TABLE Projects
(
    ID             INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    Name           VARCHAR(255) NOT NULL,
    Description    VARCHAR(255),
    AllocatedTime  INT,
    OwnerID        INT,
    ProjectMembers VARCHAR(255),
    Deadline       DATE,
    FOREIGN KEY (OwnerID) REFERENCES Users (ID)
);

CREATE TABLE SubProjects
(
    ID                INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    Name              VARCHAR(255) NOT NULL,
    Description       TEXT,
    AllocatedTime     INT,
    OwnerID           INT,
    SubProjectMembers VARCHAR(255),
    Deadline          DATE,
    ProjectID         INT,
    FOREIGN KEY (ProjectID) REFERENCES Projects (ID),
    FOREIGN KEY (OwnerID) REFERENCES Users (ID)
);

CREATE TABLE Tasks
(
    ID            INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    Name          VARCHAR(255) NOT NULL,
    Description   TEXT,
    AllocatedTime INT,
    OwnerID       INT,
    Deadline      DATE,
    SubprojectID  INT,
    FOREIGN KEY (SubprojectID) REFERENCES SubProjects (ID),
    FOREIGN KEY (OwnerID) REFERENCES Users (ID)
);
