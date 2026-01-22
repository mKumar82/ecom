# 🛒 Distributed E-Commerce System (Microservices)

A **microservices-based e-commerce application** built using **Spring Boot and React**, designed to demonstrate real-world backend workflows, event-driven communication, and frontend integration.

This project focuses on **scalability, service isolation, fault handling, and clean frontend-backend integration** and is built for **learning, demo, and resume showcase purposes**.

---

## 🎥 Demo Video

📌 **Silent Screen Recording (No Voice)**  
This demo showcases the complete end-to-end flow of the distributed e-commerce system, including frontend interaction, backend microservices, and asynchronous order processing.

▶️ **Demo Video Link:**  
https://drive.google.com/file/d/1EbeCnfKPMs5U0otnrmh_Ph2Qh47gRfEd/view?usp=sharing

**Demo Highlights:**
- User login and product browsing
- Order creation from React frontend
- Real-time order status updates (CREATED → RESERVED → PAID)
- Backend services registered via Eureka
- Event-driven communication using Kafka
- Inventory reservation and payment confirmation flow

## 🚀 Tech Stack

### Frontend
- React
- Redux Toolkit
- RTK Query
- JavaScript / TypeScript
- Tailwind CSS

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Cloud
- REST APIs

### Architecture & Infrastructure
- Microservices Architecture
- API Gateway
- Eureka Service Discovery
- Event-Driven Architecture
- Apache Kafka (Local setup)

### Databases
- PostgreSQL

### Authentication
- JWT (JSON Web Tokens)

### Tools
- Git & GitHub
- Docker (basic)
- Postman

---

## 🧩 Microservices Overview

| Service | Description |
|-------|------------|
| API Gateway | Central entry point for all client requests |
| Eureka Server | Service discovery and registration |
| User Service | User authentication and authorization |
| Order Service | Order creation and lifecycle management |
| Inventory Service | Product stock management and reservation |
| Payment Service | Payment processing and confirmation |
| Product Service | Product creation |

---

## 🏗️ System Architecture

- All microservices register with **Eureka Server**
- **API Gateway** routes requests to appropriate services
- Services communicate using:
  - **REST APIs** (synchronous)
  - **Kafka events** (asynchronous)
- JWT-based authentication for secured APIs

> 📌 **Architecture Diagram:** 
![System Architecture Diagram](diagram/ecomHLD.png)

---

## 🔄 Order Processing Flow

1. User places an order from the frontend
2. Order Service creates an order in `CREATED` state
3. Inventory Service reserves product stock
4. Payment Service processes payment
5. Order status is updated to `RESERVED` or `FAILED`
6. Inventory rollback occurs in case of payment failure

This ensures **eventual consistency** across services.

---

## 🔐 Security

- JWT-based authentication
- Secured REST APIs using Spring Security
- Token validation at service level

---

## 🖥️ Frontend Features

- User authentication
- Product browsing and ordering
- Order placement and tracking
- Loading indicators and error handling
- Clean and responsive UI

---

## 🧪 Error Handling & Edge Cases

- Inventory out-of-stock handling
- Payment failure and rollback handling
- Safe Kafka consumer execution
- Service restart recovery support

---

## 🛠️ Local Setup (Kafka Optional)

> Kafka is used **only for demonstration and learning** and can be disabled via configuration.

### Prerequisites
- Java 21
- Node.js
- PostgreSQL
- Docker (optional)

### Kafka Toggle
Kafka can be enabled or disabled using:

```properties
app.kafka.enabled=false
