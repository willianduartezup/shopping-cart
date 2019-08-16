<%@ page contentType="text/html;charset=UTF-8" %>

<div>
    <form onsubmit="return onSubmit(this)">
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
                        <input style="font-family:sans-serif;margin: 5px;width:65px; height: 25px" name="quantity" type="number" id="quantity" placeholder="quantity" min="1" required/>
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
            <th>product</th>
            <th>quantity</th>
            <th>price unit product</th>
            <th>action</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
<script type="text/javascript" src="js/page/cart/manager.js"></script>