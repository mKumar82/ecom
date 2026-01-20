import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { CartItem, Product } from "../../../apiServices/app_types";

type initialStateType = {
  items: CartItem[];
  totalQuantity: number;
  totalAmount: number;
};

const initialState: initialStateType = {
  items: [],
  totalQuantity: 0,
  totalAmount: 0,
};

export const cartSlice = createSlice({
  name: "cart",
  initialState: initialState,
  reducers: {
    addToCart(state, action: PayloadAction<Product>) {
      const product = action.payload;

      const existingItem = state.items.find((item) => item.id === product.id);

      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        state.items.push({ ...product, quantity: 1 });
      }
      state.totalQuantity += 1;
      state.totalAmount += product.price;
    },
    removeFromCart(state, action: PayloadAction<Product>) {
      const product = action.payload;

      const existingItem = state.items.find((item) => item.id === product.id);
      if (existingItem) {
        state.items = state.items.filter((item) => item.id !== product.id);
        state.totalQuantity -= existingItem.quantity;
        state.totalAmount -= existingItem.price * existingItem.quantity;
      }
    },
    increaseQuantity(state, action: PayloadAction<Product>) {
      const product = action.payload;

      const existingItem = state.items.find((item) => item.id === product.id);
      if (existingItem) {
        existingItem.quantity += 1;
        state.totalQuantity += 1;
        state.totalAmount += existingItem.price;
      }
    },
    decreaseQuantity(state, action: PayloadAction<Product>) {
      const product = action.payload;

      const existingItem = state.items.find((item) => item.id === product.id);
      if (existingItem && existingItem.quantity >= 1) {
        existingItem.quantity -= 1;
        state.totalQuantity -= 1;
        state.totalAmount -= existingItem.price;
      }
    },
  },
});

export const { addToCart, removeFromCart, increaseQuantity, decreaseQuantity } =
  cartSlice.actions;

export default cartSlice.reducer;
