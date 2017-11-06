package com.emag.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ReviewDAO;
import com.emag.model.ReviewPojo;
import com.emag.model.UserPojo;

@Controller
public class ReviewController {

	@Autowired
	ServletContext application;

	@RequestMapping(value = "/saveReview", method = RequestMethod.POST)
	public String saveComment(HttpServletRequest request, HttpSession session, Model m) {
		String rating = request.getParameter("rating");
		String reviewBody = request.getParameter("message");
		String productId = request.getParameter("productId");
		String error = "The comment was saved. Thank you.";
		UserPojo user = (UserPojo) session.getAttribute("user");
		Map<String, TreeSet<ReviewPojo>> reviews = (Map<String, TreeSet<ReviewPojo>>) application
				.getAttribute("reviews");
		int reviewId = 0;
		int ratingParsed;
		try {
			ratingParsed = Integer.parseInt(rating);
			System.out.println("reviews: " + reviews);
			ReviewDAO r = ReviewDAO.getInstance();
			reviewId = r.addReview(
					new ReviewPojo(ratingParsed, reviewBody, user.getCustomerID(), Integer.parseInt(productId)));
			synchronized (reviews) {
				reviews.get(productId).add(new ReviewPojo(reviewId, ratingParsed, reviewBody, user.getCustomerID(),
						Integer.parseInt(productId)));
			}
		} catch (NumberFormatException | SQLException e) {
			error = "Sory mate. There were some issues with database.";
		}
		m.addAttribute("error", error);
		return "redirect:admin/show?id=" + productId;
	}

	@RequestMapping(value = "/deleteReview", method = RequestMethod.POST)
	public String deleteReview(HttpServletRequest request, Model m) {
		String reviewId = request.getParameter("reviewId");
		String productId = request.getParameter("productId");
		String error = "";
		Map<String, TreeSet<ReviewPojo>> reviews = (Map<String, TreeSet<ReviewPojo>>) application
				.getAttribute("reviews");
		int reveiwIdParsed = Integer.parseInt(reviewId);

		ReviewPojo review = getReview(reviews, reveiwIdParsed, productId);
		try {
			ReviewDAO.getInstance().deleteReview(reveiwIdParsed);
			synchronized (reviews) {
				reviews.get(productId).remove(review);
			}
			error = "The review was deleted.";
		} catch (NumberFormatException e) {
			error = "Such a pity. It appears the id of review is not a number.";
		} catch (SQLException e) {
			error = "Such a pity. It appears there is an issue with database." + e;
		}
		m.addAttribute("error", error);
		return "redirect:admin/show?id=" + productId;
	}

	public ReviewPojo getReview(Map<String, TreeSet<ReviewPojo>> reviews, int reviewIdParsed, String productId) {

		TreeSet<ReviewPojo> treeSetOfReviews = reviews.get(productId);
		ReviewPojo review = null;
		for (ReviewPojo rev : treeSetOfReviews) {
			if (rev.getReviewID() == reviewIdParsed) {
				review = rev;
				break;
			}
		}
		return review;
	}
}