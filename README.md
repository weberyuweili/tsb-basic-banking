Here’s a sample **README.md** file for your project:

---

# TSB Basic Banking Application

This is a basic banking application designed to demonstrate features like user authentication, password reset with OTP, and secure API interactions using JWT. 

---

## Features
- **Login and Authentication**: Secure login with JWT-based session handling.
- **Password Reset**: Password reset functionality with OTP verification via SMS.
- **Account Management**: View account details and perform transactions.
- **Transfer Money**: Transfer funds between accounts with an intuitive interface.

---

## Getting Started

### Prerequisites
Ensure you have the following installed:
- **Node.js** (for running the client-side application)
- **Angular CLI** (for client-side development)
- **Java 11+** (for backend development)
- **Redis** (for in-memory token and OTP management)

### Setup Instructions

#### 1. Clone the Repository
```bash
git clone https://github.com/your-repository-url.git
cd your-repository-folder
```

#### 2. Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Update application properties in `src/main/resources/application.properties`:
   - Update database and Redis configurations if required.
3. Build and run the backend:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Backend will run at `http://localhost:8080`.

#### 3. Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   ng serve
   ```
4. Frontend will run at `http://localhost:4200`.

---

## Usage Instructions

### Login Credentials
To log in, use the following test credentials:

- **Email**: `test@tsb.co.nz`  
- **Password**: `password`

### Password Reset Flow
1. Navigate to the **Reset Password** page.
2. Enter the email address associated with your account and request a reset link.
3. Get the OTP and token sent to from console.
4. Set a new password to complete the reset process.

---

## Technology Stack

### Frontend
- **Angular 16**
- **Angular Material** for UI components

### Backend
- **Spring Boot**
- **Spring Security** for authentication
- **Redis** for in-memory OTP/token management

---

## Known Issues
- Didn't integrate any email or SMS service
- Reset password flow is not done

---

Let me know if you'd like any edits or additional details!