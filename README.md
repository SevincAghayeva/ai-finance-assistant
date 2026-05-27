# AI Finance Assistant

A smart financial assistant API that leverages Spring Boot and Llama 3.3 via Groq to analyze user transactions and generate automated audit reports.

 **Live Production API:** https://ai-finance-assistant-yemm.onrender.com/api/v1/finance/audit

---

##  Quick Start & API Testing

Since this is a backend-only REST API, the root URL (`/`) will return a `404 Not Found` in your browser. To test the live AI features, send a `POST` request to the specific audit endpoint.

### Endpoint
```http
POST [https://ai-finance-assistant-yemm.onrender.com/api/v1/finance/audit](https://ai-finance-assistant-yemm.onrender.com/api/v1/finance/audit)
Content-Type: application/json

[
  {
    "description": "Wolt - Dinner",
    "amount": 15.00,
    "category": "Food",
    "date": "2026-05-27"
  },
  {
    "description": "Gym Membership",
    "amount": 50.00,
    "category": "Sports",
    "date": "2026-05-01"
  }
]

### Built With

Java 17 & Spring Boot 3.3

Groq Cloud API (Llama 3.3 70B Model)

Docker (Multi-stage build)

Render (Cloud Deployment)