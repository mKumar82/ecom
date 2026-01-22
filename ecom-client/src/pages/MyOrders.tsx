import OrderCard from "../components/OrderCard";
import { useGetOrdersQuery } from "../apiServices/orderApi";
import { useAppSelector } from "../redux/hooks";

const MyOrders = () => {
  const isAuthenticated = useAppSelector((state) => state.user.isAuthenticated);

  const { data: orders, isLoading } = useGetOrdersQuery(undefined, {
    skip: !isAuthenticated,
  });

  console.log("++++++++++++++++++", orders);

  return (
    <div className="mx-auto px-4 mt-6 grid grid-cols-1 gap-5">
      <h1 className="text-3xl font-bold mb-4">My Orders</h1>
      {/* Orders content will go here */}
      {/* doing reverse here for now but later get data in descing from backend only */}
      {isLoading &&
        Array.from({ length: 3 }).map((_, i) => (
          <div
            key={i}
            className="mx-auto w-full px-4 mt-6 grid grid-cols-1 gap-5 h-50 bg-gray-200 animate-pulse rounded"
          />
        ))}
      {orders && orders?.length > 0 ? (
        orders
          ?.slice() // creates a copy (important!)
          .reverse()
          ?.map((order) => <OrderCard key={order.id} {...order} />)
      ) : (
        <div className="text-center text-gray-500 mt-100 text-3xl">
          No orders 🛒
          <br />
          Start shopping now!
        </div>
      )}
      {/* {orders
        ?.slice() // creates a copy (important!)
        .reverse()
        ?.map((order) => (
          <OrderCard key={order.id} {...order} />
        ))} */}
    </div>
  );
};

export default MyOrders;
