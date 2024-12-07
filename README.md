# Simple OAuth 2.0 Implementation

This is an open-source project designed to provide a simple implementation of **OAuth 2.0** security concepts. It includes both an **Authorization Server** and an **OAuth Client**, making it easy for developers to understand the flow of authentication and authorization in a secure system.

---

## Features

- **OAuth Authorization Server**:
    - Issues secure access tokens and refresh tokens.
    - Handles token validation.
    - Uses Spring Security for robust security configurations.

- **OAuth Client**:
    - Requests access tokens from the Authorization Server.
    - Uses tokens to access protected resources.
    - Demonstrates secure communication between client and server.

- **Simple and Illustrative**:
    - Focuses on clarity and simplicity for beginners.
    - Can be adapted for practical use cases or as a learning resource.

---

## Requirements

- Java 23 
- Maven 3.6+
- Spring Boot 3.x
- A modern IDE (e.g., IntelliJ IDEA, Eclipse)

---

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/pokhrelricha/spring-security-oauth
cd oauth-2.0-implementation
```

### Build and Run
1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the Authorization Server:**
   ```bash
   cd authorization-server
   mvn spring-boot:run
   ```

3. **Run the OAuth Client:**
   ```bash
   cd oauth-client
   mvn spring-boot:run
   ```

---

## Understanding the Project Structure

- **Authorization Server**:
    - Configured using Spring Security and OAuth 2.0 libraries.
    - Manages token issuance and validation.

- **OAuth Client**:
    - Requests and uses tokens to access resources.
    - Demonstrates secure interactions with the server.

---

## Usage

1. Start the Authorization Server and OAuth Client applications.
2. Access the client application through your browser.
3. Authenticate using credentials configured in the Authorization Server.
4. Observe the token exchange and secure communication between client and server.

---

## Contributing

Contributions are welcome! Feel free to:
- Fork this repository.
- Create feature branches.
- Submit pull requests.

If you find any issues or have suggestions for improvement, open an issue, and let's discuss!

---

## Feedback

If you found this project helpful or have any feedback, feel free to connect with me on LinkedIn or open an issue here. Let's learn and grow together! 🚀
