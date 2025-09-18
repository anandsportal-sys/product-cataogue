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
