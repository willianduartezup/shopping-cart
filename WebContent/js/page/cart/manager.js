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
    cell.colSpan = 4;
    cell.innerHTML = "No products found";

    cartFactory.get(user_id, function(res){
        const list = JSON.parse(res);

        if (list.length > 0){
            tableRef.innerHTML = '';

            list.map(function (item) {
                productFactory.get(item.product_id,function (res) {
                    const product = JSON.parse(res);

                    const newRow   = tableRef.insertRow();

                    const name  = newRow.insertCell(0);
                    name.style.textAlign = "center";
                    name.innerHTML = product.name;

                    const quantity  = newRow.insertCell(1);
                    quantity.style.textAlign = "center";
                    quantity.innerHTML = "<label><input type='number' value='"+ item.quantity +"'/></label>";

                    const price  = newRow.insertCell(2);
                    price.style.textAlign = "center";
                    price.innerHTML = "R$ "+ item.price;

                    const actions  = newRow.insertCell(3);
                    actions.style.textAlign = "center";
                    actions.innerHTML = "<button class='remove_item' onclick='onRemove(\""+ item.id +"\")'>X</button>";
                });
            });
        }
    });
}

function onSubmit(form){
    const item = {};

    item.product_id = form.product_id.value;
    item.quantity = form.quantity.value;
    productFactory.get(item.product_id, function (res) {
       const product = JSON.parse(res);

        item.price = product.price;

        cartFactory.add(item, function () {
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

getUser();
getProducts();
getItens();