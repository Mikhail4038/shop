<html lang="en">
<head>
    <meta charset="UTF-8">
    <style>
    table, th, td, br {
      border: 2px solid black;
    }
    </style>
</head>

<body>
    <h4>${message}</h4>
    <table style="width:50%">
        <tr>
            <td>ean</td>
            <td>name</td>
            <td>price</td>
            <td>quantity</td>
        </tr>
        <#foreach orderEntry in order.entries>
        <tr>
            <td>${orderEntry.product.ean}</td>
            <td>${orderEntry.product.name}</td>
            <td>${orderEntry.product.price.value}</td>
            <td>${orderEntry.quantity}</td>
        </tr>
        </#foreach>
    </table>
    <h4><br>
        Total price: ${order.totalPrice}
     </br>
     <br>
        Delivery cost: ${order.deliveryCost}
     </br>
     <br>
        Total amount: ${order.totalAmount}
     </br></h4>
     <br>
        Delivery address: ${order.deliveryAddress}
     </br>
</body>
</html>