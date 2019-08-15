<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <form onsubmit="return onSubmit(this)">
        <table>
            <tr>
                <td id="userName"></td>
                <td>
                    <label>
                        <select name="product_id" id="listProduct" required>
                            <option value="">Product</option>
                        </select>
                    </label>
                </td>
                <td>
                    <label>
                        <input name="quantity" type="number" id="quantity" placeholder="quantity" min="1" required/>
                    </label>
                </td>
                <td>
                    <label>
                        <button type="submit" id="addItem">+</button>
                    </label>
                </td>
            </tr>
        </table>
    </form>
</div>
<br/>
<div>
    <table style="width: 100%;" border="1" id="listItens">
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