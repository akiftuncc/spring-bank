download packages: ./mvnw clean compile
start app: ./mvnw spring-boot:run


AUTH:
1- register
2- login

Endpoints:

POST /api/auth/register

- Request:
  { "email": "user@example.com", "password": "min 8 chars" }
- Responses:
  201:
  { "accessToken": "...", "tokenType": "Bearer", "expiresIn": 3600 }
  400: validation error (Problem Details)
  409: email already in use

POST /api/auth/login

- Request:
  { "email": "user@example.com", "password": "..." }
- Responses:
  200:
  { "accessToken": "...", "tokenType": "Bearer", "expiresIn": 3600 }
  400: validation error (Problem Details)
  401: invalid credentials

Security:

- Authorization: Bearer <accessToken>
- Stateless JWT. No user/account APIs are implemented yet.

USER:
1- get profile
2- update profile

ACCOUNT:
1- Get Balance
2- Set Balance
3- Spend Money
4- Send Money
