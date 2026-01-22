import { useAppDispatch, useAppSelector } from "../redux/hooks";
import {
  addToCart,
  decreaseQuantity,
  increaseQuantity,
  removeFromCart,
} from "../redux/features/cart/cartSlice";
import { CiCircleMinus, CiCirclePlus } from "react-icons/ci";
import { MdAddBox } from "react-icons/md";
import toast from "react-hot-toast";

type Product = {
  id: string;
  title: string;
  price: number;
  image: string;
};

export default function ProductCard({ product }: { product: Product }) {
  const dispatch = useAppDispatch();
  // this is to get the quantity of the product in the cart
  const productQuantity = useAppSelector((state) => {
    const item = state.cart.items.find((item) => item.id === product.id);
    return item ? item.quantity : 0;
  });
  const handleAddToCart = (product: Product) => {
    dispatch(addToCart(product));
    toast.success("Added to cart");
  };
  const handleDecreaseQuantity = (product: Product) => {
      if (productQuantity > 1) {
        dispatch(decreaseQuantity(product));
      } else {
        dispatch(removeFromCart(product));
      }
  };
  const handleIncreaseQuantity = (product: Product) => {
    dispatch(increaseQuantity(product));
  };

  return (
    <div className="bg-white rounded-xl shadow-sm border hover:shadow-lg transition-transform duration-300 hover:scale-105">
      {/* Image */}
      <div className="aspect-square bg-gray-100 rounded-t-xl overflow-hidden">
        <img
          src={product.image}
          alt={product.title}
          className="w-full h-full object-cover"
        />
      </div>

      {/* Content */}
      <div className="p-4">
        <h3 className="text-lg font-medium line-clamp-2">{product.title}</h3>
        <div className="mt-2 flex items-center justify-between gap-3">
          <span className="text-3xl text-center w-22 font-semibold">
            ₹{product.price}
          </span>
          {productQuantity === 0 ? (
            <button onClick={() => handleAddToCart(product)}>
              <MdAddBox size={30} />
            </button>
          ) : (
            <div className="flex justify-between items-center gap-1">
              <button onClick={() => handleDecreaseQuantity(product)}>
                <CiCircleMinus className="size-8" />
              </button>
              <span className="text-3xl text-center w-8">
                {productQuantity}
              </span>
              <button onClick={() => handleIncreaseQuantity(product)}>
                <CiCirclePlus className="size-8" />
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
