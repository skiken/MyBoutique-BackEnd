<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Java Techie Mail</title>
</head>

<body>
<div align="center" >
<img  src="https://image.freepik.com/vecteurs-libre/fourgonnette-camion-conduite-route-colis-express-service-livraison-concept-paysage-vector-illustration_48369-25636.jpg"  height="100" width="100%"/>
</div>
<table  width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td bgcolor="red" style="background-color: red;" >
	<p align="center" style="font-size:20px; color:white;">Merci pour votre commande </p>
	</td>
</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td  align="center" valign="top" color="black" style="background-color: black;">
		<br>
		<div  style="font-size: 12px; color:white;">
		<span>Bonjour ${customer.firstName}, </span>
        <br><span>Nous avons terminé de traiter votre commande.</span>
		</div>
		<p style="font-size: 12px; color:lightsalmon;font-family: Arial, Helvetica, sans-serif;">[Commande n° ${order.id}] (${order.shipped})</p>

		<table  border="1" width="90%" style="font-size: 12px; color:white;">
        <tr>
         <td width="20%" ><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Nom produit</p></td>
         <td width="20%"><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Quantité commandée</p></td>
         <td ><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Prix unité</p></td>

        </tr>
        <#list mapProductQuantity?keys as key>
        <tr>
         <td>
                ${key.name}

          </td>
            <td>

                 ${mapProductQuantity?values[key_index]}
            </td>
          <td>
                 ${key.price} Dt

          </td>
           </#list>
        </tr>
        </table>
        <table  border="1" width="90%" style="font-size: 12px; color:floralwhite;">
        <tr>
        <td width="52.5%"> <p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Sous-total:</p> </td>
        <td>${order.totalPrice} Dt</td>
        </tr>
        <tr>
        <td><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Frais de livraison: </p></td>
        <td>7 Dt</td>
         </tr>
          <tr>
           <td><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Moyen de paiement:</p> </td>
           <td>paiement à la livraison </td>
            </tr>
            <tr>
            <td><p style="color:blue ;font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">Total:</p> </td>
            <td>${totalToPay} Dt</td>
            </tr>
        </table>
        <br>

        <p style="font-size: 12px; color:lightsalmon;font-family: Arial, Helvetica, sans-serif;">Adresse de facturation :</p>

        <table border="1" width="90%">
        <tr>
        <td>
         <div style="font-size: 12px; color:white;">
          <span> Nom : ${customer.lastName} ${customer.firstName}</span>
          <br><span> Adresse: ${customer.address.address}</span>
          <br><span> Region : ${customer.address.city}</span>
          <br><span> Code Postal: ${customer.address.postcode}</span>
          <br><span> Telephone : ${customer.telephone}</span>
          <br><span> Email : ${customer.email}</span>
          </div>
        </td>
        </tr>
        </table>
        <br>
        <p style="font-size: 12px; color:white;" > Merci pour votre achat. <p>
        </td>
		</td>
		</tr>
</table>
<br>
<div align="center">
<span > Aziz-Market-PLace: Achat sur Internet à prix discount </span>
<br><span >(+216)71123567</span>
</div>

</body>
</html>