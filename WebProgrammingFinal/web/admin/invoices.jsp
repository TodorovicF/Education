<%@ page import="bookstore.business.Invoice" %>
<%@ page import="java.util.List" %>
<jsp:include page="/includes/header.jsp" />
<%
    HttpSession httpSession = request.getSession();
    List<Invoice> unprocessedInvoices = (List<Invoice>)httpSession.getAttribute("invoices");
%>
<!-- begin middle column -->

<section>

<h1>Invoices to be processed:</h1>

    <%
        if(unprocessedInvoices == null) {
    %>
        <p>There are no invoices to process.</p>
    <%
        } else {
    %>
        <table>

        <tr>
            <th></th>
            <th>Customer Name</th>
            <th>Invoice Total</th>
            <th>Invoice Date</th>
        </tr>

        <%
            for( Invoice invoice : unprocessedInvoices) {
        %>
        <tr>
          <td>
            <a href="displayInvoice?invoiceNumber=<%= invoice.getInvoiceNumber()%>">Click to View</a>
          </td>
          <td><%= invoice.getUser().getFirstName()%>  <%=invoice.getUser().getLastName()%></td>
            <td><%= invoice.getInvoiceTotalCurrencyFormat()%></td>
          <td><%= invoice.getInvoiceDateDefaultFormat()%></td>
        </tr>
        <%
            }
        %>
        </table>

        <form action="/admin" method="post">
            <input type=submit value="Go Back to Menu">
        </form>
    <%
        }
    %>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />