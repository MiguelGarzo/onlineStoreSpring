# Online Store Project - Java + Spring Boot + React + Stripe

## Description

That's my first Spring Boot + React project. I developed it within 2/3 weeks with ChatGPT as a supporter.

I used **Java - Spring Boot** as my backend and **Java Script - React** to my frontend. You will see a very simple frontend because I have never used it before, so I just wanted something functional to test my backend. It includes **Spring Security** and **JWT** with roles by users (ADMIN, USER) for different levels of permissions. You can also make payments with **Stripe**.

My intention with that is to share something **professional backend** to start my carreer as a freelancer. I will update my frontend when I have mastered my backend.


## Technologies & Versions

### Backend
- Java: 25.0.1 LTS (Temurin)
- Spring Boot: 4.0.1
- Spring Security: 4.0.1
- JJWT: 0.11.5
- Stripe Java SDK: 29.0.0
- PostgreSQL Driver: runtime
- Validation: spring-boot-starter-validation
- OpenAPI/Swagger: 2.3.0
- Lombok: provided

### Frontend
- React: 18.2.0
- React Router v6
- @stripe/stripe-js
- @stripe/react-stripe-js

### Tools
- Stripe CLI
- OpenSSL
- Postman
- IntelliJ IDEA


## Functionaliteis

- Users Register and Login (with endpoints "/users/register", "/users/login")
- Roles: USER, ADMIN (Special endpoints for ADMIN users)
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

    - Configure your DB connection with your own names:
    spring.application.name=Tienda
    spring.datasource.url=jdbc:postgresql://localhost:5432/tienda
    spring.datasource.username=postgres
    spring.datasource.password=Prueba1

    - Add your secrets (Webhook, Stripe Secret Key, Secret Key Token):
    stripe.webhook.secret=YOUR_WEBHOOK_SECRET
    stripe.api.key=YOUR_STRIPE_SECRET_KEY
    jwt.secret=YOUR_SECRET_KEY_TOKEN (must be 256bytes at least)
 
- Configure the frontend:

    - Go to tienda-frontend/src/index.js and add your Publicable_Stripe_Key:
    const stripePromise = loadStripe('pk_test_XXXXXXXXXXXXXXXXXXXXXXXX'); // your PUBLIC key here
    
    - Install node.js and add it to the path
    - Install the dependencies:
    ``` bash
    npm install
    npm install @stripe/react-stripe-js
    npm install @stripe/stripe-js
    ```

- Install and configure Stripe CLI:
    You can do it from here:
    https://docs.stripe.com/stripe-cli/install

    Then add it to the path and login:
    ``` bash
    stripe login
    ```

    Make the webhook listen:
    ``` bash
    stripe listen --forward-to localhost:8080/webhooks/stripe
    ```

    You can now try to do a paymentIntent.


## Future features

- Upgrade the frontend
- Add cookies-http only


## Contact

- Author: Miguel Garzo
- Mail: miguel.garzo53@gmail.com
- GitHub: https://github.com/MiguelGarzo


