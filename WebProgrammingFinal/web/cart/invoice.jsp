<%@ page import="bookstore.business.Invoice" %>
<%@ page import="bookstore.business.User" %>
<%@ page import="bookstore.business.LineItem" %>
<jsp:include page="/includes/header.jsp" />
<%
    HttpSession httpSession = request.getSession();
    Invoice invoice = (Invoice)httpSession.getAttribute("invoice");
    User user = (User)httpSession.getAttribute("user");
%>

<section class="cart">

<h1>Your invoice</h1>

<table>
  <tr>
    <th>Date</th>
    <td><%= invoice.getInvoiceDateDefaultFormat()%></td>
    <td></td>
  </tr>
  <tr>
      <th class="top">Ship To</th>
    <td><%= user.getAddressHTMLFormat()%></td>
    <td></td>
  </tr>
  <tr>
      <td colspan="3"><hr></td>
  </tr>
  <tr>
      <th>Qty</th>
      <th>Description</th>
      <th>Price</th>
  </tr>

    <%
        for(LineItem item : invoice.getLineItems())
        {
    %>

  <tr>
    <td><%=item.getQuantity()%></td>
    <td><%= item.getProduct().getDescription()%></td>
    <td><%= item.getTotalCurrencyFormat()%></td>
  </tr>

    <%
        }
    %>
  <tr>
    <th>Total:</th>
    <td></td>
    <td><%= invoice.getInvoiceTotalCurrencyFormat()%></td>
  </tr>
</table>
<form action="/order/completeOrder" method="post">
    <input type="submit" value="Submit Order">
</form>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />
