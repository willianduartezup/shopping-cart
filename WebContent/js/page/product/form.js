function getProduct(){
    const id = url.findGetParameter('id');

    if (id){
        productFactory.get(id, function (res) {
            const product = JSON.parse(res);

            document.getElementById("name").value = product.name;
            document.getElementById("price").value = product.price;
            document.getElementById("unit").value = product.unit;
            document.getElementById("quantity").value = product.quantity;
        });
    }
}

function onSubmit(form) {

    try {
        const id = url.findGetParameter('id');

        let product = {};
        product.name = form.name.value;
        product.price = form.price.value;
        product.unit = form.unit.value;
        product.quantity = form.quantity.value;

        if (id){
            product.id = id;
            productFactory.update(product, function () {
                alert('product updated success');
            });
        }else{
            productFactory.add(product, function () {
                alert('product created success');
            });
        }

        return false;
    }catch (e) {
        console.log(e);
        return false;
    }
}

getProduct();