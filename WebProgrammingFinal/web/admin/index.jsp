<jsp:include page="/includes/header.jsp" />


<!-- begin middle column -->

<section>

    <h1>Admin Menu</h1>

    <!-- these Form tags don't force a secure connection -->
    <form action="/adminController/displayInvoices" method="post">
       <input type="submit" value="Display Invoices" class="left_margin">
    </form>
    <form action="/adminController/displayReport" method="post">
        <input type="submit" value="Display Reports" class="left_margin">
    </form>
    <form action="inventoryControl.jsp" method="post">
        <input type="submit" value="Modify Inventory" class="left_margin">
    </form>
    <form action="/adminController/marketData" method="post">
        <input type="submit" value="Market Data" class="left_margin">
    </form>

</section>

<!-- end middle column -->

<jsp:include page="/includes/footer.jsp" />
