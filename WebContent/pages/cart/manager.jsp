<%@ page contentType="text/html;charset=UTF-8" %>
<style type="text/css">
    /* The Modal (background) */
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    /* Modal Content/Box */
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto; /* 15% from the top and centered */
        padding: 20px;
        border: 1px solid #888;
        width: 80%; /* Could be more or less, depending on screen size */
    }

    /* The Close Button */
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }
</style>

<div>
    <form id="cartForm" onsubmit="return onSubmit(this)">
        <table class="gridtable">
            <tr>
                <td id="userName"></td>
                <td>
                    <label>
                        <select style="font-family:sans-serif;margin-bottom: 5px;width:150px; height: 25px" name="product_id" id="listProduct" required>
                            <option value="">Product</option>
                        </select>
                    </label>
                </td>
                <td>
                    <label>
                        <input style="font-family:sans-serif;margin-bottom: 5px;width:65px; height: 25px" name="quantity" type="number" id="quantity" placeholder="quantity" min="1" max="100000" required/>
                    </label>
                </td>
                <td>
                    <label>
                        <button style="font-family:sans-serif;margin-bottom: 5px;width:150px; height: 25px" type="submit" id="addItem">Add item</button>
                    </label>
                </td>
            </tr>
        </table>
    </form>
</div>
<br/>
<div>
    <table class="gridtable" border="1" style="width: 100%;border-collapse: collapse;" id="listItens">
        <thead>
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Unit price</th>
            <th>Total product price</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
<div>
    <button style="font-family:sans-serif;margin-top: 5px;width:150px; height: 25px; float: right;" id="myBtn">Buy now</button>
</div>

<!-- The Modal -->
<div id="myModal" class="modal">

    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <form onsubmit="return createOrder(this);">
            <div>
                <table style="width: 100%;">
                    <tr>
                        <td colspan="2" style="width: 100%;">
                            <label>
                                <select id="select_id_credit" style="width: 100%;" required>
                                    <option value="">Credit Card</option>
                                </select>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 100%;">
                            <label>
                                <button type="button" id="btnNewCredit" onclick="showNewCredit();" style="width: 100%;">If your credit card is not in the options? register it by clicking here.</button>
                            </label>
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
            <div id="new_credit_card" style="display: none;">
                <table style="width: 100%;">
                    <tr>
                        <td colspan="3" style="width: 100%;">
                            <label>
                                <input style="width: 100%;" type="text" id="name" name="name" placeholder="Name"/>
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 45%;">
                            <label>
                                <input type="number" style="width: 99%;" id="number" name="number" placeholder="Number"/>
                            </label>
                        </td>
                        <td style="width: 5%;">
                            <label>
                                <input type="text" onkeyup="maskNumber(this)" pattern="[0-9]{3}" maxlength="3" style="width: 90%;" id="cvv" name="cvv" placeholder="CVV"/>
                            </label>
                        </td>
                        <td style="width: 45%;">
                            <label>
                                <!--input type="text" style="width: 100%;" onkeyup="maskExpiration(this)" onchange="validExpiration(this)" pattern="\d{1,2}/\d{4}" id="expiration_date" name="expiration_date" placeholder="Expiration Date"/-->
                                <input type="month" style="width: 100%;" onchange="validExpiration(this)" id="expiration_date" name="expiration_date" placeholder="Expiration Date" />
                            </label>
                        </td>
                    </tr>
                </table>
            </div>
            <br/>
            <div>
                <button type="submit" style="width: 100%;">Pay</button>
            </div>
        </form>
    </div>

</div>
<script type="text/javascript" src="js/page/cart/manager.js"></script>