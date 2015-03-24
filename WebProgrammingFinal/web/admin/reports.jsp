<jsp:include page="/includes/header.jsp" />
<%@ page import="bookstore.business.Report" %>
<%@ page import="java.util.List" %>
<%
    HttpSession httpSession = request.getSession();
    List<Report> reports = (List<Report>)httpSession.getAttribute("newReports");
%>
<!-- begin middle column -->

<section id="admin">

    <h1>Total Sales and Profit</h1>
    <%if (reports != null) {%>
    <h2>Recent Months</h2>
    <table border="1">
        <tr>
            <th>Report Date</th>
            <th>Gross Sales</th>
            <th>Sales Difference From Previous Month</th>
            <th>Total Books Sold</th>
        </tr>
        <tr>
            <td><%= reports.get(0).getEndDate() %> - <%= reports.get(0).getStartDate()%></td> <!--Current Month Date-->
            <td>$<%= String.format("%.2f", reports.get(0).getTotalSales()) %></td> <!--Current Month Sales-->
            <td>$<%= String.format("%.2f", (reports.get(0).getTotalSales() - reports.get(1).getTotalSales()))%></td>
            <td><%= reports.get(0).getBookCount() %></td> <!--Current Month Book Count-->
        </tr>
        <tr>
            <td><%= reports.get(1).getEndDate() %> - <%= reports.get(1).getStartDate()%></td> <!--Previous Month Date-->
            <td>$<%= String.format("%.2f", reports.get(1).getTotalSales()) %></td> <!--Previous Month Sales-->
            <td>$<%= String.format("%.2f", (reports.get(1).getTotalSales() - reports.get(4).getTotalSales()))%></td>
            <td><%= reports.get(1).getBookCount() %></td> <!--Previous Month Book Count-->
        </tr>
    </table>
    <h2>Recent Weeks</h2>
    <table border="1">
        <tr>
            <th>Report Date</th>
            <th>Gross Sales</th>
            <th>Sales Difference From Previous Week</th>
            <th>Total Books Sold</th>
        </tr>
        <tr>
            <td><%= reports.get(2).getEndDate() %> - <%= reports.get(2).getStartDate()%>
            </td> <!--Current Week Date-->
            <td>$<%= String.format("%.2f", reports.get(2).getTotalSales()) %></td> <!--Current Week Sales-->
            <td>$<%= String.format("%.2f", (reports.get(2).getTotalSales() - reports.get(3).getTotalSales()))%></td>
            <td><%= reports.get(2).getBookCount() %></td>
            <!--Current Week Book Count-->
        </tr>
        <tr>
            <td><%= reports.get(3).getEndDate() %> - <%= reports.get(3).getStartDate()%>
            </td> <!--Previous Week Date-->
            <td>$<%= String.format("%.2f", reports.get(3).getTotalSales()) %></td> <!--Previous Week Sales-->
            <td>$<%= String.format("%.2f", (reports.get(3).getTotalSales() - reports.get(5).getTotalSales()))%></td>
            <td><%= reports.get(3).getBookCount() %></td> <!--Previous Week Book Count-->
        </tr>
    </table>
    <%} else {%>
        <h1>No Current Report</h1>
    <%}%>
</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />