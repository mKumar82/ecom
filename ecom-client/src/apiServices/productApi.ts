import { createApi } from "@reduxjs/toolkit/query/react";
import { createBaseQuery } from "./baseQuery";
import type { Product } from "./app_types";


export const productApi = createApi({
  reducerPath: "productApi",
  baseQuery: createBaseQuery(import.meta.env.VITE_BASE_URL),
  endpoints: (builder) => ({
    getProducts: builder.query<Product[], void>({
      query: () => "productservice/products",
    }),
  }),
});

export const { useGetProductsQuery } = productApi;
