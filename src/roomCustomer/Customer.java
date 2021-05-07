package roomCustomer;

import java.util.ArrayList;

import order.Coupon;
import order.Order;

public class Customer{
	
	private String name;
	private String email;
	private Room room = null;
	private ArrayList<Coupon> coupons = new ArrayList<Coupon>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	//ΜΟΛΙΣ ΠΕΡΑΣΤΕΙ ΣΤΟ ΜΑΣΤΕΡ, ΤΟ ΠΑΡΑΚΑΤΩ ARRAYLIST NA ΠΑΨΕΙ ΝΑ ΕΙΝΑΙ ΣΧΟΛΙΟ
	//MΑΖΙ ΜΕ ΤΙΣ ΚΛΑΣΕΙΣ ΠΟΥ ΤΟ ΠΕΡΙΒΑΛΛΟΥΝ
	//private ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	
	
	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}

	public void addCoupons(Coupon coupon) {
		coupons.add(coupon);
	}


	public void addOrders(Order order) {
		orders.add(order);
	}


	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}


	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	/*public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
	
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}*/
}