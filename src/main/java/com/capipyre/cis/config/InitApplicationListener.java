package com.capipyre.cis.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.capipyre.cis.util.DbConnector;

@WebListener
public class InitApplicationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DbConnector.initDbConnector(sce.getServletContext());

		List<ApplicationInitializer> appInitList = new ArrayList<>();

		appInitList.add(new InitProductData());

		for (ApplicationInitializer appInit : appInitList) {
			appInit.init();
		}
	}

}
