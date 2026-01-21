import { useNavigate } from "react-router-dom";
import type { OrderType } from "../apiServices/app_types";

import { RiDeleteBin5Line } from "react-icons/ri";
import {
  useCancelOrderMutation,
  useGetOrderQuery,
} from "../apiServices/orderApi";
import { useAppSelector } from "../redux/hooks";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

const OrderCard = (order: OrderType) => {
  const navigate = useNavigate();
  const [cancelOrder] = useCancelOrderMutation();
  const isAuthenticated = useAppSelector((state) => state.user.isAuthenticated);

  const [shouldPoll, setShouldPoll] = useState(order.status === "CREATED");

  const { data: liveOrder, refetch } = useGetOrderQuery(order.id, {
    skip: !isAuthenticated || !shouldPoll,
    pollingInterval: shouldPoll ? 2000 : 0,
  });

  const effectiveOrder = liveOrder ?? order;
  const handlePayNow = () => {
    // Implement payment logic here
    console.log(`Initiating payment for order ${order.id}
    `);
    navigate(`/payment/${order.id}`);
  };

  const handleDeleteOrder = async () => {
    const res = await cancelOrder(order.id).unwrap();
    toast.success("Order cancelled");
    console.log("delete mutation res:   ", res);
  };

  useEffect(() => {
    if (!liveOrder || !shouldPoll) return;

    const isFinal =
      liveOrder.status === "RESERVED" ||
      liveOrder.status === "PAID" ||
      liveOrder.status === "FAILED";

    if (isFinal) {
      setShouldPoll(false);
      return;
    }
  }, [liveOrder]);
  return (
    <div className="m-3 border relative rounded-2xl">
      <h1 className="text-2xl font-bold p-4 border-b">
        Order #{order.id.slice(0, 8)}
      </h1>

      <div className="absolute top-4 right-4">
        {!shouldPoll && effectiveOrder.status === "CREATED" && (
          <button
            onClick={() => {
              // setRetryCount(0);
              setShouldPoll(true);
              refetch();
            }}
          >
            Check Status
          </button>
        )}

        {effectiveOrder.status === "CREATED" && (
          <button
            className="transition-transform hover:scale-110 duration-300"
            onClick={handleDeleteOrder}
          >
            <RiDeleteBin5Line color="red" className="size-8" />
          </button>
        )}

        {effectiveOrder.status === "RESERVED" && (
          <div className=" flex gap-3">
            <button
              onClick={handleDeleteOrder}
              className="transition-transform hover:scale-110 duration-300"
            >
              <RiDeleteBin5Line color="red" className="size-8" />
            </button>
            <button
              onClick={() => handlePayNow()}
              className="bg-yellow-400 px-4 py-1 rounded transition-transform hover:scale-110 duration-300"
            >
              Pay Now
            </button>
          </div>
        )}

        {effectiveOrder.status === "CANCELLED" && (
          <button
            // onClick={() => handlePayNow()}
            className="bg-red-400 px-4 py-1 rounded"
          >
            CANCELLED
          </button>
        )}

        {effectiveOrder.status === "COMPLETED" && (
          <button className="bg-green-400 px-4 py-1 rounded text-white">
            Paid
          </button>
        )}
      </div>

      <div className="p-4 flex">
        <div className="flex-1 lg:flex-1">
          <p className="text-lg mb-2">Date: {order.createdAt.slice(0, 10)}</p>
          <p className="text-lg mb-2">Total Amount: ₹{order.totalAmount}</p>
          <p className="text-lg mb-2">Status: {effectiveOrder.status}</p>
        </div>
        <div className="flex-1 lg:flex-3 lg:flex gap-10">
          <h2 className="text-xl font-semibold mb-2">Items:</h2>
          {order.items.map((item) => (
            <div
              key={item.productId}
              className="flex items-center mb-4  pb-2 w-fit"
            >
              <img
                src={item.imageUrl}
                alt={item.name}
                className="object-cover rounded mr-4 aspect-square size-16"
              />
              <div>
                <h3 className="text-lg font-medium">{item.name}</h3>
                <p className="text-md">Quantity: {item.quantity}</p>
                <p className="text-md">Price: ₹{item.price}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrderCard;
