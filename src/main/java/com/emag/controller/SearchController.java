package com.emag.controller;

import java.sql.SQLException;
import java.util.Map.Entry;
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
	
	@Autowired
	ServletContext application;

	@RequestMapping(value = "/searchProduct", method = RequestMethod.GET)
	public String searchProduct(Model model, HttpServletRequest req) {	
		System.out.println("1");
		// Get the specified product from the request
		String productName = req.getParameter("productName");
		// Get all the products from the database
		TreeMap<Integer, ProductPojo> products = null;
		try {
			products = ProductDAO.getInstance().getAllProducts();
			System.out.println("2");
		} catch (SQLException e) {
			System.out.println("Error loading the products " + e.getMessage());
		}
		// Find if the requested product exists in the data base
		System.out.println("3");
		boolean productExists = false;
		ProductPojo searchedProduct = null;
		for (Entry<Integer, ProductPojo> p : products.entrySet()) {
			if (p.getValue().getName().equalsIgnoreCase(productName)) {
				searchedProduct = p.getValue();
				productExists = true;
				break;
			}
		}
		System.out.println("4");
		
		// If it dosen't exist go to homepage
		System.out.println(productExists);
		model.addAttribute("productExists", productExists);
		if (productExists) {
			// If it exists store it in the Model
			model.addAttribute("productName", searchedProduct.getName());
			model.addAttribute("productPrice", searchedProduct.getPrice());
			model.addAttribute("productQuantity", searchedProduct.getQuantity());
			System.out.println("Product exists");
			
		} 
		return "searchView";

		
	}
//	@RequestMapping (value = "/searchProduct", method = RequestMethod.GET)
//	public String search() {
//		
//		return "about";
//	}
	// System.out.println("Start of search servlet");
			// String productName = req.getParameter("productName");
			// Map<Integer, ProductPojo> products = null;
			// ProductPojo product = null;
			// try {
			// ProductDAO p = ProductDAO.getInstance();
			// products = p.getAllProducts();
			// } catch (SQLException e) {
			// System.out.println("Problem loading the products " + e.getMessage());
			// }
			// System.out.println("Middle of search servlet");
			// if(products != null) {
			// for(Entry<Integer, ProductPojo> p : products.entrySet()) {
			// if(p.getValue().getName().equalsIgnoreCase(productName)) {
			// product = p.getValue();
			// }
			// }
			// }
			// System.out.println(product);;
			// req.setAttribute("product", product);
			// System.out.println("End of search servlet");
			// return "SearchView";
			//
			//
			// }


}
