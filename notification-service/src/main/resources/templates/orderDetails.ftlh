<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
    table, th, td {
      border: 1px solid black;
    }
    </style>
</head>

<body>
    <h4>${message}</h4>
    <table style="width:50%">
        <tr>
            <td>ean</td>
            <td>quantity</td>
            <td>price</td>
        </tr>
        <#foreach orderEntry in order.entries>
        <tr>
            <td>${orderEntry.productEan}</td>
            <td>${orderEntry.quantity}</td>
            <td>${orderEntry.price}</td>
        </tr>
        </#foreach>
        <tr>
            Total Price: ${order.totalPrice}
        </tr>
        <tr>
        <#if order.deliveryCost??>
            Delivery cost: ${order.deliveryCost}
        </tr>
        </#if>
        <tr>
        <#if order.totalAmount??>
            Total amount: ${order.totalAmount}
        </tr>
        </#if>
    </table>
</body>
</html>