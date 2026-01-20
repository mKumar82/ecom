import { CiCircleMinus, CiCirclePlus } from "react-icons/ci";
import { RiDeleteBin5Line } from "react-icons/ri";
import { decreaseQuantity, increaseQuantity, removeFromCart } from "../redux/features/cart/cartSlice";
import { useAppDispatch } from "../redux/hooks";
import type { Product } from "../apiServices/app_types";

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
      dispatch(decreaseQuantity(product));
    };
    const handleIncreaseQuantity = (product: Product) => {
      dispatch(increaseQuantity(product));
    };
    const handleRemoveFromCart = (product: Product) => {
      dispatch(removeFromCart(product));
    };
  return (
    <div className="m-2 p-1 outline rounded">
      <div className="flex gap-3">
        <img src={image} className="aspect-square w-1/2" />
        <div className="flex-col my-1">
          <h3>{title}</h3>
          <p className="mt-8">Price: ${price}</p>
        </div>
      </div>
      <div className="flex justify-between m-3">
        <button onClick={() => handleDecreaseQuantity(product)} className="">
          <CiCircleMinus className="size-10" />
        </button>
        <span className="text-5xl">{quantity}</span>
        <button onClick={() => handleIncreaseQuantity(product)}>
          <CiCirclePlus className="size-10" />
        </button>

        <button onClick={() => handleRemoveFromCart(product)}>
          <RiDeleteBin5Line color="red" className="size-8" />
        </button>
      </div>
    </div>
  );
};

export default CartItem;
