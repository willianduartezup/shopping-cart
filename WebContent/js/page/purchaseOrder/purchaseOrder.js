function getOrder() {
    const orderId = url.findGetParameter("order");

    if (orderId) {
        orderFactory.get(orderId, function (res) {
            const order = JSON.parse(res);
            if (order) {
                document.getElementById("userName").innerHTML = "User: " + order.user.name;
                document.getElementById("order").innerHTML = "Order: " + order.number;
                document.getElementById("credit_card").innerHTML = "Paid for by credit card: "+ order.credit_card.name;
            }
        });
    } else {
        window.location.href = "index.jsp?page=cart/manager";
    }
}

function getItems() {

    const orderId = url.findGetParameter("order");

    const tableRef = document.getElementById('purchaseOrder').getElementsByTagName('tbody')[0];
    tableRef.innerHTML = '';

    const newRowNotFound = tableRef.insertRow();

    const cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No products found";

    orderFactory.get(orderId, function (res) {
        const order = JSON.parse(res);
        const list = order.cart.items;
        if (list.length > 0) {
            tableRef.innerHTML = '';
            let orderPrice = 0;
            list.map(function (item) {
                const newRow = tableRef.insertRow();

                const name = newRow.insertCell(0);
                name.style.textAlign = "center";
                name.innerHTML = item.name;

                const quantity = newRow.insertCell(1);
                quantity.style.textAlign = "center";
                quantity.innerHTML = item.quantity;

                const priceUnitProduct = newRow.insertCell(2);
                priceUnitProduct.style.textAlign = "center";
                const unitPriceToUser = item.unit_price/100;
                priceUnitProduct.innerHTML = unitPriceToUser.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});

                const totalProduct = (item.unit_price * item.quantity) / 100;

                const priceTotalProduct = newRow.insertCell(3);
                priceTotalProduct.style.textAlign = "center";
                priceTotalProduct.innerHTML = totalProduct.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});;

                orderPrice += item.unit_price * item.quantity;

            });
            const totalPrice = tableRef.insertRow();
            const totalPriceCell = totalPrice.insertCell(-1);
            totalPriceCell.style.textAlign = "right";
            totalPriceCell.colSpan = 4;
            orderPrice = orderPrice/100;
            totalPriceCell.innerHTML = orderPrice.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});;
        }
    });
}
getOrder();
getItems();