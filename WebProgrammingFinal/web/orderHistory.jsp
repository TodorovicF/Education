<%@ page import="bookstore.business.Invoice" %>
<%@ page import="java.util.List" %>
<%@ page import="bookstore.business.LineItem" %>
<%--
  Created by IntelliJ IDEA.
  User: v-ctran
  Date: 3/12/2015
  Time: 7:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./includes/header.jsp"/>
<%
    List<Invoice> invoiceList = (List<Invoice>)session.getAttribute("userInvoices");
%>
<section>
<%
    if(invoiceList == null || invoiceList.size() < 1) {
        %>
        <h3>No Order History On record</h3>
        <%
    } else {
        for(Invoice invoice : invoiceList) {
        %>
            <h4><%= invoice.getInvoiceDateDefaultFormat()%></h4>
            <p>Total: <%= invoice.getInvoiceTotalCurrencyFormat()%></p>
            <table>
                <th>
                    Book Title
                </th>
                <th>
                    Cost
                </th>
                <th>
                    Quantity
                </th>
                <th>
                    Total
                </th>

            <%
                for(LineItem item : invoice.getLineItems()) {
                    %>
                    <tr>
                        <td>
                            <%= item.getProduct().getDescription()%>
                        </td>
                        <td>
                            <%= item.getProduct().getPriceCurrencyFormat()%>
                        </td>
                        <td>
                            <%= item.getQuantity()%>
                        </td>
                        <td>
                            <%= item.getTotalCurrencyFormat()%>
                        </td>
                    </tr>
                    <%
                }
            %>
            </table>
        <%
        }
    }
%>
</section>
<jsp:include page="./includes/footer.jsp"/>