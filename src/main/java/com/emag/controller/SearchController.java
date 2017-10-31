package com.emag.controller;

import java.sql.SQLException;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ProductDAO;
import com.emag.model.ProductPojo;

@Controller
public class SearchController {

	@RequestMapping(value = "/searchProduct", method = RequestMethod.GET)
	public String searchProduct(Model model, HttpServletRequest req) {	
		boolean productExists = false;
		// Get the specified product from the request
		TreeMap<Integer, ProductPojo> matchingProducts = null;
		String productName = req.getParameter("productName");
		try {
			//Find it in the db
			matchingProducts = ProductDAO.getMatchingProducts(productName);
		} catch (SQLException e1) {
			System.out.println("Issue retrieving the products from the database " + e1.getMessage() );
		}
		//return it to the req if it exists
		if(matchingProducts.size() > 0) {
			productExists = true;
			req.setAttribute("matchingProducts", matchingProducts);
		}
		//return to the view
		model.addAttribute("productExists", productExists);
		return "searchView";

		
	}
}
