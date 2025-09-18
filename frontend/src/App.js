import React, { useEffect, useState } from "react";
import axios from "axios";

const PAGE_SIZE = 20;

function App() {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchProducts = async (searchTerm = "", pageNum = 0) => {
    const params = {
      page: pageNum,
      size: PAGE_SIZE,
    };
    if (searchTerm) params.search = searchTerm;
    const res = await axios.get("http://localhost:8080/api/products", { params });
    setProducts(res.data.content || []);
    setTotalPages(res.data.totalPages || 1);
  };

  useEffect(() => {
    fetchProducts(search, page);
    // eslint-disable-next-line
  }, [page]);

  const handleSearch = (e) => {
    setSearch(e.target.value);
    setPage(0);
    fetchProducts(e.target.value, 0);
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Product Catalogue</h2>
      <input
        type="text"
        placeholder="Search products..."
        value={search}
        onChange={handleSearch}
        style={{ marginBottom: 16, width: 300, padding: 8 }}
      />
      <table border="1" cellPadding="8" cellSpacing="0" width="100%">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Category</th>
            <th>SKU</th>
            <th>Brand</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {products.length === 0 && (
            <tr>
              <td colSpan="6" align="center">No products found.</td>
            </tr>
          )}
          {products.map((p) => (
            <tr key={p.sku}>
              <td>{p.name}</td>
              <td>{p.description}</td>
              <td>{p.category}</td>
              <td>{p.sku}</td>
              <td>{p.brand}</td>
              <td>{p.price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{ marginTop: 16 }}>
        <button onClick={() => setPage((p) => Math.max(p - 1, 0))} disabled={page === 0}>
          Prev
        </button>
        <span style={{ margin: "0 12px" }}>
          Page {page + 1} of {totalPages}
        </span>
        <button onClick={() => setPage((p) => Math.min(p + 1, totalPages - 1))} disabled={page + 1 >= totalPages}>
          Next
        </button>
      </div>
    </div>
  );
}

export default App;
