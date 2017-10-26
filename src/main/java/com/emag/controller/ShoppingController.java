package com.emag.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.LineItem;

@Controller
public class ShoppingController {

	private BigDecimal total = BigDecimal.ZERO;

	@RequestMapping(value = "/shopping/shop", method = RequestMethod.POST)
	public String shopping() {
		return "/shopping/shop";
	}

	@RequestMapping(value = "handleOrder", method = RequestMethod.POST)
	public void handleOrder(HttpServletRequest req, HttpServletResponse res) {

		try {
			total = BigDecimal.ZERO;
			List<LineItem> lineItems = getAndVerifyInputs(req, res);
			sendResponse(req, res, lineItems, this.total);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private List<LineItem> getAndVerifyInputs(HttpServletRequest req, HttpServletResponse res) {
		List<LineItem> lineItems = new ArrayList<LineItem>();
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
		} catch (NumberFormatException e) {
			sendErrorResponse(req, res, "Data validation errors arose during processing.");
			return null;
		}
		return lineItems;
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse res, List<LineItem> items,
			BigDecimal totalPrice) {
		try {
			HttpSession session = req.getSession();
			session.setAttribute("items", items);
			session.setAttribute("total", totalPrice);
			res.sendRedirect("confirmOrder");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sendErrorResponse(HttpServletRequest req, HttpServletResponse res, String msg) {
		// TODO
	}

	@RequestMapping(value = "confirmOrder", method = RequestMethod.POST)
	public void orderPlacer(HttpServletRequest req, HttpServletResponse res) {

		try {
			total = BigDecimal.ZERO;
			List<LineItem> lineItems = getConfirmationInputs(req, res);
			saveToDB(lineItems);
			sendResponseCo(req, res, lineItems, this.total);
		} catch (Exception e) {

			// TODO
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

	private void saveToDB(List<LineItem> lineItems) {
		// TODO
	}

	private void sendResponseCo(HttpServletRequest req, HttpServletResponse res, List<LineItem> items,
			BigDecimal totalPrice) {
		try {
			HttpSession session = req.getSession();
			session.setAttribute("items", items);
			session.setAttribute("total", totalPrice);
			res.sendRedirect("placeOrder");
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		}
	}
}
