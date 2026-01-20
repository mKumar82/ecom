import FilterSidebar from "../components/FilterSidebar";
import ProductGrid from "../components/ProductGrid";

import Search from "../components/Search";

const Home = () => {
  return (
    <div className="max-w-7xl mx-auto px-4 mt-6 grid grid-cols-1 lg:grid-cols-[260px_1fr] gap-6">
      {/* Desktop Filters */}
      <aside className="hidden lg:block">
        <FilterSidebar />
      </aside>

      {/* Products Section */}
      <section>
        <Search />

        {/* Mobile filter button */}
        <div className="flex justify-between items-center my-4 lg:hidden">
          <button className="border px-4 py-2 rounded-md">Filters</button>
          <span className="text-sm text-gray-500">Sort</span>
        </div>

        <ProductGrid />
      </section>
    </div>
  );
};

export default Home;
