function loadProducts(){
    var tableRef = document.getElementById('listProducts').getElementsByTagName('tbody')[0];

    var newRowNotFound   = tableRef.insertRow();

    var cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No products found";

    productFactory.list(function (res) {
        var list = JSON.parse(res);

        if (list.length > 0){
            tableRef.deleteRow(0);

            list.map(function (product) {
                var newRow   = tableRef.insertRow();

                var name  = newRow.insertCell(0);
                name.style.textAlign = "center";
                name.innerHTML = product.name;

                var price  = newRow.insertCell(1);
                price.style.textAlign = "center";
                price.innerHTML = product.price;

                var unity  = newRow.insertCell(2);
                unity.style.textAlign = "center";
                unity.innerHTML = product.unity;

                var quantity  = newRow.insertCell(3);
                quantity.style.textAlign = "center";
                quantity.innerHTML = product.quantity;

                var actions  = newRow.insertCell(4);
                actions.style.textAlign = "center";
                actions.innerHTML = "<a href=\"index.jsp?page=product/form&id="+ product.id +"\">update</a>\n"/* +
                    "<a href=\"index.jsp?page=product/list\">remove</a>"*/;
            });
        }
    });
}

loadProducts();