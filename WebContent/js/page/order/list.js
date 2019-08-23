function getUser(user_id) {
    userFactory.get(user_id, function (res) {
        const user = JSON.parse(res);

        document.getElementById("titleListOrder").innerHTML = "orders placed by user "+ user.name;
    })
}

function getOrders() {
    const user_id = url.findGetParameter('user_id');

    if (user_id){
        getUser(user_id);
    }else{
        document.getElementById("titleListOrder").innerHTML = "orders placed";
    }

    const table = document.getElementById('listOrder');
    const thead = table.getElementsByTagName('thead')[0];
    const tbody = table.getElementsByTagName('tbody')[0];

    const newRowNotFound = tbody.insertRow();

    let cell = newRowNotFound.insertCell(0);

    cell.style.textAlign = "center";
    cell.colSpan = 5;
    cell.innerHTML = "No orders found";

    const header = thead.insertRow();

    header.insertCell().outerHTML = "<th style='text-align: center;'>Order Number</th>";
    header.insertCell().outerHTML = "<th style='text-align: center;'>Date</th>";
    header.insertCell().outerHTML = "<th style='text-align: center;'>Total</th>";
    if (!user_id){
        header.insertCell().outerHTML = "<th style='text-align: center;'>User</th>";
    }
    header.insertCell().outerHTML = "<th style='text-align: center;'>Action</th>";

    orderFactory.list(user_id, function (res) {
        const listOrder = JSON.parse(res);

        if (listOrder.length > 0){
            tbody.innerHTML = '';

            listOrder.map(function (order) {
                const body = tbody.insertRow();

                let bNumber  = body.insertCell();
                bNumber.style.textAlign = "center";
                bNumber.innerHTML = order.number;

                let bDate  = body.insertCell();
                bDate.style.textAlign = "center";
                bDate.innerHTML = order.date;

                let bTotal  = body.insertCell();
                bTotal.style.textAlign = "center";
                const priceToUser = order.total/100;
                bTotal.innerHTML = priceToUser.toLocaleString('pt-br', {style: 'currency', currency: 'BRL'});

                if (!user_id) {
                    let bUser = body.insertCell();
                    bUser.style.textAlign = "center";
                    bUser.innerHTML = order.user;
                }

                let bActions = body.insertCell();
                bActions.style.textAlign = "center";
                bActions.innerHTML = "<a href=\"index.jsp?page=purchaseOrder/purchaseOrder&order="+ order.id +"\">View</a>";
            });
        }
    });
}

getOrders();