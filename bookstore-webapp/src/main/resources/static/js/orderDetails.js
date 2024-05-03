document.addEventListener("alpine:init", () => {
   Alpine.data("initData", (orderNumber) => ({

       orderNumber: orderNumber,

       orderDetails: {
           items: [],
           customer: {},
           deliveryAddress: {}
       },

       init() {
           setCartItemCount();
           this.getOrderDetails();
       },

       getOrderDetails() {
           $.getJSON("http://localhost:8989/order/api/orders/" + this.orderNumber, (data) => {
               this.orderDetails = data;
           })
       }
   }));
});
