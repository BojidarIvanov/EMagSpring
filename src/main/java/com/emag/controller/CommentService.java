package com.emag.controller;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ProductDAO;
import com.emag.db.ReviewDAO;
import com.emag.db.UserDAO;
import com.emag.model.ReviewPojo;
import com.emag.model.UserPojo;


@Controller
//@RequestMapping(value="/comments")
public class CommentService {

//		@RequestMapping(value="/save", method=RequestMethod.PUT)
//		@ResponseBody
//		public void saveComment(HttpServletResponse r){
//			//
//			System.out.println("comment added");
//			r.setStatus(200);
//		}
		
		@RequestMapping(value="/admin/comment", method=RequestMethod.PUT)
		public String saveComment(HttpServletRequest req){
			System.out.println(1);
			String review = req.getParameter("comment");
			System.out.println(2);
			String name = req.getParameter("name");
			System.out.println(3);
			String email = req.getParameter("email");
			System.out.println(4);
			//String ratingString = req.getParameter("rating");
			System.out.println(5);
			//Integer rating = new Integer(ratingString);
			System.out.println(6);
			//String  productIDString = req.getParameter("productID");
			System.out.println(7);
			//Integer productID = new Integer(productIDString);
			System.out.println(8);
			UserPojo user = null;
			System.out.println(9);
			TreeMap<Integer, ReviewPojo> reviews = new TreeMap<>();
			try {
				user = UserDAO.getInstance().getUser(email);
				if(user != null) {
					int userID = user.getCustomerID();
					ReviewDAO r = ReviewDAO.getInstance();
					r.addReview(new ReviewPojo(1, review, userID, 3));
					System.out.println("review is added from the controller");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reviews = ReviewDAO.getInstance().getAllReviewsByProductId(3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			req.setAttribute("reviews", reviews);
			return "comment2";
		}
}

