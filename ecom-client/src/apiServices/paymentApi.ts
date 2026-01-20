import { createApi } from "@reduxjs/toolkit/query/react";
import { createBaseQuery } from "./baseQuery";
import type {
  ConfirmPaymentRequestType,
  ConfirmPaymentResponseType,
  PaymentRequestType,
  PaymentResponseType,
  VerifyPaymentResponseType,
} from "./app_types";

export const paymentApi = createApi({
  reducerPath: "paymentApi",
  baseQuery: createBaseQuery(import.meta.env.VITE_BASE_URL),
  endpoints: (builder) => ({
    initiatePayment: builder.mutation<PaymentResponseType, PaymentRequestType>({
      query: (payload) => ({
        url: "paymentservice/payments/initiate",
        method: "POST",
        body: payload,
      }),
    }),
    confirmPayment: builder.mutation<
      ConfirmPaymentResponseType,
      ConfirmPaymentRequestType
    >({
      query: (payload) => ({
        url: "paymentservice/payments/confirm",
        method: "POST",
        body: payload,
      }),
    }),
    verifyPayment: builder.query<VerifyPaymentResponseType, string>({
      query: (paymentId) => `paymentservice/payments/${paymentId}`,
    }),
  }),
});

export const {
  useInitiatePaymentMutation,
  useConfirmPaymentMutation,
  useVerifyPaymentQuery,
} = paymentApi;
