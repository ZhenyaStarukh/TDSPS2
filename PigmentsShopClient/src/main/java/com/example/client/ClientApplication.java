package com.example.client;

import com.example.client.objects.Client;
import com.example.client.objects.Order;
import com.example.client.services.CustomerService;
import com.example.client.services.OrderService;
import com.example.client.services.ShopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);

		story();
	}

	public static void story(){





		ShopService.openShop();

		System.out.println("CREATING CUSTOMERS...");
		Client Ivan = CustomerService.createCustomer("Ivan","+380959873450");
		Client Anna = CustomerService.createCustomer("Anna","+380664521183");
		Client Sasha = CustomerService.createCustomer("Alexander","+380934568761");

		System.out.println("\nREGISTERING THEM...");
		CustomerService.registerCustomer(Ivan,"yes");//you'll see an error message that he's already registered
		CustomerService.registerCustomer(Anna,"yes");
		CustomerService.registerCustomer(Sasha,"no");


		System.out.println("\nLIST OF REGISTERED CLIENTS");
		CustomerService.showClients();


		System.out.println("\nCREATING ORDERS...");
		Order ivansOrder = OrderService.makeOrder(Ivan);
		Order annasOrder = OrderService.makeOrder(Anna);
		Order sashasOrder = OrderService.makeOrder(Sasha);


		OrderService.showPigmentsList(Sasha);


		OrderService.choosePigments(ivansOrder,7,1000);
		OrderService.choosePigments(ivansOrder,5,500);
		OrderService.createPigment(annasOrder,new double[]{0.0,0.34,0.66,0.0,0.0},1000);
		OrderService.createPigment(sashasOrder,new double[]{0.1,0.3,0.5,0.1,0.0},140);
		OrderService.choosePigments(sashasOrder,4,590);


		System.out.println("\nSAVING PIGMENT IN DATABASE...");
		OrderService.savePigment(annasOrder,1,"Orange");

		System.out.println("\nCHECKING WHETHER THE PIGMENT IS SAVED IN A RIGHT WAY...");
		System.out.println(Anna.getName()+":");
		OrderService.showPigmentsList(Anna);
		System.out.println(Ivan.getName()+":");
		OrderService.showPigmentsList(Ivan);


		System.out.println("\nDELETING PIGMENT...");
		OrderService.deletePigment("Orange",Anna);

		System.out.println("\nCHECKING WHETHER THE PIGMENT IS REMOVED FROM DATABASE...");
		System.out.println(Anna.getName());
		OrderService.showPigmentsList(Anna);
		System.out.println();


		OrderService.alterPigment(sashasOrder,1,new double[]{0.1,0.3,0.5,0.0,0.1});

		System.out.println("\nADDING EFFECT TO A PIGMENT...");
		OrderService.showEffects();
		OrderService.addEffect(ivansOrder,1,3);

		OrderService.showOrder(ivansOrder);

		System.out.println("REMOVING PIGMENT FROM AN ORDER...");
		OrderService.removeFromOrder(ivansOrder,2);

		OrderService.showOrder(ivansOrder);

		System.out.println("MANAGING PURCHASES...");
		ShopService.makePurchase(annasOrder,"yes");
		ShopService.makePurchase(ivansOrder,"no");
		ShopService.makePurchase(sashasOrder,"yes");


		ShopService.closeShop();




	}

}
