function getProduct(){
    var id = url.findGetParameter('id');

    if (id){
        productFactory.get(id, function (res) {
            var product = JSON.parse(res);

            document.getElementById("name").value = product.name;
            document.getElementById("price").value = product.price;
            document.getElementById("unity").value = product.unity;
            document.getElementById("quantity").value = product.quantity;
        });
    }
}

function onSubmit(form) {

    var product = {};
    product.name = form.name.value;
    product.price = form.price.value;
    product.unity = form.unity.value;
    product.quantity = form.quantity.value;

    productFactory.add(product);
    return false;
}

getProduct();