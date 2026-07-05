# AWS Serverless Services

This project demonstrates how to build an **event-driven serverless application** using Java and AWS managed services, including **AWS Lambda**, **Amazon API Gateway**, **Amazon S3**, **Amazon EventBridge**, **Amazon DynamoDB**, **Amazon SNS**, and **AWS Serverless Application Model (AWS SAM)**.

The application exposes a REST API for vehicle registration. When a vehicle registration request is submitted, supporting documents are uploaded to Amazon S3, an event is published to Amazon EventBridge, registration details are stored in Amazon DynamoDB, and an email notification is sent using Amazon SNS.

Unlike traditional Java applications that run on continuously running application servers, this project demonstrates how independent AWS Lambda functions collaborate through an event-driven architecture to complete a business workflow without provisioning or managing servers.

The entire infrastructure is defined as code using AWS SAM, making deployments simple, repeatable, and version-controlled.

---

# Things to Remember

Before exploring the project, keep these points in mind.

- This is a **Serverless Application**, not a Microservices application.
- There are **no continuously running Java applications**.
- Each AWS Lambda executes only when an event occurs.
- Amazon API Gateway exposes the REST endpoint.
- Amazon EventBridge enables communication between Lambda functions.
- AWS SAM creates and deploys all required AWS resources.
- All infrastructure is defined in a single `template.yaml` file.
- You focus on writing business logic while AWS manages the infrastructure.

---

# What is Serverless Computing?

Serverless Computing is a cloud execution model where developers write application code without managing servers or infrastructure.

Instead of provisioning virtual machines, configuring operating systems, or maintaining application servers, developers deploy their code and AWS automatically handles execution, scaling, availability, and infrastructure management.

> **Serverless doesn't mean there are no servers. It means the servers are fully managed by AWS.**

---

# Why Serverless?

Serverless architecture allows developers to spend more time building business functionality instead of managing infrastructure.

### Benefits

- Automatic scaling
- Pay only for actual execution
- No server management
- High availability
- Faster deployments
- Lower operational cost
- Easy integration with AWS services

---

# When Should You Use Serverless?

Serverless is best suited for applications that are event-driven or API-driven.

Common use cases include:

- REST APIs
- File upload systems
- Notification services
- Image and document processing
- Payment processing
- Order management
- Scheduled jobs
- Background processing
- IoT applications
- Workflow automation

---

# Why This Project?

Many AWS Lambda examples demonstrate only basic concepts such as returning a simple response.

This project demonstrates how multiple AWS services work together to solve a real-world business problem using an event-driven architecture.

By building this application, you will learn how to:

- Build REST APIs using Amazon API Gateway
- Develop AWS Lambda functions using Java 21
- Upload files to Amazon S3
- Publish events using Amazon EventBridge
- Store data in Amazon DynamoDB
- Send notifications using Amazon SNS
- Deploy infrastructure using AWS SAM
- Build Infrastructure as Code using AWS CloudFormation
- Design loosely coupled serverless applications

---

# AWS Serverless Services Used in This Project

The application uses multiple AWS managed services, where each service performs a specific responsibility. Together, they build a complete event-driven serverless application.

```text
                                   Client
                                      │
                                      │ HTTP POST
                                      ▼
                          Amazon API Gateway
                                      │
                                      ▼
                      Register Vehicle Lambda
                     ┌────────────────┴────────────────┐
                     │                                 │
                     ▼                                 ▼
        Upload Documents to S3           Publish Registration Event
                     │                                 │
                     ▼                                 ▼
              Amazon S3                   Amazon EventBridge
                                                      │
                                                      |
                      ┌───────────────────────────────┴──────────────────────────────┐
                      ▼                                                              ▼
          Save Vehicle Lambda                                        Notify Applicant Lambda
                      │                                                              │
                      ▼                                                              ▼
             Amazon DynamoDB                                                   Amazon SNS
                                                                                     │
                                                                                     ▼
                                                                            Email Notification
```

### 1. AWS Lambda

AWS Lambda is a serverless compute service that executes code only when it is invoked.

**Used in this project**

- `RegisterVehicleLambda` processes vehicle registration requests.
- `SaveVehicleLambda` stores registration details in Amazon DynamoDB.
- `NotifyApplicantLambda` sends email notifications using Amazon SNS.


### 2. Amazon API Gateway

Amazon API Gateway creates and manages REST APIs without requiring a web server.

**Used in this project**

- Exposes the `POST /vehicles` endpoint.
- Receives HTTP requests from clients.
- Invokes `RegisterVehicleLambda`.
- Returns the HTTP response to the client.


### 3. Amazon S3

Amazon S3 is an object storage service for storing files of any size.

**Used in this project**

- Stores uploaded vehicle documents.
- Generates unique object keys.
- Provides durable and highly available storage.


### 4. Amazon EventBridge

Amazon EventBridge is an event bus that enables communication between independent services.

**Used in this project**

- Receives the `VehicleRegistrationSubmitted` event.
- Invokes `SaveVehicleLambda`.
- Invokes `NotifyApplicantLambda`.
- Keeps Lambda functions loosely coupled.


### 5. Amazon DynamoDB

Amazon DynamoDB is a fully managed NoSQL database service.

**Used in this project**

- Stores vehicle registration details.
- Persists Request ID, Owner Name and Vehicle Number.
- Automatically scales based on workload.


### 6. Amazon SNS

Amazon Simple Notification Service (SNS) is a messaging service used for notifications.

**Used in this project**

- Sends email notifications.
- Publishes registration confirmation messages.
- Delivers notifications to subscribed email addresses.


### 7. AWS Identity and Access Management (IAM)

AWS IAM controls access to AWS resources through roles and policies.

**Used in this project**

- Allows Lambda functions to access AWS services.
- Grants permissions to Amazon S3.
- Grants permissions to Amazon DynamoDB.
- Grants permissions to Amazon SNS.
- Grants permissions to Amazon EventBridge.


### 8. AWS Serverless Application Model (AWS SAM)

AWS SAM is a framework for building and deploying serverless applications.

**Used in this project**

- Creates AWS Lambda functions.
- Creates Amazon API Gateway.
- Creates Amazon S3 Bucket.
- Creates Amazon DynamoDB Table.
- Creates Amazon SNS Topic.
- Creates EventBridge Rules.
- Creates IAM Roles and Policies.
- Deploys the complete infrastructure from a single `template.yaml` file.

---

# Why This Is NOT a Microservices Project

Although this project contains multiple independent modules, it is **not** a Microservices application.

It is a **Serverless Event-Driven Application** built using AWS Lambda functions.

The Lambda functions execute only when they are triggered by an HTTP request or an event. There are no continuously running services, embedded web servers, or service discovery mechanisms.

| Microservices | Serverless |
|---------------|------------|
| Long-running services | Event-driven Lambda functions |
| Services run continuously | Functions execute only when invoked |
| Usually deployed to Kubernetes, ECS or EC2 | Deployed to AWS Lambda |
| Own web server | Managed by AWS |
| Service-to-service communication | API Gateway or EventBridge |
| Infrastructure managed by developers | Infrastructure managed by AWS |

This project demonstrates how to build a scalable **Serverless Event-Driven Architecture** using AWS managed services.

---

# Why Use EventBridge?

Instead of directly calling other Lambda functions, the application publishes an event to Amazon EventBridge.

This approach provides several advantages:

- Loose coupling between Lambda functions.
- Independent processing.
- Better scalability.
- Easier maintenance.
- New consumers can be added without modifying existing code.

For example, in the future you could add another Lambda to generate PDF reports, send SMS notifications, or perform analytics simply by subscribing to the same event.

---

# Project Overview

This project implements a **Vehicle Registration Portal** using AWS Serverless Services.

A user submits a vehicle registration request through a REST API. The application stores supporting documents in Amazon S3, publishes an event to Amazon EventBridge, saves registration details in Amazon DynamoDB, and finally sends an email notification using Amazon SNS.

Each business operation is handled by an independent AWS Lambda function, resulting in a loosely coupled, scalable, and event-driven architecture.

---

# Business Requirement

The application simulates a simple vehicle registration system.

When a registration request is submitted, the application should:

- Accept vehicle registration details.
- Accept supporting documents.
- Generate a unique Request ID.
- Upload documents to Amazon S3.
- Publish a registration event.
- Store registration details in Amazon DynamoDB.
- Send an email notification to the applicant.

---

# Sample Request

```json
{
  "ownerName": "John Doe",
  "vehicleNumber": "TS09AB1234",
  "vehicleType": "Car",
  "manufacturer": "Toyota",
  "model": "Camry",
  "manufacturingYear": 2024,
  "documents": [
    "insurance.pdf",
    "rc.pdf"
  ]
}
```

---

> **Note**
>
> For simplicity, this project does **not** upload actual files to Amazon S3. The `documents` field contains only document names such as `rc.pdf` and `insurance.pdf`.
>
> The sample upload logic is implemented in `S3Service`, where an Amazon S3 object is created for each document and populated with sample text content instead of the actual file bytes.
>
> In a production application, the REST API would typically accept real file uploads using `multipart/form-data` or Amazon S3 Pre-Signed URLs`, and the uploaded file content would be stored in Amazon S3.

---

# Sample Response

```json
{
  "success": true,
  "message": "Vehicle registration request submitted successfully.",
  "data": {
    "requestId": "e8d2a8c7-f574-4f18-aab5-6a47d8b8f5d1"
  }
}
```

---

# Solution (Application) Architecture

```text
                                  +----------------------+
                                  |      REST Client     |
                                  +----------+-----------+
                                             |
                                             |
                                             ▼
                                  +----------------------+
                                  | Amazon API Gateway   |
                                  +----------+-----------+
                                             |
                                             |
                                             ▼
                           +----------------------------------+
                           | Register Vehicle Lambda          |
                           +----------------------------------+
                           | RegisterVehicleHandler           |
                           | RegisterVehicleService           |
                           | S3Service                        |
                           | EventBridgeService               |
                           +----------+-----------+-----------+
                                      |           |
                                      |           |
                                      ▼           ▼
                             +---------------+  +----------------------+
                             | Amazon S3     |  | Amazon EventBridge   |
                             +---------------+  +----------+-----------+
                                                           |
                                     +---------------------+---------------------+
                                     |                                           |
                                     ▼                                           ▼
                     +-------------------------------+      +-------------------------------+
                     | Save Vehicle Lambda           |      | Notify Applicant Lambda       |
                     +-------------------------------+      +-------------------------------+
                     | SaveVehicleHandler            |      | NotifyApplicantHandler        |
                     | SaveVehicleService            |      | NotificationService           |
                     | VehicleRepository             |      +---------------+---------------+
                     +---------------+---------------+                      |
                                     |                                      |
                                     ▼                                      ▼
                         +--------------------------+             +--------------------------+
                         | Amazon DynamoDB          |             | Amazon SNS               |
                         +--------------------------+             +-----------+--------------+
                                                                             |
                                                                             ▼
                                                                  Email Notification
```

Key Features

- Vehicle registration using REST API
- Serverless architecture using AWS Lambda
- REST API using Amazon API Gateway
- Upload vehicle documents to Amazon S3
- Event-driven communication using Amazon EventBridge
- Store registration details in Amazon DynamoDB
- Send notifications using Amazon SNS
- Infrastructure as Code using AWS SAM
- Java 21 implementation
- Multi-module Maven project

---

# Request Lifecycle

### Step 1 - Submit Registration Request

The client sends a **POST** request to the `/vehicles` endpoint through Amazon API Gateway.


### Step 2 - Invoke RegisterVehicle Lambda

Amazon API Gateway invokes **RegisterVehicleLambda**.

This Lambda:

- Receives the request.
- Processes the registration.
- Coordinates the remaining workflow.


### Step 3 - Upload Documents

`RegisterVehicleLambda` uploads all supporting documents to **Amazon S3**.

Examples:

- insurance.pdf
- rc.pdf


### Step 4 - Publish Registration Event

After successfully uploading the documents, the Lambda publishes a **VehicleRegistrationSubmitted** event to **Amazon EventBridge**.


### Step 5 - Trigger Downstream Lambda Functions

Amazon EventBridge automatically invokes:

- SaveVehicleLambda
- NotifyApplicantLambda

Both Lambda functions execute independently and in parallel.


### Step 6 - Save Registration Details

`SaveVehicleLambda` stores the registration details in **Amazon DynamoDB**.

Stored information includes:

- Request ID
- Owner Name
- Vehicle Number


### Step 7 - Send Notification

`NotifyApplicantLambda` publishes a notification message to **Amazon SNS**.

Amazon SNS sends an email notification to all subscribed recipients.

---

# Project Structure

The project is organized as a **Multi-Module Maven Project**. Each module has a single responsibility, making the application modular, maintainable, and easy to extend.

```text
aws-serverless-services
│
├── notify-applicant-lambda
├── register-vehicle-lambda
├── save-vehicle-lambda
├── shared
├── pom.xml
├── template.yaml
└── samconfig.toml
```

---

# Project Modules

## 1. register-vehicle-lambda

This Lambda is the entry point of the application.

**Responsibilities**

- Receives REST requests from Amazon API Gateway.
- Uploads vehicle documents to Amazon S3.
- Generates a Request ID.
- Publishes `VehicleRegistrationSubmitted` events to Amazon EventBridge.
- Returns the API response.

**Package Structure**

```text
register-vehicle-lambda
│
├── config
├── handler
└── service
    ├── event
    └── s3
```


## 2. save-vehicle-lambda

This Lambda is triggered automatically by Amazon EventBridge.

**Responsibilities**

- Receives vehicle registration events.
- Stores registration details in Amazon DynamoDB.

**Package Structure**

```text
save-vehicle-lambda
│
├── config
├── handler
├── repository
└── service
```


## 3. notify-applicant-lambda

This Lambda is also triggered by Amazon EventBridge.

**Responsibilities**

- Receives vehicle registration events.
- Sends email notifications using Amazon SNS.

**Package Structure**

```text
notify-applicant-lambda
│
├── config
├── handler
└── service
```


## 4. shared

Contains reusable classes shared across all Lambda modules.

**Contents**

- DTOs
- Events
- Models
- Utilities

**Package Structure**

```text
shared
│
├── dto
├── events
├── model
└── util
```

---

# Application Layers (Project Architecture)

Each Lambda follows a simple layered architecture.

```text
Request / Event
       │
       ▼
    Handler
       │
       ▼
    Service
       │
       ▼
 Repository (Optional)
       │
       ▼
 AWS Service
```

### Handler

- Entry point of the Lambda function.
- Receives API Gateway requests or EventBridge events.
- Delegates processing to the Service layer.

### Service

- Contains the application's business logic.
- Coordinates interactions with AWS services.

### Repository

- Used only when persistence is required.
- Interacts with Amazon DynamoDB.

> **Note:** Not every Lambda requires a Repository. Only `save-vehicle-lambda` persists data, so it contains a Repository layer.

---

# Technology Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Maven | Build & Dependency Management |
| AWS Lambda | Serverless Compute |
| Amazon API Gateway | REST API |
| Amazon EventBridge | Event Routing |
| Amazon S3 | Object Storage |
| Amazon DynamoDB | NoSQL Database |
| Amazon SNS | Email Notifications |
| AWS IAM | Access Management |
| AWS SAM | Serverless Deployment |
| AWS CloudFormation | Infrastructure Provisioning |
| AWS SDK for Java v2 | AWS Service Integration |
| Jackson | JSON Processing |
| Lombok | Boilerplate Code Reduction |
| Docker | Local Lambda Runtime |

---

# Running the Application

Follow the steps below to build, run, and deploy the application.


## Step 1 - Prerequisites

Install the following software before getting started.

| Software | Version |
|----------|---------|
| Java | 21 |
| Apache Maven | 3.9+ |
| Docker Desktop | Latest |
| AWS CLI | Latest |
| AWS SAM CLI | Latest |
| Git | Latest |

Verify the installation.

```bash
java -version
mvn -version
docker --version
aws --version
sam --version
```
<img width="2780" height="768" alt="image" src="https://github.com/user-attachments/assets/3d684dda-7457-4ddd-898e-5d8d8adb0231" />

## Step 2 - Clone the Repository

```bash
git clone https://github.com/sirajchaudhary/aws-serverless-services.git

cd aws-serverless-services
```


## Step 3 - Build the Project

Compile all Lambda modules.

```bash
mvn clean install
```
<img width="3420" height="1238" alt="image" src="https://github.com/user-attachments/assets/c817b423-e7e0-4050-aa39-457293968793" />

Package the serverless application.

```bash
sam build
```
<img width="3392" height="1088" alt="image" src="https://github.com/user-attachments/assets/ae4262c2-3db9-40e6-9f9d-1026be2d94cb" />

## Step 4 - Run Locally (Optional)

Start the local API (On your local docker environment)

```bash
sam local start-api
```

The REST endpoint will be available at:

```text
http://127.0.0.1:3000/vehicles
```


## Step 5 - Test the Local API (Optional)

```bash
curl -X POST http://127.0.0.1:3000/vehicles \
-H "Content-Type: application/json" \
-d '{
  "ownerName":"John Doe",
  "vehicleNumber":"TS09AB1234",
  "vehicleType":"Car",
  "manufacturer":"Toyota",
  "model":"Camry",
  "manufacturingYear":2024,
  "documents":[
    "insurance.pdf",
    "rc.pdf"
  ]
}'
```


## Step 6 - Deploy to AWS

Configure AWS credentials (E.g. IAM User)

```bash
aws configure
```

Build the latest changes.

```bash
sam build
```

Deploy the application.

```bash
sam deploy --guided
```

Use the following values during the first deployment.

| Option | Value |
|---------|-------|
| Stack Name | aws-serverless-services |
| AWS Region | ap-south-1 |
| Confirm changes before deploy | No |
| Allow SAM CLI IAM role creation | Yes |
| Disable rollback | No |
| Save arguments | Yes |

<img width="2898" height="1884" alt="image" src="https://github.com/user-attachments/assets/c01320e1-cc17-4d76-bc6d-fa5d3fc2976c" />
<br />
<img width="3410" height="1908" alt="image" src="https://github.com/user-attachments/assets/ccf07985-5b8e-4e9f-aaa7-adf8d57b4090" />

After the first deployment, simply run:

```bash
sam deploy
```

AWS SAM automatically creates and configures:

- AWS Lambda Functions
- Amazon API Gateway
- Amazon S3 Bucket
- Amazon DynamoDB Table
- Amazon SNS Topic
- Amazon SNS Subscription
- Amazon EventBridge Rules
- IAM Roles and Policies


## Step 7 - Verify Deployment

List CloudFormation stacks.

```bash
aws cloudformation list-stacks
```

Describe the deployed stack.

```bash
aws cloudformation describe-stacks --stack-name aws-serverless-services
```
<img width="3116" height="1908" alt="image" src="https://github.com/user-attachments/assets/d07253e8-7ccf-4b44-a1f4-b12c98fd401f" />

List Lambda functions.

```bash
aws lambda list-functions
```
<img width="2874" height="1902" alt="image" src="https://github.com/user-attachments/assets/4c8a9cf5-1390-41da-b33e-68471d204c71" />

List API Gateway APIs.

```bash
aws apigateway get-rest-apis
```
<img width="2802" height="986" alt="image" src="https://github.com/user-attachments/assets/268f9875-1910-42b9-94a4-7f63f8260458" />

List S3 buckets.

```bash
aws s3 ls
```

List DynamoDB tables.

```bash
aws dynamodb list-tables
```
<img width="2352" height="274" alt="image" src="https://github.com/user-attachments/assets/f7c83778-974e-4cfb-9bdc-75717d7a8416" />

List SNS topics.

```bash
aws sns list-topics
```
<img width="2530" height="298" alt="image" src="https://github.com/user-attachments/assets/9dc6945a-a829-4a0e-be31-8a88b1d3fe4a" />

## Step 8 - Test the Deployed API

Use the API URL returned by CloudFormation.

```bash
curl -X POST https://<api-id>.execute-api.<region>.amazonaws.com/Prod/vehicles \
-H "Content-Type: application/json" \
-d '{
  "ownerName":"John Doe",
  "vehicleNumber":"TS09AB1234",
  "vehicleType":"Car",
  "manufacturer":"Toyota",
  "model":"Camry",
  "manufacturingYear":2024,
  "documents":[
    "insurance.pdf",
    "rc.pdf"
  ]
}'
```
<img width="3216" height="592" alt="image" src="https://github.com/user-attachments/assets/1f4a5a9d-2f6f-40ea-a887-77c7e5eb22ef" />

Verify the complete workflow:

- Documents are uploaded to Amazon S3.
  <img width="3462" height="970" alt="image" src="https://github.com/user-attachments/assets/d4f1640d-d7ab-49f6-87f9-c8480e5be5f4" />
  <br />
  <img width="2606" height="272" alt="image" src="https://github.com/user-attachments/assets/9c1f4711-6830-4021-804b-5463c66bf27b" />
  <br />
  <img width="2688" height="276" alt="image" src="https://github.com/user-attachments/assets/5e179858-45a9-4f66-bee7-fa93b697994d" />

- Registration details are stored in Amazon DynamoDB.
  <img width="3216" height="1662" alt="image" src="https://github.com/user-attachments/assets/b4d4d7cf-e5be-403e-b31a-c0441200ebea" />

- Email notification is received through Amazon SNS.
  <img width="3464" height="570" alt="image" src="https://github.com/user-attachments/assets/718c67d4-fda0-4bd3-ba1a-315aaad5ff2c" />

## Step 9 - Clean Up

Delete all AWS resources created by the project.

```bash
aws cloudformation delete-stack --stack-name aws-serverless-services
```

---
# What You'll Learn

By completing this project, you will gain practical experience with:

- Serverless Computing
- Event-Driven Architecture
- AWS Lambda
- Amazon API Gateway
- Amazon EventBridge
- Amazon S3
- Amazon DynamoDB
- Amazon SNS
- AWS Identity and Access Management (IAM)
- AWS Serverless Application Model (AWS SAM)
- AWS CloudFormation
- Infrastructure as Code (IaC)
- Java 21 Serverless Development
- Multi-Module Maven Projects
- AWS SDK for Java v2

---

# License

Free software, [Siraj Chaudhary](https://www.linkedin.com/in/sirajchaudhary/)
