package com.emag.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.EmailDAO;
import com.emag.model.UserPojo;
import com.emag.util.MailUtilGmail;

@Controller
public class HandlingEmails {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(HttpServletRequest request, HttpServletResponse response) {

		String task = request.getParameter("task");
		Set<String> allEmails = null;
		if (task.equals("promo")) {
			try {
				allEmails = EmailDAO.getInstance().getAllEmails();
			} catch (SQLException e) {
				e.printStackTrace();
				return "redirect:html/sqlError.html";
			}
		}
		Map<String, UserPojo> users = (Map<String, UserPojo>) application.getAttribute("users");
		System.out.println(users);
		String from = "pechorinnd@gmail.com";
		String subject = request.getParameter("subject");
		String bodyProvided = request.getParameter("promoContent");
		String fullName = "";
		String message = "";
		String to = "";
		String body = "";
		boolean isBodyHTML = false;
		for (String email : allEmails) {
			try {
				if (users == null) {
					return "redirect:html/lostSession.html";
				}
				fullName = users.get(email) != null ? users.get(email).getName() : "customer";
				body = "Dear " + fullName + ",\n\n" + bodyProvided;
				MailUtilGmail.sendMail(email, from, subject, body, isBodyHTML);
			} catch (MessagingException e) {
				String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
						+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
						+ "ERROR MESSAGE: " + e.getMessage();
				request.setAttribute("errorMessage", errorMessage);
			}
		}
		// request.setAttribute("message", message);
		String url = "/admin/productManagement";
		return url;
	}

	@RequestMapping(value = "/addEmail", method = RequestMethod.POST)
	public String subscribe(HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("email");
		boolean isEmailSaved = false;
		boolean isValid = validate(email);
		String result = "";
		if (isValid) {
			try {
				isEmailSaved = EmailDAO.getInstance().addEmail(email);
			} catch (SQLException e) {
				e.printStackTrace();
				return "redirect:sqlError.html";
			}
			result = "Email saved. Thank you.";
		}

		if (!isEmailSaved && isValid) {
			System.out.println("The email was already in our records.");
			result = "The email was already in our records.";
		}

		if (!isValid) {
			result = "Invalid email.";
		}

		request.setAttribute("SubscrMsg", result);
		return "index";
	}

	@RequestMapping(value = "/messageFromCustomer", method = RequestMethod.POST)
	public String contact(Model model, HttpServletRequest request, HttpServletResponse response) {
		String subject = request.getParameter("subject");
		String email = request.getParameter("email");
		String message = request.getParameter("message");
		String name = request.getParameter("name");
		String completeMessage = "The message was sent by customer " + name + ", with email: " + email + "\n\n" + message;
		try {
			MailUtilGmail.sendMail("pechorinnd@gmail.com", "pechorinnd@gmail.com", subject, completeMessage, false);
			model.addAttribute("errorMessage", "The email was sent. We will contact you as soon as possible. Thank you for being our customer.");
		} catch (MessagingException e) {
			String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
					+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
					+ "ERROR MESSAGE: " + e.getMessage();
			model.addAttribute("errorMessage", errorMessage);
		}
		return "contact";
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

}
