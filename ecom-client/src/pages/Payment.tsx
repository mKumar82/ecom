import { useParams } from "react-router-dom";
import { useGetOrderQuery } from "../apiServices/orderApi";
import { useInitiatePaymentMutation } from "../apiServices/paymentApi";

const Payment = () => {
  const orderId = useParams().orderId;
  const { data, isLoading } = useGetOrderQuery(orderId!);
  //   console.log("Processing payment for order ID:", orderId);
  //   console.log("Order Data:", data);

  const order = data!;

  // const navigate = useNavigate();

  const [initaitePayment] = useInitiatePaymentMutation();
  const handleConfirmPayNow = async () => {
    const payload = {
      orderId: order.id,
      totalAmount: order.totalAmount,
    };

    console.log("Confirming payment with payload:", payload);
    const res = await initaitePayment(payload).unwrap();
    console.log("Payment initiation response:", res);
    console.log("redirect url ", res.redirectUrl);
    // Simulate payment processing
    window.location.href = res.redirectUrl;
  };
  return (
    <div className="mx-auto px-4 mt-6">
      <h1 className="text-3xl font-bold mb-4">Payment Page</h1>
      {isLoading &&
        Array.from({ length: 1 }).map((_, i) => (
          <div
            key={i}
            className="mx-auto w-full px-4 mt-6 grid grid-cols-1 gap-5 h-50 bg-gray-200 animate-pulse rounded"
          />
        ))}
      {order && (
        <div className="m-3 border relative rounded-2xl">
          <h1 className="text-2xl font-bold p-4 border-b">
            Order #{order.id.slice(0, 8)}
          </h1>

          <div className="p-4 flex">
            <div className="flex-1 lg:flex-1">
              <p className="text-lg mb-2">
                Date: {order.createdAt.slice(0, 10)}
              </p>
              <p className="text-lg mb-2">Total Amount: ₹{order.totalAmount}</p>
              <p className="text-lg mb-2">Status: {order.status}</p>
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
      )}
      {order && order?.status === "RESERVED" && (
        <div className="m-10 p-4 text-xl text-center  bg-yellow-300 rounded-4xl hover:scale-105">
          <button onClick={handleConfirmPayNow}>Confirm Pay now</button>
        </div>
      )}
    </div>
  );
};

export default Payment;
