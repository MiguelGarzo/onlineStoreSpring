# Online Store Project - Java + Spring Boot + React + Stripe

## Description

That's my first Spring Boot + React project. I developed it within 2/3 weeks with ChatGPT as a supporter.

I used **Java - Spring Boot** as my backend and **Java Script - React** to my frontend. You will se a very simple frontend because I have never used it before, so I just wanted something functional to test my backend. It includes **Spring Security** and **JWT** with roles by users (ADMIN, USER) for different levels of permissions. You can also make payments with **Stripe**.

My intention with that is to share something **professional backend** to start my carreer as a freelancer. I will update my frontend when I have mastered my backend.

## Technologies

- **Backend**: Java 25, Spring Boot 3, Spring Security, JPA/Hibernate, MySQL(PostgreSQL).
- **Frontend**: React 18, React Router v6
- **Authentication**: JSON Web Token
- **Payments**: Stripe, Webhooks
- **Security**: BCrypt to encrypt passwords, tokens HS256, protected endpoints.
- **Tools**: Stripe CLI, Postman, OpenSSL, DBeaver, GitHub, IntellIJ IDEA  


## Functionaliteis

- Users Register and Login (with endpoints "/users/register", "/users/login")
- Roles: USER, ADMIN (Especial endpoints just for ADMINS)
- Products handler ("/products/**")
- Orders handler ("/orders/**")
- Cart handler ("/cart/**")
- Exceptions handler
- Payments with Stripe and local Webhooks
- JSON Web Tokens with expiration
- Jwt Filter


## Configuration

- Clone my repository wherever you want:

``` bash
git clone https://github.com/MiguelGarzo/onlineStoreSpring.git
cd onlineStoreSpring
```

- Configure application.properties:

    - Configure your DB conection with your own names:
    spring.application.name=Tienda
    spring.datasource.url=jdbc:postgresql://localhost:5432/tienda
    spring.datasource.username=postgres
    spring.datasource.password=Prueba1

    - Add your secrets (Webhook, Stripe Secret Key, Secret Key Token):
    stripe.webhook.secret=YOUR_WEBHOOK_SECRET
    stripe.api.key=YOUR_STRIPE_SECRET_KEY
    jwt.secret=YOUR_SECRET_KEY_TOKEN (must be 256bytes at least)
 
- Configure the frontend:

    
