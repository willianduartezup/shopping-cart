function loadProducts(){
    const tableRef = document.getElementById('listProducts').getElementsByTagName('tbody')[0];

    tableRef.innerHTML = "";

    const newRowNotFound   = tableRef.insertRow();

    const cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No products found";

    productFactory.list(function (res) {
        const list = JSON.parse(res);

        if (list.length > 0){
            tableRef.innerHTML = "";

            list.map(function (product) {
                const newRow   = tableRef.insertRow();

                const name  = newRow.insertCell(0);
                name.style.textAlign = "center";
                name.innerHTML = product.name;

                const price  = newRow.insertCell(1);
                price.style.textAlign = "center";
                price.innerHTML = product.price.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });

                const unity  = newRow.insertCell(2);
                unity.style.textAlign = "center";
                unity.innerHTML = product.unit;

                const quantity  = newRow.insertCell(3);
                quantity.style.textAlign = "center";
                quantity.innerHTML = product.quantity.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });

                const actions  = newRow.insertCell(4);
                actions.style.textAlign = "center";
                actions.innerHTML = "<a href=\"index.jsp?page=product/form&id="+ product.id +"\">update</a>\n";
            });
        }
    });
}

loadProducts();