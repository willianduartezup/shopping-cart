function getUser(){
    const user_id = url.findGetParameter("user_id");

    if (user_id){
        userFactory.get(user_id, function(res){
            const user = JSON.parse(res);

            if (user){
                document.getElementById("userName").innerHTML = user.name+ ':';
            }
        });
    }else{
        window.location.href = "index.jsp?page=user/usersList";
    }
}

function getProducts(){
    productFactory.list(function (res) {
        const list = JSON.parse(res);

        if (list.length > 0){
            let options = "<option value=''>Product</option>";

            list.map(function (product) {
                options += "<option value='"+ product.id +"'>"+ product.name +" R$ "+ product.price +"</option>";
            });

            document.getElementById("listProduct").innerHTML = options;
        }
    });
}

function getItens(){
    const user_id = url.findGetParameter("user_id");

    const tableRef = document.getElementById('listItens').getElementsByTagName('tbody')[0];
    tableRef.innerHTML = '';

    const newRowNotFound   = tableRef.insertRow();

    const cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No products found";

    cartFactory.get(user_id, function(res){
        const list = JSON.parse(res);

        if (list.length > 0){
            tableRef.innerHTML = '';
            let i = 0;
            let cartPrice = 0;
            list.map(function (item) {
                productFactory.get(item.product_id,function (res) {
                    const product = JSON.parse(res);

                    const newRow   = tableRef.insertRow();

                    const name  = newRow.insertCell(0);
                    name.style.textAlign = "center";
                    name.innerHTML = product.name;

                    const quantity  = newRow.insertCell(1);
                    quantity.style.textAlign = "center";
                    quantity.innerHTML = "<label><input class='quantity_item' onchange='onUpdate(\""+ item.id +"\",\""+ item.product_id +"\", this.value)' type='number' min=\"1\" required value='"+ item.quantity +"'/></label>";

                    const priceUnitProduct  = newRow.insertCell(2);
                    priceUnitProduct.style.textAlign = "center";
                    priceUnitProduct.innerHTML = "R$ "+ item.price_unit_product;

                    const totalProduct = item.price_unit_product * item.quantity;

                    const priceTotalProduct  = newRow.insertCell(3);
                    priceTotalProduct.style.textAlign = "center";
                    priceTotalProduct.innerHTML = "R$ "+ totalProduct;

                    const actions  = newRow.insertCell(4);
                    actions.style.textAlign = "center";
                    actions.innerHTML = "<button class='remove_item' onclick='onRemove(\""+ item.id +"\")'>X</button>";
                    i++;

                    cartPrice += item.price_unit_product * item.quantity;
                    if(list.length === i){
                        const totalPrice   = tableRef.insertRow();
                        const totalPriceCell = totalPrice.insertCell(-1);
                        totalPriceCell.style.textAlign = "right";
                        totalPriceCell.colSpan = 5;
                        totalPriceCell.innerHTML = "Total price: R$ " + cartPrice;

                       

                    }
                });
            });
        }
    });
}

function onSubmit(form){
    const user_id = url.findGetParameter("user_id");
    const item = {};

    item.product_id = form.product_id.value;
    item.quantity = form.quantity.value;
    productFactory.get(item.product_id, function (res) {
       const product = JSON.parse(res);

        item.price_unit_product = product.price;

        cartFactory.add(user_id, item, function () {
            document.getElementById("cartForm").reset();
            getItens();
        });
    });

    return false;
}

function onRemove(id){
    if (confirm("confirm remove this item?")){
        cartFactory.removeItem(id, function () {
            getItens();
        });
    }
}

function onUpdate(id, product_id, quantity){
    productFactory.get(product_id, function (res) {
        const product = JSON.parse(res);

        const item = {};
        item.id = id;
        item.product_id = product_id;
        item.quantity = quantity;
        item.price_unit_product = product.price;

        cartFactory.update(item, function () {
            getItens();
        });
    });
}

function createOrder(){
    if (confirm("Confirm buy cart?")){
        const user_id = url.findGetParameter("user_id");

        orderFactory.create(user_id, function(res){
            const order = JSON.parse(res);
            window.location.href="index.jsp?page=purchaseOrder/purchaseOrder&order=" + order.id;
        });
    }
}

getUser();
getProducts();
getItens();