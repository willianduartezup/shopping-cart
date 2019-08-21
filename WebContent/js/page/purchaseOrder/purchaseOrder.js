
function getUser(){
    const user_id = url.findGetParameter("user_id");

    if (user_id){
        userFactory.get(user_id, function(res){
            const user = JSON.parse(res);
            if (user){
                document.getElementById("userName").innerHTML = "User: "+user.name;
            }
        });
    }else{
        window.location.href = "index.jsp?page=cart/manager";
    }
}

function getOrderId(){
    const user_id = url.findGetParameter("user_id");
    orderFactory.get(user_id, function (res) {
        const order = JSON.parse(res);
        document.getElementById("order").innerHTML = "Order: "+order.number;

    });
}

function getItems(){

    const user_id = url.findGetParameter("user_id");

    const tableRef = document.getElementById('purchaseOrder').getElementsByTagName('tbody')[0];
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
                    quantity.innerHTML = item.quantity;

                    const priceUnitProduct  = newRow.insertCell(2);
                    priceUnitProduct.style.textAlign = "center";
                    priceUnitProduct.innerHTML = "R$ "+ item.price_unit_product;

                    const totalProduct = item.price_unit_product * item.quantity;

                    const priceTotalProduct  = newRow.insertCell(3);
                    priceTotalProduct.style.textAlign = "center";
                    priceTotalProduct.innerHTML = "R$ "+ totalProduct;

                    cartPrice += item.price_unit_product * item.quantity;
                });
            });
            const totalPrice   = tableRef.insertRow();
            const totalPriceCell = totalPrice.insertCell(-1);
            totalPriceCell.style.textAlign = "right";
            totalPriceCell.colSpan = 4;
            totalPriceCell.innerHTML = "Total price: R$ " + cartPrice;
        }

    });
}

getUser();
getItems();
getOrderId();