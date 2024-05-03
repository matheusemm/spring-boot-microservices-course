document.addEventListener("alpine:init", () => {
    Alpine.data("initData", (pageNo) => ({

        pageNo: pageNo,

        products: {
            data: []
        },

        init() {
            this.loadProducts();
        },

        loadProducts() {
            $.getJSON("/api/products?page=" + this.pageNo, (resp) => {
                console.log(resp);
                this.products = resp;
            });
        },

        addToCart(product) {
            addProductToCart(product);
        }
    }));
});
