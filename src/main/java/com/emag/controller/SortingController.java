package com.emag.controller;

import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.db.ProductDAO;
import com.emag.model.ProductPojo;

@Controller
public class SortingController {
	
	@RequestMapping(value = "/sortProducts", method = RequestMethod.GET)
	public String sortProductsByAscendingOrder(HttpSession session, HttpServletRequest req) {
		//Get all products
		String sort = req.getParameter("sort");
		TreeSet<ProductPojo> products = new TreeSet<ProductPojo>(new Comparator<ProductPojo>() {

			@Override
			public int compare(ProductPojo o1, ProductPojo o2) {
				if(sort.equals("asc")) {
					return	o1.getPrice().compareTo(o2.getPrice());
				}else {
					return	o2.getPrice().compareTo(o1.getPrice());
				}
			}
		});
		TreeMap<Integer, ProductPojo> productsFromDB = null;
		try {
			//Store them in a treeset and order them by ascending order
			productsFromDB = ProductDAO.getInstance().getAllProducts();
			for(Entry<Integer, ProductPojo> p : productsFromDB.entrySet()) {
				products.add(p.getValue());
			}
			
		} catch (SQLException e) {
			System.out.println("Issue loading the prducts from the database " + e.getMessage() );
		}
		//Push them in the session
		session.setAttribute("products", products);
		//Return to the main page 
		return "sortPrices";
	}
	
	@RequestMapping(value = "/gotToSortProducts", method = RequestMethod.GET)
	public String gotToSortProducts() {
		return "sortPrices";
	}

}
