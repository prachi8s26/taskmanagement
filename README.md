# Task Management System (Low-Level Design)

## Overview

This project implements a **Task Management System** designed using low-level design principles. The system allows users to create, assign, update, and track tasks while supporting priorities, statuses, comments, and extensibility for future enhancements.

The primary goal of this project is to demonstrate clean object-oriented design, proper separation of responsibilities, and a scalable architecture.

---


## Core Domain Entities

- **Task**  
  Represents a unit of work with metadata such as status, priority, assignee, and comments.

- **User**  
  Represents a system user who can create, assign, and work on tasks.

- **Comment**  
  Represents user-generated comments associated with a task.

- **TaskStatus**  
  Enum defining the lifecycle states of a task.

- **TaskPriority**  
  Enum defining priority levels for tasks.

- **TaskManager / TaskManagementSystem**  
  Central service responsible for managing tasks and coordinating operations.

---

## Design & Architecture

### UML Class Diagram

![Task Management System Class Diagram](../../../../uml-diagrams/class-diagrams/taskmanagementsystem-class-diagram.png)

---

## Class Responsibilities

### Task

**Fields**
- id  
- title  
- description  
- status  
- priority  
- assignee (User)  
- comments (List<Comment>)

**Key Methods**
- updateStatus(TaskStatus)  
- updatePriority(TaskPriority)  
- assignUser(User)  
- addComment(Comment)  

---

### User

**Fields**
- id  
- name  

**Key Methods**
- getId()  
- getName()  

---

### Comment

**Fields**
- id  
- content  
- author (User)  
- timestamp  

---

### TaskStatus (Enum)

Defines task states such as:
- TODO  
- IN_PROGRESS  
- DONE  

---

### TaskPriority (Enum)

Defines task priority levels:
- LOW  
- MEDIUM  
- HIGH  

---

### TaskManager / TaskManagementSystem

**Responsibilities**
- Manage the task lifecycle  
- Assign and update tasks  
- Add comments to tasks  
- Provide task querying and filtering APIs  

**Key Methods**
- createTask(...)  
- assignTask(...)  
- updateTaskStatus(...)  
- updateTaskPriority(...)  
- addCommentToTask(...)  
- listTasks()  
- listTasksByStatus(...)  
- listTasksByAssignee(...)  

---

## Design Principles & Patterns Used

- **Separation of Concerns**  
  Each class has a single, well-defined responsibility.

- **Manager / Service Pattern**  
  Centralized task operations are handled by the TaskManager.

- **Enum-Based Extensibility**  
  Task statuses and priorities can be extended with minimal changes.

---

## Example Usage

```java
TaskManager manager = new TaskManager();

User alice = new User("Alice");
User bob = new User("Bob");

Task task = manager.createTask(
    "Implement login",
    "Add login functionality",
    TaskPriority.HIGH,
    alice
);

manager.assignTask(task.getId(), bob);
manager.updateTaskStatus(task.getId(), TaskStatus.IN_PROGRESS);
manager.addCommentToTask(
    task.getId(),
    new Comment("Started working on this", bob)
);
