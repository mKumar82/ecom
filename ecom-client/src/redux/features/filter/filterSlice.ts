import { createSlice } from "@reduxjs/toolkit";

type FilterState = {
  category: string[];
  rating: number;
  sortBy: string;
  priceRange: number;
  searchQuery: string;
};

const initialState: FilterState = {
  category: [],
  rating: 0,
  sortBy: "",
  priceRange: 0,
  searchQuery: "",
};

export const filterSlice = createSlice({
  name: "filter",
  initialState,
  reducers: {
    setCategoryFilter: (state, action) => {
      state.category = action.payload;
    },
    setRatingFilter: (state, action) => {
      state.rating = action.payload;
    },
    setSortByFilter: (state, action) => {
      state.sortBy = action.payload;
    },
    setPriceRangeFilter: (state, action) => {
      state.priceRange = action.payload;
    },
    setSearchQuery: (state, action) => {
      state.searchQuery = action.payload;
    },
    clearFilters: (state) => (state = initialState),
  },
});

export const {
  setCategoryFilter,
  setRatingFilter,
  setSortByFilter,
  setPriceRangeFilter,
  setSearchQuery,
  clearFilters,
} = filterSlice.actions;

export default filterSlice.reducer;
