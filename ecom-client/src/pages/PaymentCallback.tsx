import { useSearchParams, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { useVerifyPaymentQuery } from "../apiServices/paymentApi";

const PaymentCallback = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const paymentId = searchParams.get("paymentId");
  // const status = searchParams.get("status");
  console.log("Payment callback:", paymentId);
  const { data } = useVerifyPaymentQuery(paymentId || "");
  const status = data?.status;
  console.log("data====:", data);
  useEffect(() => {
    if (paymentId && status) {
      // 🔥 Call backend to confirm payment
      // confirmPayment({ paymentId, status })

      if (status === "SUCCESS") {
        navigate("/my-orders");
      } else {
        navigate("/");
      }
    }
  }, [paymentId, status, navigate]);

  return (
    <div className="h-screen flex justify-center items-center">
      <h2 className="text-lg font-semibold">Processing payment...</h2>
    </div>
  );
};

export default PaymentCallback;
