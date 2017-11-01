package com.emag.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ProductDAO;
import com.emag.model.ProductPojo;

@Controller

public class AdminController {

	private static final int MinLength = 3;

	@RequestMapping(value = "/admin/productManagement", method = RequestMethod.GET)
	public String admin() {
		return "admin/productManagement";
	}

	@RequestMapping(value = "/admin/show?id=${item.productID}", method = RequestMethod.GET)
	public String getShow(Model m, @PathVariable("productID") Integer id) {
		m.addAttribute("productID", id);
		return "admin/show";
	}

	@RequestMapping(value = "/admin/show", method = RequestMethod.GET)
	public String getShowIndeed() {
		return "admin/show";
	}

	@RequestMapping(value = "/admin/edit?id=${item.productID}", method = RequestMethod.GET)
	public String getEdit(Model m, @PathVariable("productID") Integer id) {
		m.addAttribute("productID", id);
		return "admin/edit";
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public String getEditIndeed() {
		return "admin/edit";
	}

	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
	public String getCreate() {
		return "/admin/create";
	}

	@RequestMapping(value = "/admin/goodResult", method = RequestMethod.GET)
	public String goodResult() {
		return "/admin/goodResult";
	}

	@RequestMapping(value = "/admin/badResult", method = RequestMethod.GET)
	public String badResult() {
		return "/admin/badResult";
	}

	@RequestMapping(value = "/dataVerifier", method = RequestMethod.POST)
	public void verifyUserInputsAndUpdateDB(HttpServletRequest req, HttpServletResponse res) {

		System.out.println("verifying user.");
		String productId = req.getParameter("id");
		String productName = req.getParameter("name");
		String price = req.getParameter("price");
		String description = req.getParameter("description");
		String categoryId = req.getParameter("categoryId");
		String brandId = req.getParameter("brandId");
		String availability = req.getParameter("availability");
		String imageUrl = req.getParameter("imageUrl");
		String discountPercent = req.getParameter("discountPercent");
		// data verification checks
		if (productName == null || price == null || description == null || categoryId == null || brandId == null
				|| availability == null || imageUrl == null) { // bad
			// inputs?

			System.out.println("Product id is: " + productId);
			System.out.println(productName);
			System.out.println(price);
			System.out.println(description);
			System.out.println(categoryId);
			System.out.println(brandId);
			System.out.println(availability);
			System.out.println(imageUrl);

			sendResponse(req, res, "One or more bad input items.", true);
		}

		BigDecimal priceBD = convertPrice(price);
		priceBD = priceBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		// in case there is a value for discount percentage a new price is
		// calculated
		if (discountPercent != null && Float.parseFloat(discountPercent) > 0d) {
			BigDecimal decimalDiscountPercent = new BigDecimal(discountPercent);
			BigDecimal discountAmount = priceBD.multiply(decimalDiscountPercent).divide(new BigDecimal("100"));
			discountAmount = discountAmount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
			BigDecimal newPrice = priceBD.subtract(discountAmount);
			priceBD = newPrice;
			System.out.println("New prcie:" + newPrice);
		}

		if (productName.length() < MinLength) {
			sendResponse(req, res, "A product's name must be at least " + MinLength + " characters.", true);
		}

		if (priceBD == null) {
			sendResponse(req, res, "Price entered contains invalid characters: +/-, ., and decimal digits only.", true);
		}

		if (!categoryId.matches("^-?\\d+$") && Integer.parseInt(categoryId) > 0) {
			sendResponse(req, res,
					"A category id must be a positive integer and should not contain any special characters.", true);
		}

		if (!brandId.matches("^-?\\d+$") && Integer.parseInt(brandId) > 0) {
			sendResponse(req, res, "A brand id must be an integer and should not contain any special characters.",
					true);
		}

		if (!availability.matches("^-?\\d+$") && Integer.parseInt(availability) >= 0) {
			sendResponse(req, res, "Availability must be an integer and should not contain any special characters.",
					true);
		}

		// Capitalize product name
		productName = capitalizeParts(productName);

		if (productId == null) { // create rather than edit
			if (productNameInUse(productName, req, res)) {
				sendResponse(req, res, "The name '" + productName + "' is already in use.", true);
			} else if (handleCreate(productName, priceBD, description, Integer.parseInt(categoryId),
					Integer.parseInt(brandId), Integer.parseInt(availability), imageUrl)) {
				sendResponse(req, res, productName + " added to the DB.", false);
			} else {
				sendResponse(req, res, "Problem saving " + productName + " to the DB", true);
			}
		} else { // edit rather than create
			if (handleEdit(productId, productName, priceBD, description, Integer.parseInt(categoryId),
					Integer.parseInt(brandId), Integer.parseInt(availability), imageUrl)) {
				sendResponse(req, res, productName + " updated successfully.", false);
			} else {
				sendResponse(req, res, "Problem updating " + productName, true);
			}
		}
	}

	private boolean handleCreate(String productName, BigDecimal priceBD, String description, int categoryId,
			int brandId, int availability, String imageUrl) {
		boolean flag = true;
		try {
			ProductDAO.getInstance().addProduct(productName, priceBD, description, categoryId, brandId, availability,
					imageUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}

	private boolean handleEdit(String productId, String productName, BigDecimal priceBD, String description,
			int categoryId, int brandId, int availability, String imageUrl) {
		try {
			ProductDAO.getInstance().handleEdit(productId, productName, priceBD, description, categoryId, brandId,
					availability, imageUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void sendResponse(HttpServletRequest req, HttpServletResponse res, String msg, boolean error) {

		try {
			HttpSession session = req.getSession();
			session.setAttribute("result", msg);
			if (error)
				res.sendRedirect("admin/badResult");
			else
				res.sendRedirect("admin/goodResult");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BigDecimal convertPrice(String price) {
		BigDecimal result = null;
		try {
			result = new BigDecimal(price);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String capitalizeParts(String str) {
		if (str.length() < 1)
			return str;
		String[] parts = str.split(" "); // split on blanks
		String result = "";

		for (String part : parts) {
			if (part.length() > 0)
				result += new String(Character.toUpperCase(part.charAt(0)) + part.substring(1) + " ");
		}
		result = result.trim();
		return result;
	}

	private boolean productNameInUse(String productName, HttpServletRequest req, HttpServletResponse resp) {
		boolean flag = false;
		TreeMap<Integer, ProductPojo> products = null;
		try {
			products = ProductDAO.getInstance().getAllProducts();

			for (ProductPojo product : products.values()) {
				if (product.getName().equals(productName)) {
					flag = true;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

}
