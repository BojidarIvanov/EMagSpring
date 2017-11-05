package com.emag.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.BrandDAO;
import com.emag.db.CategoryDAO;
import com.emag.db.ProductDAO;
import com.emag.db.UserDAO;
import com.emag.model.BrandPojo;
import com.emag.model.CategoryPojo;
import com.emag.model.ProductPojo;
import com.emag.model.UserPojo;
import com.emag.util.MailUtilGmail;
import com.emag.util.PasswordUtil;
import com.sun.swing.internal.plaf.synth.resources.synth;

@Controller

public class MyController {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/registerPage")
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo() {
		return "updateUserInfo";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerIndeed(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String error = "";

		Object userInSession = session.getAttribute("user");
		Object oldEmail = request.getParameter("oldEmail");
		System.out.println("User: " + userInSession);
		System.out.println("OldEmail: " + oldEmail);

		String name = request.getParameter("name").trim();
		String email = request.getParameter("email").trim();
		String dob = request.getParameter("DOB").trim();
		String password = request.getParameter("pass");
		String password2 = request.getParameter("pass2");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address").trim();
		System.out.println(password);
		System.out.println(password2);

		
		if (name == null || name.length() < 1) {
			error = "Name is too short or too many spaces used. Please be serious.";
			request.setAttribute("error", error);
			if (checkIfRegistered(userInSession, oldEmail)) {
				return "forward:updateUserInfo";
			}
			return "forward:registerPage";
		}

		if (oldEmail == null) {
			if (password == null || !password.equals(password2)) {
				error = "passwords missmatch";
				request.setAttribute("error", error);
				return "forward:registerPage";
			}
			error = PasswordUtil.checkPasswordStrength(password2);
			if (!error.equals("Password is ok")) {
				return "forward:updateUserInfo";
			}
		}

		dob = specialCharacterRemoverOnlyDigits(dob);
		try {
			if (dob == null || Integer.parseInt(dob) < 1900 || Integer.parseInt(dob) > 2017) {
				error = "Please provide adequate date of birth";
				request.setAttribute("error", error);
				if (checkIfRegistered(userInSession, oldEmail)) {
					return "forward:updateUserInfo";
				}
				return "forward:registerPage";
			}
		} catch (NumberFormatException nfe) {
			error = "Please provide year of birth: only four digits without special characters.";
			request.setAttribute("error", error);
			return "forward:registerPage";
		}

		if (email == null || !HandlingEmails.validate(email)) {
			error = "The email provided is invalid.";
			request.setAttribute("error", error);
			if (checkIfRegistered(userInSession, oldEmail)) {
				error = "The email provided is invalid. Details were not updated.";
				request.setAttribute("error", error);
				return "forward:updateUserInfo";
			}
			return "forward:registerPage";
		}
		address = specialCharacterRemover(address);
		if (address == null || address.length() < 3) {
			error = "Address cannot be so short.";
			request.setAttribute("error", error);
			if (checkIfRegistered(userInSession, oldEmail)) {
				return "forward:updateUserInfo";
			}
			return "forward:registerPage";
		}

		if (phone == null || phone.length() < 5) {
			error = "Phone number cannot be less than 5 digits.";
			request.setAttribute("error", error);
			if (checkIfRegistered(userInSession, oldEmail)) {
				return "forward:updateUserInfo";
			}
			if (oldEmail == null) {
				return "forward:updateUserInfo";
			}
			return "forward:registerPage";
		}

		try {
			int phoneNumber = Integer.parseInt(phone);
		} catch (NumberFormatException nfe) {
			error = "Phone number should contain only digits without spaces.";
			request.setAttribute("error", error);
			return "forward:registerPage";
		}

		// update details rather than create new user
		if ((oldEmail != null && userInSession != null)) {
            String oldEmailCasted  = (String) oldEmail;
			Map<String, UserPojo> users = (Map<String, UserPojo>) application.getAttribute("users");
			if(users.keySet().contains(email) && !email.equals(oldEmailCasted)) {
				error = "The email is already in use. Please spicify another e-mail. Details are not updated.";
				request.setAttribute("error", error);
				return "forward:updateUserInfo";
			}
			UserPojo updatedUser = users.get(oldEmailCasted);
	
			updatedUser.setEmail(email);
			updatedUser.setPhone(phone);
			updatedUser.setDateOfBirth(LocalDate.of(Integer.parseInt(dob), 1, 1));
			updatedUser.setAddress(address);
			try {
				boolean updated = UserDAO.getInstance().updateUserDetails(updatedUser);
				// updating application scope users
				synchronized(users) {
                users.remove(oldEmailCasted);
                users.put(email, updatedUser);
				}
			} catch (SQLException e) {
				// if something went wrong the email should be set back
				updatedUser.setEmail(oldEmailCasted);
				error = "Some issues with database. Details were not updated.";
				request.setAttribute("error", error + e);
				return "forward:updateUserInfo";
			}
			error = "Details were updated.";
			request.setAttribute("error", error);
			return "forward:updateUserInfo";
		}
		try {
			UserPojo customer = null;
			try {
				String pass = PasswordUtil.hashPassword(password);
				System.out.println(pass);
				customer = new UserPojo(name, email, phone, LocalDate.of(Integer.parseInt(dob), 1, 1), pass, address,
						false);
			} catch (NumberFormatException e1) {
				request.setAttribute("error", "Please provide only year of birth. Four digits without spaces.");
				return "forward:registerPage";
			} catch (NoSuchAlgorithmException e1) {
				request.setAttribute("error", "Sorry, there are some unresolved issues on our side. Please try later.");
				return "index";
			}
			if (!UserDAO.getInstance().userExists(customer)) {
				try {
					UserDAO.getInstance().addUser(customer);
				} catch (SQLException | NoSuchAlgorithmException e) {
					request.setAttribute("error",
							"Sorry, there are some unresolved issues on our side. Please try later.");
					return "index";
				}
				request.getSession().setAttribute("user", customer);
				request.getSession().setAttribute("newUser", customer);
				return "index";
			} else {
				request.setAttribute("error", "user already registered");
				return "forward:registerPage";

			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "forward:registerPage";
		}
	}

	private static String specialCharacterRemover(String str) {
		return str.replaceAll("[^A-Za-z0-9]+", " ");
	}

	private static String specialCharacterRemoverOnlyDigits(String str) {
		return str.replaceAll("[^0-9]+", "");
	}

	private static boolean checkIfRegistered(Object userInSession, Object oldEmail) {
		return (oldEmail != null && userInSession != null);
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
					Map<String, UserPojo> users;
					users = UserDAO.getInstance().getAllUsers();
					System.out.println(users);
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
	public String loginPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		String email = request.getParameter("user");
		String password = request.getParameter("pass");
		System.out.println(email);
		System.out.println(password);
		
		UserPojo user = null;
		try {
			String hashedPassword = PasswordUtil.hashPassword(password);
			user = new UserPojo(email, hashedPassword);
			System.out.println(hashedPassword);
		} catch (NoSuchAlgorithmException e1) {
			request.setAttribute("error", "Sorry, we have some issues. Please try again later.");
			return "forward:loginPage";
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
					Map<String, UserPojo> users;
					users = UserDAO.getInstance().getAllUsers();
				}
			}
			// check if user exists in db
			boolean exists = UserDAO.getInstance().userExistsEmailAndPassword(user);

			if (exists) {
				// update session
				UserPojo u = UserDAO.getInstance().getUser(user);
				session.setAttribute("user", u);
				session.setAttribute("email", u.getEmail());

				return "redirect:index";
			} else {
				request.setAttribute("error", "Email or password are wrong.");
				return "forward:loginPage";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage() + e.getErrorCode());
			return "forward:loginPage";
		}
	}

	private String url = "";
	private String errorMsg = "";

	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public String changePass(Model model, HttpServletRequest req, HttpSession session, HttpServletResponse response) {
		url = "index";
		if (session.getAttribute("user") != null) {
			String oldPass = req.getParameter("oldPassword");
			String newPass = req.getParameter("newPassword");
			UserPojo u = null;
			try {
				u = UserDAO.getInstance().getAllUsers().get((String) session.getAttribute("email"));
			} catch (SQLException e1) {
				e1.printStackTrace();
				errorMsg = "something went wrong, please try again later";
			}
			try {
				if (PasswordUtil.hashPassword(oldPass).equals(u.getPassword())) {
					if (!PasswordUtil.validatePassword(newPass)) {
						errorMsg = "Password is not safe! Please use imagination. It is all about your safety.";
					} else {
						try {
							u.setPassword(PasswordUtil.hashPassword(newPass));
							UserDAO.getInstance().updatePass(u);
							errorMsg = "Password successfully changed!";
						} catch (SQLException e) {
							errorMsg = "something went wrong, please try again later";
						}
					}
				} else {
					errorMsg = "Password does not match our records, your changes were not saved!";
				}
			} catch (NoSuchAlgorithmException e) {
				errorMsg = "Password does not match our records, your changes were not saved!";
				e.printStackTrace();
			}

		} else {
			errorMsg = "Please log in before changing the password.";
			url = "forward:loginPage";
		}
		model.addAttribute("errorMsg", errorMsg);
		errorMsg = null;
		return url;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String forgotPass(Model model, HttpServletRequest request, HttpServletResponse response) {
		url = "forgotPassword";
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			errorMsg = "The encoding in use seems to be a problem.";
			url = "forgotPassword";
		}
		String email = request.getParameter("email").trim();

		Map<String, UserPojo> users = (Map<String, UserPojo>) application.getAttribute("users");

		if (email == null) {
			errorMsg = "No such user";
			model.addAttribute("errorMsg", errorMsg);
			errorMsg = null;
			return "forgotPassword";
		} else {
			UserPojo u = users.get(email);
			if (u == null) {
				errorMsg = "No such user";
				model.addAttribute("errorMsg", errorMsg);
				errorMsg = null;
				return "forgotPassword";
			}

			String password = UUID.randomUUID().toString().substring(8);
			String hashedPassword = "";
			try {
				hashedPassword = PasswordUtil.hashPassword(password);
			} catch (NoSuchAlgorithmException e1) {
				model.addAttribute("errorMsg", errorMsg);
				errorMsg = null;
				return "forgotPassword";
			}
			u.setPassword(hashedPassword);
			String subject = "Regarding Password Recovery";
			String body = "Dear " + u.getName() + ",\n\n" + "Your new password: " + password + "\n\n"
					+ "You can change your password using Change password functionality which is located at the "
					+ "\n\n"
					+ "bottom of the main page of the site. Please note it is available only when you are logged in."
					+ "\n\n" + " Best regards," + "\n\n" + "Emag team.";
			try {
				UserDAO.getInstance().updatePass(u);
				try {
					MailUtilGmail.sendMail(email, "pechorinnd@gmail.com", subject, body, false);
					errorMsg = "A new password was sent to your email. We recommend changing the password after login.";
				} catch (MessagingException e) {
					e.printStackTrace();
					errorMsg = "Something went wrong, please try again later" + e;
					url = "forgotPassword";
				}

			} catch (SQLException e) {
				errorMsg = "Something went wrong, please try again later" + e;
				url = "forgotPassword";
			}
		}
		model.addAttribute("errorMsg", errorMsg);
		errorMsg = null;
		return url;
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword() {
		return "forgotPassword";
	}

}