<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    orders placed by user Bruno
</div>
<div>
    <table class="gridtable" border="1" id="listOrder" style="width: 100%;border-collapse: collapse;">
        <thead>
        <tr>
            <th>Order Number</th>
            <th>Date</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="text-align: center;">46783465</td>
            <td style="text-align: center;">21/08/2019</td>
            <td style="text-align: center;">R$ 200</td>
            <td style="text-align: center;">
                <a href="index.jsp?page=purchaseOrder/purchaseOrder&order=46783465">View</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="js/page/order/list.js"></script>