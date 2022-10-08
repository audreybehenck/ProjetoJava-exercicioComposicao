package application;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import entities.Client;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import entities.enums.OrderStatus;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter cliente data:");
		System.out.print("Name: ");
		String name = sc.nextLine();
		System.out.printf("Email: ");
		String email = sc.nextLine();
		System.out.print("Birth date (DD/MM/YYYY): ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate birthDate = LocalDate.parse(sc.next(), formatter);

		Client client = new Client(name, email, birthDate);

		System.out.println("Enter order data: ");
		System.out.print("Status: ");
		String status = sc.next();
		LocalDateTime moment = LocalDateTime.now();
		Order order = new Order(moment, OrderStatus.valueOf(status), client);

		System.out.print("How many items to this order? ");
		int n = sc.nextInt();

		for (int i = 0; i < n; i++) {
			System.out.println("Enter #" + (i + 1) + " item data: ");
			System.out.print("Product name: ");
			String productName = sc.next();
			System.out.print("Product price: ");
			double price = sc.nextDouble();
			Product product = new Product(productName, price);
			System.out.printf("Quantity: \n");
			int quantity = sc.nextInt();
			OrderItem orderItem = new OrderItem(quantity, price, product);
			order.addItem(orderItem);
		}

		System.out.println("ORDER SUMMARY: ");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		System.out.println("Order moment: " + order.getMoment().format(dtf));
		System.out.println("Order status: " + order.getStatus());
		System.out.println(
				"Client: " + client.getName() + " (" + client.getBirthDate().format(formatter) + ")" + " - " + client.getEmail());
		System.out.println("Order itens: ");

		for (OrderItem orderItem : order.getItems()) {
			Product product = orderItem.getProduct();

			System.out.printf("%s, $%.2f, Quantity: %d, Subtotal: $%.2f%n", product.getName(), product.getPrice(),
					orderItem.getQuantity(), orderItem.subTotal());
		}

		System.out.printf("Total price: $%.2f", order.totalItems());

		sc.close();
	}
}
