import { useSearchParams } from "react-router-dom";
import { useConfirmPaymentMutation } from "../apiServices/paymentApi";
import toast from "react-hot-toast";

const DummyGateway = () => {
  const [searchParams] = useSearchParams();

  const [confirmPayment] = useConfirmPaymentMutation();

  const paymentId = searchParams.get("paymentId");
  const callbackUrl = searchParams.get("callbackUrl");

  const handleSuccess = async (paymentId: string, callbackUrl: string) => {
    const message = await confirmPayment({ paymentId, status: "SUCCESS" });
    console.log("messagec:::::::::", message);
    
    const redirect = `${callbackUrl}?paymentId=${paymentId}&status=SUCCESS`;

    window.location.replace(redirect);
  };
  const handleFailure = async (paymentId: string, callbackUrl: string) => {
    const message = await confirmPayment({ paymentId, status: "FAILED" });
    console.log("messagec:::::::::", message);
    
    const redirect = `${callbackUrl}?paymentId=${paymentId}&status=FAILED`;

    window.location.replace(redirect);
  };
  return (
    <div className="h-screen flex flex-col items-center justify-center gap-4">
      <h1 className="text-xl font-bold">Dummy Payment Gateway</h1>

      <p>Payment ID: {paymentId}</p>

      <button
        className="bg-green-600 text-white px-6 py-2 rounded hover:scale-105"
        onClick={() => handleSuccess(paymentId!, callbackUrl!)}
      >
        Pay Success
      </button>

      <button
        className="bg-red-600 text-white px-6 py-2 rounded hover:scale-105"
        onClick={() => handleFailure(paymentId!, callbackUrl!)}
      >
        Pay Failed
      </button>
    </div>
  );
};

export default DummyGateway;
