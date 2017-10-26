package com.emag.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.BrandDAO;
import com.emag.db.CategoryDAO;
import com.emag.db.ProductDAO;
import com.emag.db.UserDAO;
import com.emag.model.BrandPojo;
import com.emag.model.CategoryPojo;
import com.emag.model.OrderPojo;
import com.emag.model.ProductPojo;
import com.emag.model.UserPojo;
import com.emag.util.PasswordUtil;

@Controller

public class MyController {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcomePage() {
		return "index";
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String myOrders() {
		return "orders";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactPage() {
		return "contact";
	}

	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession s) {
		s.invalidate();
		return "index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.POST)
	public String main() {
		return "main";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPage(HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("user");
		String password = request.getParameter("pass");
		System.out.println(email);
		System.out.println(password);
		// check if user exists in db
		UserPojo user = null;
		try {
			String hashedPassword = PasswordUtil.hashPassword(password);
			user = new UserPojo(email, hashedPassword);
			System.out.println(hashedPassword);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		try {
			boolean exists = UserDAO.getInstance().userExistsEmailAndPassword(user);
			if (exists) {
				// update session
				UserPojo u = UserDAO.getInstance().getUser(user);
				request.getSession().setAttribute("user", u);

				synchronized (application) {
					if (application.getAttribute("brands") == null) {
						Map<Integer, BrandPojo> brands = BrandDAO.getInstance().getAllBrands();
						application.setAttribute("brands", brands);
					}
					if (application.getAttribute("categories") == null) {
						Map<Integer, CategoryPojo> categories = CategoryDAO.getInstance().getAllCategories();
						application.setAttribute("categories", categories);
					}
					if (application.getAttribute("products") == null) {
						TreeSet<ProductPojo> products = ProductDAO.getInstance().getAllProducts();
						// System.out.println(products);
						application.setAttribute("products", products);
					}
					if (application.getAttribute("users") == null) {
						TreeSet<ProductPojo> users = ProductDAO.getInstance().getAllProducts();
						application.setAttribute("users", users);
					}

				}
				return "forward:main";
			} else {
				request.setAttribute("error", "user does not exist");
				return "forward:login";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage() + e.getErrorCode());
			return "forward:login";
		}
	}

	@RequestMapping(value = "/sortOrders", method = RequestMethod.GET)
	public String sortOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sort = request.getParameter("sort");
		Object o = request.getSession().getAttribute("user");
		if (o == null) {
			return "login";
		}
		UserPojo u = (UserPojo) o;
		TreeSet<com.emag.model.OrderPojo> set = new TreeSet<>(new Comparator<OrderPojo>() {
			@Override
			public int compare(OrderPojo o1, OrderPojo o2) {

				if (sort.equals("asc")) {
					if (o1.getDate().isBefore(o2.getDate())) {
						return -1;
					} else if (o2.getDate().isBefore(o1.getDate())) {
						return 1;
					}
				} else {
					if (sort.equals("desc")) {
						if (o1.getDate().isBefore(o2.getDate())) {
							return 1;
						} else if (o2.getDate().isBefore(o1.getDate())) {
							return -1;
						}
					}
				}
				return 0;
			}	
		});

		set.addAll(u.getOrders());
		u.setOrders(set);
		return "orders";
	}
}