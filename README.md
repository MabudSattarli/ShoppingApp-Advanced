# 🛒 ShoppingApp - Advanced Spring Boot Backend

This project is an **Enterprise-level E-commerce API** built to demonstrate advanced software architecture principles using Spring Boot. It goes beyond simple CRUD operations by implementing sophisticated patterns such as Multi-Tenancy, AOP-based Rate Limiting, and Automated Maintenance.

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot 3.x** (Web, Data JPA, Validation, AOP, Actuator)
* **H2 Database** (In-Memory for rapid prototyping)
* **JUnit 5 & Mockito** (Unit & Integration Testing)
* **Micrometer** (Performance Metrics & Monitoring)

---

## 🔥 Key Features

### 1. 🏢 Multi-Tenancy (SaaS Architecture)
* Implemented **Shared Database** strategy for cost-efficiency.
* **Data Isolation:** Every request is filtered using `X-Tenant-ID` header.
* Uses **Request Scope** beans (`TenantContext`) and **Interceptors** to ensure users can only access their own organization's data.

### 2. 🛡️ Rate Limiting & Security
* Custom **AOP (Aspect-Oriented Programming)** annotation `@RateLimit` created to prevent DDOS attacks.
* Automatically blocks users (HTTP 429) who exceed the request threshold (e.g., 2 requests per 60 seconds).
* **Strong Password Validation:** Custom `ConstraintValidator` logic implementation.

### 3. ⚡ Performance & Monitoring
* **Caching:** implemented for frequently accessed data to reduce database load.
* **Performance Alerts:** Custom `@ExecutionTimeAlert` annotation measures method execution time. Logs warnings and pushes metrics to Micrometer if a method exceeds the defined threshold (e.g., 2000ms).

### 4. ⚙️ Resilience & Maintenance
* **Feature Flags:** Ability to enable/disable specific services (like User Export) via `application.properties` without changing code (`@ConditionalOnProperty`).
* **Scheduled Tasks:** Automated background jobs (`@Scheduled`) run daily at 02:00 AM to clean up old audit logs and prevent database bloat.

---

## 🛠️ How to Run

1.  Clone the repository:
    ```bash
    git clone [https://github.com/MabudSattarli/ShoppingApp-Advanced.git](https://github.com/MabudSattarli/ShoppingApp-Advanced.git)
    ```
2.  Navigate to the project directory and run:
    ```bash
    ./gradlew bootRun
    ```

---

## 🧪 API Usage Examples

Since this is a Multi-Tenant application, the **`X-Tenant-ID` header is required** for all requests.

### 1. Register User (Tenant: CocaCola)
* **URL:** `POST /users/register`
* **Header:** `X-Tenant-ID: cocacola`
* **Body:**
    ```json
    {
      "username": "Ali",
      "password": "StrongPassword1!"
    }
    ```

### 2. Register User (Tenant: Pepsi)
* **URL:** `POST /users/register`
* **Header:** `X-Tenant-ID: pepsi`
* **Body:**
    ```json
    {
      "username": "Vali",
      "password": "StrongPassword1!"
    }
    ```

### 3. Fetch All Users (Tenant Isolation Test)
* **URL:** `GET /users/all`
* **Header:** `X-Tenant-ID: cocacola`
* **Result:** Returns only "Ali". ("Vali" remains hidden/isolated).

---

## ✅ Testing Strategy

The project maintains high code quality through a comprehensive testing strategy:

1.  **Unit Tests:** Core business logic (`UserService`) is tested in isolation using **Mockito**.
2.  **Integration Tests:** End-to-end flows (Controller to Database) are verified using `@SpringBootTest` and **MockMvc**.
3.  **Load Testing:** Rate Limiting resilience is verified using custom **Java Multi-threading scripts** and **JMeter**.

---

**Author:** [Mabud Sattarli](https://github.com/MabudSattarli)