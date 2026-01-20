import { createApi } from "@reduxjs/toolkit/query/react";
import { createBaseQuery } from "./baseQuery";
import type { CreateOrderRequest, OrderType } from "./app_types";

export const orderApi = createApi({
  reducerPath: "orderApi",
  baseQuery: createBaseQuery(import.meta.env.VITE_BASE_URL),
  tagTypes: ["Orders"],
  endpoints: (builder) => ({
    getOrders: builder.query<OrderType[], void>({
      query: () => "orderservice/orders",
      providesTags: ["Orders"],
    }),
    getOrder: builder.query<OrderType, string>({
      query: (orderId) => `orderservice/orders/${orderId}`,
    }),
    cancelOrder: builder.mutation<void, string>({
      query: (orderId) => ({
        url: `orderservice/orders/${orderId}/cancel`,
        method: "PUT",
      }),
      invalidatesTags: ["Orders"],
    }),
    createOrder: builder.mutation<OrderType, CreateOrderRequest>({
      query: (orderData) => ({
        url: "orderservice/orders",
        method: "POST",
        body: orderData,
      }),
      invalidatesTags: ["Orders"],
    }),
  }),
});

export const {
  useGetOrdersQuery,
  useCreateOrderMutation,
  useGetOrderQuery,
  useCancelOrderMutation,
} = orderApi;
