package bookstore.controllers;

import bookstore.data.InvoiceDB;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.data.MarketDataDB;
import bookstore.data.ReportsDB;

import bookstore.business.*;

@WebServlet(name = "AdminController", urlPatterns = {"/adminController/*"})
public class AdminController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        if (requestURI.endsWith("/displayInvoices")) {
            url = displayInvoices(request, response);
        } else if (requestURI.endsWith("/processInvoice")) {
            url = processInvoice(request, response);
        } else if (requestURI.endsWith("/displayReport")) {
            url = displayReport(request, response);
        } else if (requestURI.endsWith("/marketData")) {
            url = marketData(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        if (requestURI.endsWith("/displayInvoice")) {
            url = displayInvoice(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String marketData(HttpServletRequest request, HttpServletResponse response) {
        List<UserCategory> userCategories = MarketDataDB.topUserCategory();

        if(userCategories != null && userCategories.size() > 0) {
            HttpSession session = request.getSession();
            session.setAttribute("marketData",userCategories);

        }
        return "/admin/marketData.jsp";
    }
    private String displayInvoices(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Invoice> unprocessedInvoices = InvoiceDB.selectAllInvoices();
        
        String url;
        if (unprocessedInvoices != null) {
            if (unprocessedInvoices.size() <= 0) {
                unprocessedInvoices = null;
            }
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("invoices", unprocessedInvoices);
        url = "/admin/invoices.jsp";
        return url;
    }
    
    private String displayInvoice(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();

        String invoiceNumberString = request.getParameter("invoiceNumber");
        int invoiceNumber = Integer.parseInt(invoiceNumberString);
        List<Invoice> invoiceList = (List<Invoice>)
                session.getAttribute("invoices");

        Invoice invoice = null;
        for (Invoice i : invoiceList) {
            invoice = i;
            if (invoice.getInvoiceNumber() == invoiceNumber) {
                break;
            }
        }

        session.setAttribute("invoice", invoice);

        return "/admin/invoice.jsp";
    }

    private String processInvoice(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        Invoice invoice = (Invoice) session.getAttribute("invoice");
        InvoiceDB.update(invoice);

        return "/adminController/displayInvoices";
    }
    
    private String displayReport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Report> reports = new ArrayList<>(6);

        try {
            reports.add(0, ReportsDB.selectReport('0')); // current month
            reports.add(1, ReportsDB.selectReport('1')); // past month
            reports.add(2, ReportsDB.selectReport('2')); // current week
            reports.add(3, ReportsDB.selectReport('3')); // past week
            reports.add(3, ReportsDB.selectReport('4')); // past week
            reports.add(3, ReportsDB.selectReport('5')); // past week
        }
        catch(ParseException e){return "/admin/reports.jsp";}

        HttpSession session = request.getSession();
        session.setAttribute("newReports", reports);
        String url = "/admin/reports.jsp";
        return url;
    }
}
