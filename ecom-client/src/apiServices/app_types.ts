export type OrderItemType = {
  productId: string;
  name: string;
  quantity: number;
  price: number;
  imageUrl: string;
};
export type OrderType = {
  id: string;
  userId: string;
  totalAmount: number;
  status: string;
  createdAt: string;
  items: OrderItemType[];
};

export type CreateOrderRequest = {
  orderItems: {
    productId: string;
    quantity: number;
  }[];
  shippingAddress?: string;
};

export type Product = {
  id: string;
  title: string;
  price: number;
  image: string;
};

export type CartItem = {
  id: string;
  title: string;
  price: number;
  image: string;
  quantity: number;
};

export type PaymentResponseType = {
  paymentId: string;
  redirectUrl: string;
};
export type PaymentRequestType = {
  orderId: string;
  totalAmount: number;
};
export type ConfirmPaymentResponseType = {
  message: string;
};
export type ConfirmPaymentRequestType = {
  paymentId: string;
  status: string;
};
export type VerifyPaymentResponseType = {
  status: string;
};
export type VerifyPaymentRequestType = {
  paymentId: string;
};
