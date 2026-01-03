# HomeVault - Asset Management System

A comprehensive microservices-based asset management system built with Spring Boot that helps users track their assets, manage vendors, and receive warranty expiry notifications.

## 🏗️ Architecture

HomeVault follows a microservices architecture with the following services:

- **Eureka Server** - Service discovery and registration
- **API Gateway** - Single entry point for all client requests
- **User Service** - User authentication and management
- **Vendor Service** - Vendor information management
- **Asset Service** - Asset tracking and management
- **Notification Service** - Email notifications for asset expiry

## 🚀 Features

- **User Management**
  - User registration and authentication
  - JWT-based security
  - Secure password encryption

- **Vendor Management**
  - Add and manage vendor information
  - Track vendor details (name, address, phone)
  - User-specific vendor isolation

- **Asset Management**
  - Create, read, and delete assets
  - Upload invoice images to Cloudinary
  - Track purchase and expiry dates
  - Automatic warranty status updates
  - Filter assets by user or vendor

- **Automated Notifications**
  - Scheduled warranty expiry checks
  - Email notifications via RabbitMQ
  - Batch processing for performance

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 4.0.1
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security with OAuth2 Resource Server
- **Database**: MySQL 8.0
- **Message Queue**: RabbitMQ
- **Email**: Spring Mail (SMTP)
- **File Storage**: Cloudinary
- **Communication**: OpenFeign
- **Build Tool**: Maven

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0
- RabbitMQ
- Cloudinary account (for image storage)
- Gmail account (for sending emails)

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/homevault.git
cd homevault
```

### 2. Start Infrastructure Services

Use Docker Compose to start MySQL and RabbitMQ:

```bash
docker-compose up -d
```

This will start:
- MySQL instances on ports 3306, 3307, and 3308
- RabbitMQ on ports 5672 (AMQP) and 15672 (Management UI)

### 3. Environment Variables

Create environment variables or update application.yaml files with the following:

#### Required for All Services
```bash
export JWT_SECRET=your-secret-key-here
export EUREKA_SERVER=http://localhost:8761/eureka
```

#### Asset Service Additional Variables
```bash
export CLOUDINARY_NAME=your-cloudinary-name
export CLOUDINARY_API_KEY=your-api-key
export CLOUDINARY_API_SECRET=your-api-secret
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=admin
export RABBITMQ_PASSWORD=admin
```

#### Notification Service Additional Variables
```bash
export EMAIL=your-email@gmail.com
export EMAIL_PASSWORD=your-app-password
export RABBITMQ_HOST=localhost
export RABBITMQ_PORT=5672
export RABBITMQ_USERNAME=admin
export RABBITMQ_PASSWORD=admin
```

### 4. Build All Services

```bash
# Build each service
cd eureka-server && mvn clean install && cd ..
cd api-gateway && mvn clean install && cd ..
cd user-service && mvn clean install && cd ..
cd vendor-service && mvn clean install && cd ..
cd asset-service && mvn clean install && cd ..
cd notification-service && mvn clean install && cd ..
```

### 5. Start Services in Order

```bash
# 1. Start Eureka Server
cd eureka-server && mvn spring-boot:run &

# 2. Wait for Eureka to start, then start other services
cd api-gateway && mvn spring-boot:run &
cd user-service && mvn spring-boot:run &
cd vendor-service && mvn spring-boot:run &
cd asset-service && mvn spring-boot:run &
cd notification-service && mvn spring-boot:run &
```

## 🌐 Service Ports

| Service | Port |
|---------|------|
| Eureka Server | 8761 |
| API Gateway | 8080 |
| User Service | 8081 |
| Vendor Service | 8082 |
| Asset Service | 8083 |
| Notification Service | 8084 |

## 📡 API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### User Service

```bash
# Register
POST /api/users/register
Content-Type: application/json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}

# Login
POST /api/users/login
Content-Type: application/json
{
  "email": "john@example.com",
  "password": "password123"
}

# Get User by ID (requires authentication)
GET /api/users/user/id/{id}
Authorization: Bearer {token}
```

### Vendor Service

```bash
# Add Vendor (requires authentication)
POST /api/vendors
Authorization: Bearer {token}
Content-Type: application/json
{
  "name": "Tech Vendors Inc",
  "phoneNumber": "1234567890",
  "address": "123 Main Street, City, State, ZIP"
}

# Get All Vendors (requires authentication)
GET /api/vendors
Authorization: Bearer {token}

# Get Vendor by ID (requires authentication)
GET /api/vendors/vendor/{vendorId}
Authorization: Bearer {token}

# Get Vendor List (requires authentication)
GET /api/vendors/list
Authorization: Bearer {token}
```

### Asset Service

```bash
# Create Asset (requires authentication)
POST /api/assets
Authorization: Bearer {token}
Content-Type: multipart/form-data
request: {
  "name": "Laptop",
  "company": "Dell",
  "serialNumber": "SN12345",
  "category": "Electronics",
  "purchaseOn": "2024-01-01",
  "expiryOn": "2026-01-01",
  "price": 1200.00,
  "vendorId": "vendor-uuid"
}
image: [file]

# Get All Assets (requires authentication)
GET /api/assets
Authorization: Bearer {token}

# Get Assets by Vendor (requires authentication)
GET /api/assets/vendor/{vendorId}
Authorization: Bearer {token}

# Delete Asset (requires authentication)
DELETE /api/assets/{assetId}
Authorization: Bearer {token}
```

## 🔐 Security

- JWT-based authentication
- OAuth2 Resource Server configuration
- Password encryption using BCrypt
- Stateless session management
- Service-to-service authentication via Feign interceptors

## 📧 Notification System

The system automatically checks for expired warranties every minute and sends email notifications to users. The notification flow:

1. Asset Service schedules a job to check expired assets
2. Expired assets are marked with `WARRANTY_EXPIRED` status
3. Notification messages are sent to RabbitMQ
4. Notification Service consumes messages and sends emails

## 🗄️ Database Schema

### Users Database
- User table with authentication details

### Vendors Database
- Vendor table with vendor information

### Assets Database
- Asset table with product details, warranty info, and relationships

## 🐰 RabbitMQ Configuration

- **Exchange**: notification.exchange (Direct)
- **Queue**: notification.queue
- **Routing Key**: notification.key

## 📝 License

This project is licensed under the MIT License.

## 👥 Author

Pranjal Jais

## 🐛 Known Issues

- Feign client in Asset Service uses hardcoded localhost URL instead of service discovery
- Async file deletion in Cloudinary may need error handling improvements

## 📞 Support

For support, email pranjaljais2@gmail.com or open an issue in the repository.
