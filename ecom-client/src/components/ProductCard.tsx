import { useAppDispatch, useAppSelector } from "../redux/hooks";
import {
  addToCart,
  decreaseQuantity,
  increaseQuantity,
} from "../redux/features/cart/cartSlice";
import { CiCircleMinus, CiCirclePlus } from "react-icons/ci";

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
  };
  const handleDecreaseQuantity = (product: Product) => {
    dispatch(decreaseQuantity(product));
  };
  const handleIncreaseQuantity = (product: Product) => {
    dispatch(increaseQuantity(product));
  };

  return (
    <div className="bg-white rounded-xl shadow-sm border hover:shadow-md transition">
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
        <h3 className="text-sm font-medium line-clamp-2">{product.title}</h3>
        <div className="mt-2 flex items-center justify-between">
          <span className="text-3xl font-semibold">₹{product.price}</span>
          {productQuantity === 0 ? (
            <button
              onClick={() => handleAddToCart(product)}
              className="text-xl text-blue-600 hover:underline"
            >
              Add to Cart
            </button>
          ) : (
            <div className="flex justify-between items-center gap-4">
              <button onClick={() => handleDecreaseQuantity(product)}>
                <CiCircleMinus className="size-8" />
              </button>
              <span className="text-3xl">{productQuantity}</span>
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
