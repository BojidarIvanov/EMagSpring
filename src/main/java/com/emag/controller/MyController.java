package com.emag.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
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

	@RequestMapping(value = "/registerPage", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerIndeed(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String dob = request.getParameter("DOB");
		String password = request.getParameter("pass");
		String password2 = request.getParameter("pass2");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		System.out.println(password);
		System.out.println(password2);

		if (!password.equals(password2)) {
			request.setAttribute("error", "passwords missmatch");
			return "redirect:registerPage";
		}

		try {
			PasswordUtil.checkPasswordStrength(password2);
		} catch (Exception e2) {
			request.setAttribute("error", "password is too short");
			return "redirect:registerPage";
		}

		try {

			if (Integer.parseInt(dob) < 1900 || Integer.parseInt(dob) > 2018) {
				request.setAttribute("error", "Please provide adequate date of birth");
				return "redirect:registerPage";
			}
		} catch (NumberFormatException e) {
			request.setAttribute("error", " Please enter correct year of birth.");
			return "redirect:registerPage";
		}

		try {
			UserPojo customer = null;
			try {
				String pass = PasswordUtil.hashPassword(password);
				System.out.println(pass);
				customer = new UserPojo(name, email, phone, LocalDate.of(Integer.parseInt(dob), 1, 1), pass, address,
						false);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
			if (!UserDAO.getInstance().userExists(customer)) {
				try {
					UserDAO.getInstance().addUser(customer);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				request.getSession().setAttribute("user", customer);
				request.getSession().setAttribute("newUser", customer);
				return "forward:main";
			} else {
				request.setAttribute("error", "user already registered");
				return "redirect:registerPage";

			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "redirect:loginPage";
		}
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String aboutUs() {
		return "about";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcomePage(HttpServletRequest request) {

		try {
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
					TreeMap<Integer, ProductPojo> products = ProductDAO.getInstance().getAllProducts();
					// System.out.println(products);
					application.setAttribute("products", products);
				}
				if (application.getAttribute("users") == null) {
					TreeMap<Integer, ProductPojo> users;
					users = ProductDAO.getInstance().getAllProducts();
					application.setAttribute("users", users);
				}
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage() + e.getErrorCode());
			return "forward:loginPage";
		}
		return "index";
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String myOrders() {
		return "orders";
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String categories() {
		return "categories";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contactPage() {
		return "contact";
	}

	@RequestMapping(value = "/loginPage")
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession s) {
		s.invalidate();
		return "index";
	}

	@RequestMapping(value = "/main")
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

			synchronized (application) {
				if (application.getAttribute("brands") == null) {
					Map<Integer, BrandPojo> brands = BrandDAO.getInstance().getAllBrands();
					application.setAttribute("brands", brands);
				}
				if (application.getAttribute("categories") == null) {
					Map<Integer, CategoryPojo> categories = CategoryDAO.getInstance().getAllCategories();
					System.out.println(categories);
					application.setAttribute("categories", categories);
				}
				if (application.getAttribute("products") == null) {
					TreeMap<Integer, ProductPojo> products = ProductDAO.getInstance().getAllProducts();
					// System.out.println(products);
					application.setAttribute("products", products);
				}
				if (application.getAttribute("users") == null) {
					TreeMap<Integer, ProductPojo> users = ProductDAO.getInstance().getAllProducts();
					application.setAttribute("users", users);
				}
			}

			boolean exists = UserDAO.getInstance().userExistsEmailAndPassword(user);

			if (exists) {
				// update session
				UserPojo u = UserDAO.getInstance().getUser(user);
				request.getSession().setAttribute("user", u);

				return "forward:main";
			} else {
				request.setAttribute("error", "user does not exist");
				return "forward:loginPage";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage() + e.getErrorCode());
			return "forward:loginPage";
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

	@RequestMapping(value = "/sortCategories", method = RequestMethod.GET)
	public String sortCategories(HttpServletRequest request, HttpServletResponse response) {
		String sort = request.getParameter("sort");
		Integer categoryId = Integer.parseInt(sort);
		Map<Integer, CategoryPojo> categories = (Map<Integer, CategoryPojo>) application.getAttribute("categories");
		System.out.println(categories);
		CategoryPojo category = categories.get(categoryId);
		request.setAttribute("specificCategory", category);
		System.out.println("Category" + category);
		return "categories";
	}
}