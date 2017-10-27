package com.emag.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.OrderDAO;
import com.emag.db.ProductDAO;
import com.emag.model.LineItem;
import com.emag.model.OrderPojo;
import com.emag.model.ProductPojo;
import com.emag.model.UserPojo;

@Controller
public class ShoppingController {

	@Autowired
	ServletContext application;

	private BigDecimal total = BigDecimal.ZERO;

	@RequestMapping(value = "/shopping/shop")
	public String shopping() {
		return "shopping/shop";
	}

	@RequestMapping(value = "/shopping/confirmOrder", method = RequestMethod.GET)
	public String confirmOrder(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("/shopping/confirmation/");
		return "shopping/confirmOrder";
	}

	@RequestMapping(value = "/shopping/handleOrder", method = RequestMethod.POST)
	public String handleOrder(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Handle Order");
		try {
			total = BigDecimal.ZERO;
			List<LineItem> lineItems = getAndVerifyInputs(req, res);
			int rowCount = Integer.parseInt(req.getParameter("rowCount").trim());
			if (rowCount <= 0) {
				return "index";
			}
			sendResponse(req, res, lineItems, this.total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "shopping/shop";
	}

	private List<LineItem> getAndVerifyInputs(HttpServletRequest req, HttpServletResponse res) {
		List<LineItem> lineItems = new ArrayList<LineItem>();
		System.out.println("Am I getting here.........................!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		int ind = 1;
		int rowCount = -1;
		try {
			rowCount = Integer.parseInt(req.getParameter("rowCount").trim());

			for (int i = 0; i < rowCount; i++) {
				String check = "check-" + ind;

				if (req.getParameter(check) != null) { // Has the row been
														// checked?
					int qty = Integer.parseInt(req.getParameter("num-" + ind).trim());
					int id = Integer.parseInt(req.getParameter("id-" + ind).trim());
					String product = req.getParameter("prod-" + ind);
					String category = req.getParameter("cat-" + ind);
					BigDecimal price = new BigDecimal(req.getParameter("price-" + ind).trim());
					lineItems.add(new LineItem(qty, id, product, category, price));
					if (qty > 0) {
						BigDecimal subtotal = price.multiply(new BigDecimal(qty));
						this.total = this.total.add(subtotal);
					}
				}
				ind++;
			}
			if (total.compareTo(BigDecimal.ZERO) <= 0) {
				sendErrorResponse(req, res, "No items chosen during shopping or above available quantity.");
			}
		} catch (NumberFormatException e) {
			sendErrorResponse(req, res, "Data validation errors arose during processing.");
			return null;
		}

		System.out.println(lineItems);
		return lineItems;
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse res, List<LineItem> items,
			BigDecimal totalPrice) {
		try {
			System.out.println("Send response");
			HttpSession session = req.getSession();
			session.setAttribute("items", items);
			session.setAttribute("total", totalPrice);
			res.sendRedirect("confirmOrder");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendErrorResponse(HttpServletRequest req, HttpServletResponse res, String msg) {
		try {
			HttpSession session = req.getSession();
			session.setAttribute("result", msg);
			res.sendRedirect("badResult");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "confirmOrder", method = RequestMethod.POST)
	public void orderPlacer(HttpServletRequest req, HttpServletResponse res) {
		try {
			System.out.println("Confirming order.........");
			total = BigDecimal.ZERO;
			List<LineItem> lineItems = getConfirmationInputs(req, res);
			sendResponseCo(req, res, lineItems, this.total);
			System.out.println("Order placer");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<LineItem> getConfirmationInputs(HttpServletRequest req, HttpServletResponse res) {
		List<LineItem> lineItems = new ArrayList<LineItem>();
		int ind = 1;
		int rowCount = -1;
		try {
			rowCount = Integer.parseInt(req.getParameter("rowCount").trim());
			for (int i = 0; i < rowCount; i++) {
				int qty = Integer.parseInt(req.getParameter("num-" + ind).trim());
				int id = Integer.parseInt(req.getParameter("id-" + ind).trim());
				String product = req.getParameter("prod-" + ind);
				String category = req.getParameter("cat-" + ind);
				BigDecimal price = new BigDecimal(req.getParameter("price-" + ind).trim());
				lineItems.add(new LineItem(qty, id, product, category, price));
				if (qty > 0) {
					BigDecimal subtotal = price.multiply(new BigDecimal(qty));
					this.total = this.total.add(subtotal);
				}
				ind++;
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException("wow");
		}
		return lineItems;
	}

	private void saveToDB(List<LineItem> lineItems, HttpServletRequest req, HttpServletResponse res, BigDecimal bd) {
		System.out
				.println("Trying to add to database orders..........................................................");
		System.out.println("Lineitems: " + lineItems);
		HttpSession session = req.getSession();
		TreeMap<Integer, ProductPojo> products = (TreeMap<Integer, ProductPojo>) application.getAttribute("products");
		UserPojo user = (UserPojo) session.getAttribute("user");
		System.out.println(user);
		OrderPojo order = new OrderPojo(LocalDateTime.now(), user.getCustomerID(), user.getAddress(), user.getAddress(),
				this.total, 1);

		for (int i = 0; i < lineItems.size(); i++) {
			order.getCollection().put(products.get(lineItems.get(i).getId()), lineItems.get(i).getQty());
		}

		System.out.println("Orders:" + order);

		try {
			OrderDAO.getInstance().addOrder(order);
			// if no exception is thrown it's save to empty current collection of orders
			order.getCollection().clear();
		} catch (SQLException e) {
			e.printStackTrace();
			sendErrorResponse(req, res, "SQL exception friend - BIG TIME!!!\n\n" + e.toString());
		}
		
		System.out.println(lineItems);
	}

	private String sendResponseCo(HttpServletRequest req, HttpServletResponse res, List<LineItem> items,
			BigDecimal totalPrice) {

		System.out.println("sendResponseCo");
		HttpSession session = req.getSession();
		session.setAttribute("items", items);
		session.setAttribute("total", totalPrice);
		saveToDB(items, req, res, this.total);
		return "forword:/shopping/placeOrder";
	}

	@RequestMapping(value = "shopping/placeOrder", method = RequestMethod.POST)
	public String placeOrder(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("shopping/placeOrder");
		orderPlacer(req, res);
		return "shopping/placeOrder";
	}

	@RequestMapping(value = "shopping/badResult")
	public String shoppingBadResult(HttpServletRequest req, HttpServletResponse res) {
		return "shopping/badResult";
	}

}
