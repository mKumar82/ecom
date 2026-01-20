import { useNavigate } from "react-router-dom";
import type { OrderType } from "../apiServices/app_types";

import { RiDeleteBin5Line } from "react-icons/ri";
import { useCancelOrderMutation } from "../apiServices/orderApi";

const OrderCard = (order: OrderType) => {
  const navigate = useNavigate();
  const [cancelOrder] = useCancelOrderMutation();
  const handlePayNow = () => {
    // Implement payment logic here
    console.log(`Initiating payment for order ${order.id}
    `);
    navigate(`/payment/${order.id}`);
  };

  const handleDeleteOrder = async () => {
    const res = await cancelOrder(order.id).unwrap();
    console.log("delete mutation res:   ", res);
  };
  return (
    <div className="m-3 border relative rounded-2xl">
      <h1 className="text-2xl font-bold p-4 border-b">
        Order #{order.id.slice(0, 8)}
      </h1>

      <div className="absolute top-4 right-4">
        {/* {order.status === "CREATED" && (
          <div className=" flex gap-3">
            <button onClick={handleDeleteOrder} className="">
              <RiDeleteBin5Line color="red" className="size-8" />
            </button>
            <button
              onClick={() => handlePayNow()}
              className="bg-yellow-400 px-4 py-1 rounded"
            >
              Pay Now
            </button>
          </div>
        )} */}

        {order.status === "CREATED" && (
          <button onClick={handleDeleteOrder}>
            <RiDeleteBin5Line color="red" className="size-8" />
          </button>
        )}

        {order.status === "RESERVED" && (
          <div className=" flex gap-3">
            <button onClick={handleDeleteOrder} className="">
              <RiDeleteBin5Line color="red" className="size-8" />
            </button>
            <button
              onClick={() => handlePayNow()}
              className="bg-yellow-400 px-4 py-1 rounded"
            >
              Pay Now
            </button>
          </div>
        )}

        {order.status === "CANCELLED" && (
          <button
            // onClick={() => handlePayNow()}
            className="bg-red-400 px-4 py-1 rounded"
          >
            CANCELLED
          </button>
        )}

        {order.status === "COMPLETED" && (
          <span className="bg-green-400 px-4 py-1 rounded text-white">
            Paid
          </span>
        )}
      </div>

      <div className="p-4">
        <p className="text-lg mb-2">Date: {order.createdAt.slice(0, 10)}</p>
        <p className="text-lg mb-2">Total Amount: ₹{order.totalAmount}</p>
        <p className="text-lg mb-2">Status: {order.status}</p>
        <h2 className="text-xl font-semibold mt-4 mb-2">Items:</h2>
        {order.items.map((item) => (
          <div
            key={item.productId}
            className="flex items-center mb-4 border-b pb-2"
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
  );
};

export default OrderCard;
