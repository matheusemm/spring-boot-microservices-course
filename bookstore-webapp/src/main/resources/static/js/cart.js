document.addEventListener("alpine:init", () => {
    Alpine.data("initData", () => ({

        cart: {items: [], totalAmount: 0.0},

        orderForm: {
            customer: {
                name: "Matheus Moreira",
                email: "matheus.moreira@example.com",
                phone: "+12 345 67890"
            },
            deliveryAddress: {
                line1: "Street 10",
                line2: "2nd floor",
                city: "City",
                state: "State",
                zipCode: "12345",
                country: "Germany"
            }
        },

        init() {
            setCartItemCount();
            this.loadCart();
        },

        loadCart() {
            this.cart = Object.assign({}, getCart(), {totalAmount: 0.0});
            this.cart.totalAmount = this.cart.items.reduce((total, item) => total + (item.price * item.quantity), 0);
        },

        removeCart() {
            deleteCart();
        },

        updateItemQuantity(code, quantity) {
            updateProductQuantity(code, quantity);
            this.loadCart();
        },

        createOrder() {
            let order = Object.assign({}, this.orderForm, {items: this.cart.items});
            console.log(order);

            $.ajax({
               url: "http://localhost:8989/order/api/orders",
               type: "POST",
               dataType: "json",
               contentType: "application/json",
               data: JSON.stringify(order),
               success: (resp) => {
                   console.log("Order Resp");
                   console.log(resp);

                   this.removeCart();
                   // alert("Order placed successfully!")
                   window.location = "/orders/" + resp.orderNumber;
               },
               error: (err) => {
                   console.log("Order creation error: " + err);
                   alert("Order creation failed!");
               }
            });
        }
    }));
});
