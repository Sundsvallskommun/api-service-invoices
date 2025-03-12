# Invoices

_The service provides invoice metadata for citizens within Sundsvall municipality_

## Getting Started

### Prerequisites

- **Java 21 or higher**
- **Maven**
- **MariaDB**
- **Git**
- **[Dependent Microservices](#dependencies)**

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Sundsvallskommun/api-service-invoices.git
cd api-service-invoices
```
  
2. **Configure the application:**

   Before running the application, you need to set up configuration settings.
   See [Configuration](#configuration)

   **Note:** Ensure all required configurations are set; otherwise, the application may fail to start.

3. **Ensure dependent services are running:**

   If this microservice depends on other services, make sure they are up and accessible. See [Dependencies](#dependencies) for more details.

4. **Build and run the application:**

   - Using Maven:

   ```bash
   mvn spring-boot:run
   ```

   - Using Gradle:

   ```bash
   gradle bootRun
   ```

## Dependencies

This microservice depends on the following services:

- **DataWarehouseReader**
  - **Purpose:** Retrieve the metadata for invoices.
  - **Repository:** [https://github.com/Sundsvallskommun/api-service-datawarehousereader](https://github.com/Sundsvallskommun/api-service-datawarehousereader.git)
  - **Setup Instructions:** See documentation in repository above for installation and configuration steps.
- **InvoiceCache**
  - **Purpose:** Retrieve the complete invoices.
  - **Repository:** [https://github.com/Sundsvallskommun/api-service-invoice-cache](https://github.com/Sundsvallskommun/api-service-invoice-cache.git)
  - **Setup Instructions:** See documentation in repository above for installation and configuration steps.

Ensure that these services are running and properly configured before starting this microservice.

## API Documentation

Access the API documentation via Swagger UI:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Usage

### API Endpoints

See the [API Documentation](#api-documentation) for detailed information on available endpoints.

### Example Request

```bash
curl -X GET http://localhost:8080/2281/COMMERCIAL?partyId=fc21e65b-3608-4355-932c-442540034302
```

## Configuration

Configuration is crucial for the application to run successfully. Ensure all necessary settings are configured in `application.yml`.

### Key Configuration Parameters

- **Server Port:**

```yaml
server:
  port: 8080
```
- **External Service URLs**
```yaml
  integration:
    datawarehousereader:
      url: <service-url>
    invoicecache:
      url: <service-url>

  spring:
    security:
      oauth2:
        client:
          registration:
            datawarehousereader:
              client-id: <client-id>
              client-secret: <client-secret>
            invoicecache:
              client-id: <client-id>
              client-secret: <client-secret>

          provider:
            datawarehousereader:
              token-uri: <token-url>
            invoicecache:
              token-uri: <token-url>
```
### Additional Notes
- **Application Profiles:**
  Use Spring profiles (`dev`, `prod`, etc.) to manage different configurations for different environments.

- **Logging Configuration:**

  Adjust logging levels if necessary.

## Contributing

Contributions are welcome! Please see [CONTRIBUTING.md](https://github.com/Sundsvallskommun/.github/blob/main/.github/CONTRIBUTING.md) for guidelines.

## License

This project is licensed under the [MIT License](LICENSE).

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-invoices)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-invoices)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-invoices)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=sqale_rating)](https://sonarcloud.iso/summary/overall?id=Sundsvallskommun_api-service-invoices)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-invoices)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-invoices&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-invoices)

## 

Copyright (c) 2023 Sundsvalls kommun
