import { CiCircleMinus, CiCirclePlus } from "react-icons/ci";
import { RiDeleteBin5Line } from "react-icons/ri";
import {
  decreaseQuantity,
  increaseQuantity,
  removeFromCart,
} from "../redux/features/cart/cartSlice";
import { useAppDispatch } from "../redux/hooks";
import type { Product } from "../apiServices/app_types";
import toast from "react-hot-toast";

type CartItemProps = {
  id: string;
  title: string;
  price: number;
  image: string;
  quantity: number;
};

const CartItem = ({ id, title, price, image, quantity }: CartItemProps) => {
  const dispatch = useAppDispatch();
  const product: Product = { id, title, price, image };

  const handleDecreaseQuantity = (product: Product) => {
    if (quantity > 1) {
      dispatch(decreaseQuantity(product));
    } else {
      dispatch(removeFromCart(product));
    }
  };
  const handleIncreaseQuantity = (product: Product) => {
    dispatch(increaseQuantity(product));
  };
  const handleRemoveFromCart = (product: Product) => {
    dispatch(removeFromCart(product));
    toast.success("Removed from cart");
  };
  return (
    <div className="m-2 p-1 outline rounded">
      <div className="flex gap-3">
        <img src={image} className="aspect-square w-1/4" />
        <div className="flex-col my-1">
          <h1 className="text-3xl">{title}</h1>
          <p className="mt-5 text-gray-900">Price: ₹{price}</p>
        </div>
      </div>
      <div className="flex justify-between m-3">
        <button
          className="transition-transform hover:scale-110 duration-300"
          onClick={() => handleDecreaseQuantity(product)}
        >
          <CiCircleMinus className="size-10" />
        </button>
        <span className="text-5xl text-center w-10">{quantity}</span>
        <button
          className="transition-transform hover:scale-110 duration-300"
          onClick={() => handleIncreaseQuantity(product)}
        >
          <CiCirclePlus className="size-10" />
        </button>

        <button
          className="transition-transform hover:scale-110 duration-300"
          onClick={() => handleRemoveFromCart(product)}
        >
          <RiDeleteBin5Line color="red" className="size-8" />
        </button>
      </div>
    </div>
  );
};

export default CartItem;
