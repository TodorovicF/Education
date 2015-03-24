<%@ page import="bookstore.business.Cart" %>
<%@ page import="bookstore.business.LineItem" %>
<%@ page import="bookstore.business.Product" %>
<jsp:include page="/includes/header.jsp" />
<!-- begin middle column -->

<%
    Cart cart = (Cart)session.getAttribute("cart");
%>

<section class="cart">

    <h1>Your cart</h1>
        <%
    if(cart == null || cart.getCount() == 0) {
        %>
            <p>Your cart is empty.</p>
        <%
    } else {
        %>
        <table>
            <tr>
                <th>Qty</th>
                <th>Description</th>
                <th>Price</th>
                <th>Amount</th>
                <th>&nbsp;</th>
            </tr>
            <%
                float totalAmount = 0;
                for(LineItem item : cart.getItems()) {
                    Product product = item.getProduct();
                    totalAmount += item.getTotal();
            %>
            <tr class="cart_row">
                <td>
                    <form action="/order/updateItem" method="post">
                        <input type="hidden" name="ISBN13"
                               value="<%= product.getISBN13()%>">
                        <input type=text name="quantity"
                               value="<%= item.getQuantity()%>" id="quantity">
                        <input type="image" width="18" src="/images/Button-Refresh.png" alt="Update">
                    </form>
                </td>
                <td><%= product.getDescription()%></td>
                <td><%= product.getPriceCurrencyFormat()%></td>
                <td><%= item.getTotalCurrencyFormat()%></td>
                <td>
                    <form action="/order/removeItem" method="post">
                        <input type="hidden" name="ISBN13"
                               value="<%= product.getISBN13()%>">
                        <input type="image" width="18" src="/images/Button-Remove.png" alt="Remove">
                    </form>
                </td>
            </tr>

            <%
                }
            %>
            <h3>Total Amount: $<%= totalAmount%></h3>
            <%
                Boolean cartChanged = (Boolean)session.getAttribute("cartChanged");
                if(cartChanged != null && cartChanged) {
                    %>
                    <h5>Cart has been modified to reflect current stock.</h5>
                    <%
                }
            %>
            <tr>
                <td colspan="5">
                    <p><b>To change the quantity for an item</b>, enter the new quantity
                        and click on the Update button.</p>
                    <p><b>To remove an item</b>, click on the Remove button.</p>
                </td>
                <td colspan="5">&nbsp;</td>
            </tr>
        </table>
            <%
        }
    %>

    <form action="/catalog" method="get" id="float_left">
      <input type="submit" value="Continue Shopping">
    </form>

    <%
        if(cart != null && cart.getCount() >0 )
        {
    %>
        <form action="/order/checkUser" method="post">
          <input type="submit" value="Checkout">
        </form>
    <%
        }
    %>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />
