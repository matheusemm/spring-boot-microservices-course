document.addEventListener("alpine:init", () => {
   Alpine.data("initData", () => ({

       orders: [],

       init() {
           this.loadOrders();
           setCartItemCount();
       },

       loadOrders() {
           $.getJSON("/api/orders", (data) => {
               this.orders = data;
           });
       }
   }));
});
