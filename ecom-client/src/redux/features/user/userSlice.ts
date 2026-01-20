import { createSlice } from "@reduxjs/toolkit";

type initialStateType = {
  isAuthenticated: boolean;
  userInfo: null | {};
  token: null | string;
};

const token = localStorage.getItem("token");

const initialState: initialStateType = {
  isAuthenticated: !!token,
  userInfo: null,
  token: token || null,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setToken(state, action) {
      console.log("Setting token in userSlice:", action.payload);
      state.isAuthenticated = true;
      // (state.userInfo = action.payload.user),
      state.token = action.payload;
      localStorage.setItem("token", action.payload);
    },
    logout(state) {
      console.log("logging out user");
      state.token = null;
      state.isAuthenticated = false;
      localStorage.removeItem("token");
    },
  },
});

export const { setToken, logout } = userSlice.actions;

export default userSlice.reducer;
