<%@ page contentType="text/html;charset=UTF-8"%>
<form method="post" onsubmit="return onSubmit()">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name" id="name"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>price:</td>
            <td>
                <label>
                    <input type="text" name="price" id="price"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>unity:</td>
            <td>
                <label>
                    <input type="text" name="unity" id="unity"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>quantity:</td>
            <td>
                <label>
                    <input type="text" name="quantity" id="quantity"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button style="float: right" type="submit">add</button>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" src="js/page/product/form.js"></script>