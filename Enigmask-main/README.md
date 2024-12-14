# Enigmask
An **End-to-End Encrypted (E2EE)** messaging application built with **Spring Boot**, 
**PostgreSQL**, and **RESTful APIs**. This project ensures secure real-time communication by implementing advanced 
encryption techniques like **ECDH**, **RSA**, and **AES**, while using **WebSocket** for low-latency message delivery.

---

## **Features**

### **Core Features**
- **End-to-End Encryption**: Messages are encrypted on the sender's side and decrypted only by the intended recipient.
- **Authentication**: Uses **public key cryptography** to verify user identities, preventing impersonation attacks.
- **Message Integrity**: Protects messages from tampering during transit.
- **Real-Time Messaging**: Ensures instant delivery of messages with **WebSocket** communication.

### **Security Features**
- **Key Exchange**: Utilizes **Elliptic Curve Diffie-Hellman (ECDH)** for secure key exchange between users.
- **Message Encryption**: Implements **AES** for symmetric encryption of messages after the key exchange.
- **Digital Signatures**: Verifies the authenticity and integrity of messages using **RSA** signatures.
- **Threat Notifications**: Alerts users if potential attacks (e.g., MITM, interception) are detected.

---

## **Technologies Used**

### **Backend**
- **Spring Boot 3.4.0**: Framework for RESTful APIs and WebSocket configuration.
- **Spring Security**: Provides authentication and encryption integration.
- **PostgreSQL**: Relational database to store user data and encrypted messages.
- **Bouncy Castle**: Cryptographic library for implementing ECDH, RSA, and AES.

### **Frontend**
- **HTML/CSS/JavaScript**: Lightweight client interface.
- **WebSocket API**: Enables real-time communication between users.

---

## **Setup and Installation**

### **Prerequisites**
1. **Java 21** or higher.

