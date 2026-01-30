# Personal Blogging Platform

This is a pet project that represents a functional blogging platform, created primarily to explore the practical implementation of core backend features. 
It covers essential **CRUD operations** for posts, comments, and user management, while placing a strong emphasis on a flexible security architecture. 
The highlight of the project is the **dynamic role-based access control (RBAC)** system, which supports granular permissions and hierarchical role relationships, 
allowing for a highly customizable approach to user authorization. 

---

## 🚀 Key Features

* **Content Management:** Full CRUD operations for Posts, Comments, and Tags.
* **Advanced Security:** * **JWT Authentication:** Stateless security using JSON Web Tokens.
    * **Hierarchical RBAC:** Role-Based Access Control supporting parent-child role relationships and granular permissions.
* **Search & Filtering:** Flexible post-filtering by title, tags and authors.
* **Database Versioning:** Managed schema evolutions using **Liquibase**.
* **Modern Architecture:** Clean separation of concerns with DTOs, Mappers (MapStruct), and Service layers.

---

## 🛠 Tech Stack

| Category              | Technology                                                                 |
|-----------------------|----------------------------------------------------------------------------|
| **Core Framework** | Java 21, Spring Boot 3.5.6                                                  |
| **Security** | Spring Security, JWT                                                      |
| **Data Access** | Spring Data JPA, Hibernate                                                |
| **Database** | PostgreSQL                                                                |
| **Migrations** | Liquibase                                                                 |
| **Build Tool** | Gradle                                                                    |
| **DevOps** | Docker Compose                                                         |
| **Utilities** | Lombok, MapStruct                                                         |

---

## 🏗 Database Architecture

The system uses a highly normalized PostgreSQL schema:
* **Self-Referencing Roles:** The `RoleEntity` supports a hierarchical structure (parent-child roles), allowing for complex permission inheritance.
* **Many-to-Many Relationships:** Optimized join tables for Posts-Tags and Roles-Permissions.
* **Automated Migrations:** Every schema change is tracked via Liquibase changelogs, ensuring environment consistency.

---

## 🚦 Getting Started

### Prerequisites
* **JDK 17** or higher
* **Docker Compose** (used only for running the PostgreSQL database)

### Local Development
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Lisovskiy14/Personal-Blogging-Platform-API.git 
   cd personal-blogging-platform
