function onSubmit(form) {

    try {
        let product = {};
        product.name = form.name.value;
        product.price = form.price.value;
        product.unity = form.unity.value;
        product.quantity = form.quantity.value;

        productFactory.add(product);
        return false;
    }catch (e) {
        console.log(e)
        return false;
    }
}