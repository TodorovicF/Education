<%@ page import="bookstore.business.Product" %>
<%@ page import="bookstore.data.ProductDB" %>
<%@ page import="java.util.List" %>
<jsp:include page="/includes/header.jsp" />
<%--
  Created by IntelliJ IDEA.
  User: Christopher
  Date: 3/8/2015
  Time: 14:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Product> productList = ProductDB.selectProducts();
%>
<section>
    <%
        if(productList == null) {
    %>
            <h1>No Products in Database</h1>
    <%
        } else {
    %>
        <table class="inventory">
            <th>
                ISBN13
            </th>
            <th>
                Description
            </th>
            <th>
                Stock
            </th>
    <%
            for(Product p : productList) {
    %>
        <tr>
            <td>
                <%= p.getISBN13()%>
            </td>
            <td>
                <%= p.getDescription()%>
            </td>
            <td>
                <form action="/Inventory/updateProduct" method="post">
                    <input type="hidden" name="ISBN13" value="<%= p.getISBN13()%>">
                    <input type="text" name="stock" value="<%= p.getStock()%>">
                    <input type="image" width="18" src="/images/Button-Refresh.png" alt="Update">
                </form>
            </td>
        </tr>
    <%
            }
        }
    %>
        </table>
    <h3>Add Book</h3>
    <form class="addBook" action="/Inventory/addProduct" method="post" enctype="multipart/form-data">
        <p id="required">Required <span class="required">*</span></p>

        <label>ISBN13</label>
        <input type="text" name="ISBN13"  maxlength=13
               value="0000000000000" required>
        <em class="required">*</em><br>

        <label>ISBN10</label>
        <input type="text" name="ISBN10" value="0000000000" required>
        <em class="required">*</em><br>

        <label>Book Title</label>
        <input type="text" name="title" value="Book Title" required>
        <em class="required">*</em><br>

        <label>Author(s)</label>
        <input type="text" name="author" value="" required>
        <em class="required">*</em><br>

        <label>Category</label>
        <input type="text" name="category" value="">
        <em class="required">*</em><br>

        <label>Stock</label>
        <input type="text" name="stock" value="0" required>
        <em class="required">*</em><br>

        <label>Price</label>
        <input type="text" name="price" value="0.00" required>
        <em class="required">*</em><br>

        <label>Image(jpg)</label>
        <input type="file" name="image" required="">
        <em class="required">*</em><br>
        <input type="submit" value="Add Book">
    </form>
</section>
<jsp:include page="/includes/footer.jsp" />
