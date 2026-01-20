import { useEffect } from "react";
import { useGetProductsQuery } from "../apiServices/productApi";
import { useAppSelector } from "../redux/hooks";
import ProductCard from "./ProductCard";

export default function ProductGrid() {
  const { data: products } = useGetProductsQuery();

  useEffect(() => {
    // const fetchedProducts = products() || [];
    console.log("Fetched Products:", products);
  });
  const searchQuery = useAppSelector((state) =>
    state.filter.searchQuery.toLowerCase()
  );

  // console.log("Search Query in ProductGrid:", searchQuery);

  const filteredProducts = products?.filter((product) =>
    product.title.toLowerCase().includes(searchQuery)
  );

  console.log("Filtered Products:", filteredProducts);
  return (
    <section className="mt-6">
      <h2 className="text-xl font-semibold mb-4">Products</h2>

      <div
        className="
          grid gap-4
          grid-cols-2
          sm:grid-cols-3
          md:grid-cols-4
        "
      >
        {filteredProducts?.length === 0 && searchQuery.length !== 0 ? (
          <h1>No products found</h1>
        ) : (
          filteredProducts?.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))
        )}
      </div>
    </section>
  );
}
