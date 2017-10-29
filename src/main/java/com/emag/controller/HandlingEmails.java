package com.emag.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String subscribe(HttpServletRequest request, HttpServletResponse response) {

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
				if(users == null) {
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
}
