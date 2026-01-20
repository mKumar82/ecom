import { createApi } from "@reduxjs/toolkit/query/react";
import { createBaseQuery } from "./baseQuery";

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: createBaseQuery(import.meta.env.VITE_BASE_URL),
  endpoints: (builder) => ({
    login: builder.mutation({
      query: (credentials) => ({
        url: "/userservice/auth/users/login",
        method: "POST",
        body: credentials,
      }),
    }),
    register: builder.mutation({
      query: (userInfo) => ({
        url: "userservice/auth/users/register",
        method: "POST",
        body: userInfo,
      }),
    }),
  }),
});

export const { useLoginMutation, useRegisterMutation } = authApi;
