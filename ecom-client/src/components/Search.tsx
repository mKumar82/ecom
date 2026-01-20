import { useAppDispatch } from "../redux/hooks";
import { setSearchQuery } from "../redux/features/filter/filterSlice";

const Search = () => {
  const dispatch = useAppDispatch();
  const handleOnChange = (input: string) => {
    console.log("Search Input:", input);
    dispatch(setSearchQuery(input));
  };
  return (
    <div className="w-full mt-6 flex justify-center px-4">
      <input
        onChange={(e) => handleOnChange(e.target.value.trim())}
        type="text"
        placeholder="Search products..."
        className="w-full max-w-xl
        h-12 px-3
          border border-black-300
          rounded-xl
          focus:outline-none
          focus:ring-2 focus:ring-blue-200"
      />
      
    </div>
  );
};

export default Search;
