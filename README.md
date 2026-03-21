# DocVault - Secure Document Management System

DocVault is a full-stack web application that allows users to upload, manage, and securely store documents.
It supports file upload, download, deletion, and user authentication.


# Features

- 🔐 User Registration & Login
- 📤 Single & Multiple File Upload
- 📥 File Download System
- 🗑️ Delete File + Database Sync
- 🧠 Unique File Naming (Prevents overwrite)
- 💾 MySQL Database Integration
- 🌐 Frontend using HTML, CSS, JavaScript


# Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript
- **Tools:** Postman, Git, GitHub

---

# How to Run

# 1. Clone the repo
```bash
git clone https://github.com/vamshijala/docvault.git
cd docvault

# 2. Configure MySQL

Create database:

CREATE DATABASE document_vault;

Update application.properties:

spring.datasource.username=root
spring.datasource.password=yourpassword
# 3. Run Backend
.\mvnw spring-boot:run
# 4. Run Frontend

Open:

index.html

Features Demo
Upload files
View users
Download files
Delete users + files


Future Improvements

JWT Authentication
Cloud Storage (AWS S3)
React Frontend
Role-based Access


Developed by Vamshi

spring-boot java mysql file-upload fullstack project backend