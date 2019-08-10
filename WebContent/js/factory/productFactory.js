var productFactory = {};

productFactory.add = function (product, callback) {
    request.post('product', product, callback);
};