const BOOKSTORE_STATE_KEY = "BOOKSTORE_STATE";

const getCart = function () {
    let cart = localStorage.getItem(BOOKSTORE_STATE_KEY);
    if (!cart) {
        cart = JSON.stringify({items: []});
        localStorage.setItem(BOOKSTORE_STATE_KEY, cart);
    }

    return JSON.parse(cart);
};

const addProductToCart = function (product) {
    let cart = getCart();
    let cartItem = cart.items.find(i => i.code === product.code);
    if (cartItem) {
        cartItem.quantity = cartItem.quantity + 1;
    } else {
        cart.items.push(Object.assign({}, product, {quantity: 1}));
    }

    localStorage.setItem(BOOKSTORE_STATE_KEY, JSON.stringify(cart));
    setCartItemCount();
}

const updateProductQuantity = function (code, quantity) {
    let cart = getCart();
    if (quantity < 1) {
        cart.items = cart.items.filter(i => i.code !== code);
    } else {
        let cartItem = cart.items.find(i => i.code === code);
        if (cartItem) {
            cartItem.quantity = parseInt(quantity);
        } else {
            console.log("Product with code " + code + " doesn't exist in the cart. Ignoring...");
        }
    }

    localStorage.setItem(BOOKSTORE_STATE_KEY, JSON.stringify(cart));
    setCartItemCount();
}

const deleteCart = function () {
    localStorage.removeItem(BOOKSTORE_STATE_KEY);
    setCartItemCount();
}

const setCartItemCount = function () {
    let cart = getCart();
    let count = cart.items.reduce((count, item) => count + item.quantity, 0);

    $("#cart-item-count").text("(" + count + ")");
}
