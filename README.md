# Tasks Tracker

A full-stack web application for managing tasks, learning goals, and personal progress.

## 🚀 Live Application

**Frontend:** https://taskprogresstrack.netlify.app

## 🛠️ Tech Stack

### Frontend

* React
* Vite
* Axios
* React Router
* JavaScript

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* JWT Authentication

### Database

* PostgreSQL
* Neon

### Email Service

* Brevo Transactional Email API
* Email OTP verification

### Deployment

* Netlify — Frontend
* Render — Backend
* Neon — PostgreSQL Database

## ✨ Features

* User registration
* Email OTP verification
* Secure password hashing
* JWT-based authentication
* Login and logout
* Task management
* Learning goals
* Progress tracking
* REST API backend
* PostgreSQL persistence
* Deployed full-stack application

## 🔐 Authentication Flow

```text
User Registration
       ↓
Generate OTP
       ↓
Send OTP via Brevo
       ↓
Verify OTP
       ↓
Enable User Account
       ↓
JWT Login
       ↓
Authenticated Application
```

## 📂 Project Structure

```text
Tasks Tracker
├── frontend
│   ├── Components
│   ├── Pages
│   └── Services
│
└── backend
    ├── Controller
    ├── Service
    ├── Repository
    ├── Entity
    ├── Security
    └── Configuration
```

## 📚 What I Learned

This project gave me practical experience building and deploying a full-stack application using Spring Boot and React.

Key areas I worked with:

* Designing REST APIs
* Connecting Spring Boot with PostgreSQL
* Implementing JWT authentication
* Password hashing with BCrypt
* Email OTP verification
* Environment variables and API keys
* CORS configuration
* Deploying a backend on Render
* Deploying a React frontend on Netlify
* Using Neon as a cloud PostgreSQL database
* Integrating external APIs
* Debugging differences between local and deployed environments

## 🔒 Security

Secrets such as database credentials, JWT secrets, and email API keys are stored using environment variables and are **not committed to the repository**.
