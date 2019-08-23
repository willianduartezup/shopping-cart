<%@ page contentType="text/html;charset=UTF-8"%>
<form id="productForm" method="post" onsubmit="return onSubmit(this)">
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <label>
                    <input  type="text" name="name" id="name" required/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Price:</td>
            <td>
                <label>
                    <input type="number" name="price" id="price" required/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Unit:</td>
            <td>
                <label>
                    <select id="unit" style="font-family:sans-serif;margin-bottom: 5px;width:181px; height: 25px" name="product_id" id="listProduct" required>
                        <option value="">Select product unit</option>
                        <option value="Piece">Piece</option>
                        <option value="Pair">Pair</option>
                        <option value="Kilogram">Kilogram</option>
                        <option value="Gram">Gram</option>
                        <option value="Box">Box</option>
                        <option value="m³">m³</option>
                    </select>
                </label>

            </td>
        </tr>
        <tr>
            <td>Quantity:</td>
            <td>
                <label>
                    <input type="number" name="quantity" id="quantity" required/>
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