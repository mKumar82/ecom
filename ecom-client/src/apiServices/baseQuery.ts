import { fetchBaseQuery } from "@reduxjs/toolkit/query/react";
// import { store } from "../redux/store";

export const createBaseQuery = (baseUrl: string) => {
  return fetchBaseQuery({
    baseUrl: baseUrl,
    prepareHeaders: (headers, { getState }) => {
      // const token = (getState() as any).user.token;
      const token = localStorage.getItem("token");
      // console.log("Attaching token to headers:", token);
      if (token) {
        headers.set("Authorization", `Bearer ${token}`);
      }
      return headers;
    },
  });
};
