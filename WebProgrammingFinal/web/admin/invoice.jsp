<%@ page import="bookstore.business.Invoice" %>
<%@ page import="bookstore.business.LineItem" %>
<jsp:include page="/includes/header.jsp" />

<!-- begin middle column -->
<%
    Invoice invoice = (Invoice)session.getAttribute("invoice");
%>

<section id="admin">

<h1>Your invoice</h1>

<table>
  <tr>
    <td><b>Date</b></td>
    <td><%= invoice.getInvoiceDate()%></td>
    <td></td>
  </tr>
  <tr>
    <td><b>Ship To</b></td>
    <td><%= invoice.getUser().getAddressHTMLFormat()%></td>
    <td></td>
  </tr>
  <tr><td colspan="3"><hr></td></tr>
  <tr>
    <td><b>Qty</b></td>
    <td><b>Description</b></td>
    <td><b>Price</b></td>
  </tr>

   <%
       for(LineItem item : invoice.getLineItems()) {
            %>
            <tr>
                <td>
                    <%= item.getQuantity()%>
                </td>
                <td>
                    <%= item.getProduct().getDescription()%>
                </td>
                <td>
                    <%= item.getTotalCurrencyFormat()%>
                </td>
            </tr>
            <%
       }
   %>

  <tr><td colspan="3"><hr></td></tr>
  <tr>
    <td><b>Total</b></td>
    <td></td>
    <td><p><%=invoice.getInvoiceTotalCurrencyFormat()%></td>
  </tr>      
</table>

<label>Email Address</label>
<span><%= invoice.getUser().getEmail()%></span><br>

<form action="displayInvoices" method="post">
  <input type="submit" value="View Invoices">
</form>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />
