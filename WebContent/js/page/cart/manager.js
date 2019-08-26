// Get the modal
const modal = document.getElementById("myModal");

// Get the button that opens the modal
const btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
const span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
btn.onclick = function() {

    const user_id = url.findGetParameter("user_id");

    cartFactory.get(user_id, function(res){
        const list = JSON.parse(res);

        if (list.length > 0){
            modal.style.display = "block";
        }else{
            alert('You do not have any items in your cart.');
        }
    });
};

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
};

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
                options += "<option value='"+ product.id +"'>"+ product.name +" R$ "+ (product.price/100).toLocaleString('pt-br', { style: 'currency', currency: 'BRL' }); +"</option>";
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
                    quantity.innerHTML = "<label><input class='quantity_item' onchange='onUpdate(\""+ item.id +"\",\""+ item.product_id +"\", this.value)' type='number' required value='"+ item.quantity +"' min='1' max='25'/></label>";

                    const priceUnitProduct  = newRow.insertCell(2);
                    priceUnitProduct.style.textAlign = "center";
                    const unitPriceToUser = item.price_unit_product/100;
                    priceUnitProduct.innerHTML = unitPriceToUser.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });

                    const totalProduct = item.price_unit_product * item.quantity / 100;

                    const priceTotalProduct  = newRow.insertCell(3);
                    priceTotalProduct.style.textAlign = "center";
                    priceTotalProduct.innerHTML = totalProduct.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });

                    const actions  = newRow.insertCell(4);
                    actions.style.textAlign = "center";
                    actions.innerHTML = "<button class='remove_item' onclick='onRemove(\""+ item.id +"\")'>X</button>";

                    cartPrice += item.price_unit_product * item.quantity;
                });
            });
            const totalPrice   = tableRef.insertRow();
            const totalPriceCell = totalPrice.insertCell(-1);
            totalPriceCell.style.textAlign = "right";
            totalPriceCell.colSpan = 5;
            cartPrice = cartPrice/100;
            totalPriceCell.innerHTML = "Total price: " + cartPrice.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' });

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

function createOrder(form){
    if (confirm("Confirm buy cart?")){
        const user_id = url.findGetParameter("user_id");

        if (document.getElementById("new_credit_card").style.display === 'block'){
            const credit_card= {};

            const expArr = form.expiration_date.value.split('-');
            const expiration = expArr[1] + '' + expArr[0];

            credit_card.userId = user_id;
            credit_card.cardName = form.name.value;
            credit_card.number = form.number.value;
            credit_card.cvv = form.cvv.value;
            credit_card.expirationDate = expiration;

            creditCardFactory.create(credit_card, function (res) {
                const cr_created = JSON.parse(res);

                orderFactory.create(user_id, cr_created.id, function(res){
                    const order = JSON.parse(res);
                    window.location.href="index.jsp?page=purchaseOrder/purchaseOrder&order=" + order.id;
                });
            })
        }else{
            const idCredit = form.select_id_credit.value;

            orderFactory.create(user_id, idCredit, function(res){
                const order = JSON.parse(res);
                window.location.href="index.jsp?page=purchaseOrder/purchaseOrder&order=" + order.id;
            });
        }
    }

    return false;
}

function validExpiration(input) {
    const expArr = input.value.split('-');
    const expiration = new Date();
    expiration.setFullYear(expArr[0], expArr[1], 1);
    expiration.setHours(0,0,0);
    const time = expiration.getTime() - 1000;
    expiration.setTime(time);

    const today = new Date();

    if (expiration.getTime() < today.getTime()){
        input.setCustomValidity('expired card')
    }else{
        input.setCustomValidity('')
    }
}

function showNewCredit() {

    if (document.getElementById("new_credit_card").style.display === 'none'){
        document.getElementById("select_id_credit").required = false;

        document.getElementById("name").required = true;
        document.getElementById("expiration_date").required = true;
        document.getElementById("number").required = true;
        document.getElementById("cvv").required = true;
        document.getElementById("new_credit_card").style.display = 'block';
        document.getElementById("btnNewCredit").innerHTML = "Close Credit Card";
    }else if (document.getElementById("new_credit_card").style.display === 'block'){
        document.getElementById("select_id_credit").required = true;

        document.getElementById("name").required = false;
        document.getElementById("name").value = "";
        document.getElementById("cvv").required = false;
        document.getElementById("cvv").value = "";
        document.getElementById("expiration_date").required = false;
        document.getElementById("expiration_date").value = "";
        document.getElementById("number").required = false;
        document.getElementById("number").value = "";
        document.getElementById("new_credit_card").style.display = 'none';
        document.getElementById("btnNewCredit").innerHTML = "If your credit card is not in the options? register it by clicking here.";
    }
}

function maskNumber(input) {
    let value = input.value;

    value = value.replace(/\D/g,"");

    input.value = value;
}

function getCreditCardUser() {
    const user_id = url.findGetParameter("user_id");

    creditCardFactory.list(user_id, function (res) {
        const list = JSON.parse(res);

        if (list.length > 0){
            let options = "<option value=''>Credit Card</option>";

            list.map(function (credit) {
                options += "<option value='"+ credit.id +"'>"+ credit.cardName +" - "+ credit.number +"</option>";
            });

            document.getElementById("select_id_credit").innerHTML = options;
        }
    });
}

getUser();
getProducts();
getItens();
getCreditCardUser();