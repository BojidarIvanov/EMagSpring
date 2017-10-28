package com.emag.controller;

import java.sql.SQLException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.UserDAO;
import com.emag.model.UserPojo;
import com.emag.util.MailUtilGmail;

@Controller
public class EmailsController {
	/*
			@RequestMapping(value = "/send", method = RequestMethod.POST)
			public String sendEmailToCustomer() {
			// get current action
			String url = "";
			// get parameters from the request
			String message = "";
			String em = request.getParameter("email");
			boolean emailExists = false;
			try {
				emailExists = UserDAO.getInstance().userExists(new UserPojo(em, "0"));
			} catch (SQLException e1) {
				request.getRequestDispatcher("sqlError.html").forward(request, response);
				e1.printStackTrace();
			}
			if (emailExists == true) {
				HttpSession session = request.getSession();
				String uuid = UUID.randomUUID().toString();
				UserPojo user = null;
				try {
					user = UserDAO.getInstance().getAllUsers().get(em);
				} catch (SQLException e1) {
					request.getRequestDispatcher("sqlError.html").forward(request, response);
					e1.printStackTrace();
				}
				String email = user.getEmail();
				session.setAttribute("email", email);
				String firstName = user.getName();
				String password = user.getPassword();
				System.out.println(password);
				String to = email;
				String from = "pechorinnd@gmail.com";
				String link = "http://localhost/WebDevelopment/resetpwd";
					//	+ "&value=" + uuid + "&value="+ email;
				String subject = "Regarding Password Recovery";
				String body = "Dear " + firstName + ",\n\n" + "Your password Recovery Link is below. Click on that..."
						+ link;

				boolean isBodyHTML = false;

				try {
					System.out.println("inside try");
					MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);

				} catch (MessagingException e) {
					String errorMessage = "ERROR: Unable to send email. " + "Check Tomcat logs for details.<br>"
							+ "NOTE: You may need to configure your system " + "as described in chapter 14.<br>"
							+ "ERROR MESSAGE: " + e.getMessage();
					request.setAttribute("errorMessage", errorMessage);
					this.log("Unable to send email. \n" + "Here is the email you tried to send: \n"
							+ "=====================================\n" + "TO: " + email + "\n" + "FROM: " + from + "\n"
							+ "SUBJECT: " + subject + "\n" + "\n" + body + "\n\n");

					message = "password has been sent to your email ";
				}

			} else {
				message = "Email doesn't exists in our database";
			}
			request.setAttribute("message", message);
			url = "/forget.jsp";

			getServletContext().getRequestDispatcher(url).forward(request, response);
		}
	}

	
	
	
*/
}
