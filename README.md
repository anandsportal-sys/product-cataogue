# Product Catalogue

## Backend (Spring Boot)

### Running the Spring Boot Server

```bash
./mvnw spring-boot:run
```
or
```bash
mvn spring-boot:run
```

The backend will be available at [http://localhost:8080](http://localhost:8080).

### Generating Default Products

To generate 1000 default products (or a custom number), use the following API:

- **Generate 1000 products (default):**
  ```
  POST http://localhost:8080/api/products/generate
  ```
  **curl:**
  ```bash
  curl -X POST http://localhost:8080/api/products/generate
  ```

- **Generate a custom number of products (e.g., 500):**
  ```
  POST http://localhost:8080/api/products/generate?count=500
  ```
  **curl:**
  ```bash
  curl -X POST "http://localhost:8080/api/products/generate?count=500"
  ```

You can use tools like `curl`, Postman, or your browser (if CORS is enabled) to call this endpoint.

## Frontend (React)

A React frontend is available under `frontend/` to display and search products.

### Running the frontend

```bash
cd frontend
npm install
npm start
```

The app will be available at [http://localhost:3000](http://localhost:3000).

Make sure the Spring Boot backend is running at [http://localhost:8080](http://localhost:8080).

---

## Assumptions

- The backend uses an in-memory H2 database by default.
- Product price is stored as a string (e.g., "$199.99") for simplicity.
- Product SKU is auto-generated during bulk product generation and is unique per product.
- The `/api/products` endpoint supports pagination and search across all product fields, including price.
- The React frontend expects the backend to be running on `localhost:8080`.
- CORS is enabled for `http://localhost:3000` to allow frontend-backend communication during development.
- No authentication or authorization is implemented.
- Product categories, brands, and other details are hardcoded for product generation.
- The maximum number of products that can be generated in one call is 10,000.
- Error handling is implemented globally for REST APIs.

## Possible Feature Enhancements

- Add authentication and authorization (e.g., JWT, OAuth2).
- Support for persistent databases (e.g., MySQL, PostgreSQL) with configuration.
- Allow image uploads for products.
- Add sorting and advanced filtering (by price range, category, brand, etc.) in both backend and frontend.
- Implement product detail pages and editing from the UI.
- Add bulk import/export functionality (CSV, Excel).
- Implement soft deletes and audit logging.
- Add internationalization/localization support.
- Improve UI/UX with better design and mobile responsiveness.
- Add unit and integration tests for backend and frontend.
- Dockerize the application for easier deployment.
