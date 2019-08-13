<%@ page contentType="text/html;charset=UTF-8" %>
<div>
    <table>
        <tr>
            <td id="userName"></td>
            <td>
                <label>
                    <select id="listProduct">
                        <option value="">Product</option>
                    </select>
                </label>
            </td>
            <td>
                <label>
                    <input type="number" id="quantity" placeholder="quantity"/>
                </label>
            </td>
            <td>
                <label>
                    <button id="addItem">+</button>
                </label>
            </td>
        </tr>
    </table>
</div>
<br/>
<div>
    <table style="width: 100%;" border="1" id="listItens">
        <thead>
        <tr>
            <th>product</th>
            <th>quantity</th>
            <th>price</th>
            <th>action</th>
        </tr>
        </thead>
        <tbody>
        <!--tr>
            <td style="text-align: center;">Apple</td>
            <td style="text-align: center;">
                <label>
                    <input value="1" \>
                </label>
            </td>
            <td style="text-align: center;">R$ 1</td>
            <td style="text-align: center;">
                <button>X</button>
            </td>
        </tr-->
        </tbody>
    </table>
</div>
<script type="text/javascript" src="js/page/cart/manager.js"></script>