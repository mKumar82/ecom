import OrderCard from "../components/OrderCard";
import { useGetOrdersQuery } from "../apiServices/orderApi";

const MyOrders = () => {
  // const { data } = useGetOrdersQuery();
 const { data } = useGetOrdersQuery(undefined, {
   pollingInterval: 2000, // 2 seconds
 });

  console.log("++++++++++++++++++", data);
  // useEffect(() => {
  //     const orders = data;
  //     console.log("++++++++++++++++++",orders);
  //     console.log("++++++++++++++++++",orders);
  // }, [data]);
  return (
    <div className="max-w-7xl mx-auto px-4 mt-6 grid grid-cols-1 lg:grid-cols-[260px_1fr] gap-6">
      <h1 className="text-3xl font-bold mb-4">My Orders</h1>
      {/* Orders content will go here */}
      {/* doing reverse here for now but later get data in descing from backend only */}
      {data
        ?.slice() // creates a copy (important!)
        .reverse()
        ?.map((order) => (
          <OrderCard key={order.id} {...order} />
        ))}
    </div>
  );
};

export default MyOrders;
