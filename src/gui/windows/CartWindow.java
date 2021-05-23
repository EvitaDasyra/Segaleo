package gui.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gui.factory.ButtonFactory;
import gui.factory.FontFactory;
import gui.factory.LabelFactory;
import gui.factory.TextFieldFactory;
import menu.Menu;
import order.Product;
import reservation.Activity;
import reservation.ActivityReader;
import resources.ColorResources;
import resources.TextResources;

public class CartWindow extends JFrame {

	private JPanel backgroundPanel;

	// header
	private JPanel header;
	private ImageIcon exitIcon = new ImageIcon("buttonImages/exit button.png");
	private JButton exitButton;
	private JLabel myCartLabel;

	// footer
	JPanel footer;
	private JTextField couponField;
	private JButton submitCouponButton;
	private JButton paymentMethods;
	private JLabel totalLabel;
	private JLabel priceLabel;
	private JPanel priceHolder;
	private JButton orderNowButton;

	// main
	private JPanel mainContent;
	private ImageIcon productImage;
	private JLabel productimgLabel;
	private JLabel titleLabel;
	private JLabel descLabel;
	private JLabel productPriceLabel;
	private ImageIcon plusIcon;
	private JLabel plusButtonLabel;
	private ImageIcon minusIcon;
	private JLabel minusButtonLabel;
	private JLabel quantinty;
	Menu menu = new Menu();
	private ArrayList<Activity> activities = new ActivityReader().getActivitiesList();

	private boolean isOrder = false;

	// TODO take the order from the logged customer
	// TODO display the total price in the footer

	public CartWindow() {

		initilizePanelToFrame();
		windowsConfiguration();
		showWindow(this, true);

	}

	public void windowsConfiguration() {
		this.setTitle("Segaleo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	public void initilizePanelToFrame() {
		backgroundPanel = new JPanel();
		backgroundPanel.setPreferredSize(new Dimension(375, 812));
		backgroundPanel.setLayout(new BorderLayout());

		configureHeader();
		configureFooter();
		configureMainContent();
		backgroundPanel.add(BorderLayout.NORTH, header);
		backgroundPanel.add(BorderLayout.CENTER, mainContent);
		backgroundPanel.add(BorderLayout.SOUTH, footer);

		this.setContentPane(backgroundPanel);
		this.pack();

	}

	public void showWindow(JFrame frame, boolean show) {
		frame.setVisible(show);
	}

	public void configureHeader() {
		header = new JPanel();
		header.setLayout(null);
		header.setBackground(new Color(244, 249, 250));
		header.setPreferredSize(new Dimension(375, 125));

		exitButton = ButtonFactory.createButtonIcon(exitIcon);
		exitButton.setBounds(30, 77, 13, 13);

		myCartLabel = LabelFactory.createLabel(TextResources.myCart, Color.BLACK, FontFactory.poppins(20));
		myCartLabel.setBounds(64, 67, 100, 30);

		header.add(exitButton);
		header.add(myCartLabel);
	}

	public void configureMainContent() {
		mainContent = new JPanel();
		mainContent.setLayout(new BorderLayout());
		mainContent.add(createVerticalScrollablePanel());
	}

	// creates a vertical scrollable panel
	public JScrollPane createVerticalScrollablePanel() {
		JPanel container = new JPanel();
		if (isOrder) {
			container.setLayout(new GridLayout(menu.getAppetizers().size(), 1, 0, 8));

			for (Product product : menu.getAppetizers()) {
				container.add(configureProductPanel(product));
			}
		} else {
			container.setLayout(new GridLayout(activities.size(), 1, 0, 8));

			for (Activity activity : activities) {
				container.add(configureActivityPanel(activity));
			}
		}

		JScrollPane scrollPane = new JScrollPane(container);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);

		return scrollPane;
	}

	public JPanel configureProductPanel(Product product) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(325, 120));
		panel.setBackground(Color.white);

		productImage = new ImageIcon(product.getPath());
		productimgLabel = LabelFactory.createIconLabel(productImage);
		productimgLabel.setBounds(10, 10, 100, 100);

		titleLabel = LabelFactory.createLabel(product.getName(), Color.BLACK, FontFactory.poppins(14));
		titleLabel.setBounds(120, 20, 200, 17);

		descLabel = LabelFactory.createLabel(product.getDescription(), Color.GRAY, FontFactory.poppins(12));
		descLabel.setBounds(120, 35, 200, 17);

		plusIcon = new ImageIcon("./buttonImages/plus.png");
		plusButtonLabel = LabelFactory.createIconLabel(plusIcon);
		plusButtonLabel.setIcon(plusIcon);
		plusButtonLabel.setBounds(320, 85, 24, 24);
		plusButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		minusIcon = new ImageIcon("./buttonImages/minus.png");
		minusButtonLabel = LabelFactory.createIconLabel(minusIcon);
		minusButtonLabel.setIcon(minusIcon);
		minusButtonLabel.setBounds(260, 85, 24, 24);
		minusButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		quantinty = LabelFactory.createLabel("1x", Color.BLACK, FontFactory.poppins(13));
		quantinty.setBounds(290, 85, 50, 20);

		productPriceLabel = LabelFactory.createLabel(product.getPrice() + "€", Color.BLACK, FontFactory.poppins(13));
		productPriceLabel.setBounds(286, 60, 43, 19);

		panel.add(productimgLabel);
		panel.add(titleLabel);
		panel.add(descLabel);
		panel.add(plusButtonLabel);
		panel.add(quantinty);
		panel.add(minusButtonLabel);
		panel.add(productPriceLabel);
		return panel;
	}

	public JPanel configureActivityPanel(Activity activity) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(320, 220));
		panel.setBackground(Color.white);

		productImage = new ImageIcon(activity.getPath());
		productimgLabel = LabelFactory.createIconLabel(productImage);
		productimgLabel.setBounds(5, 5, 320, 170);

		titleLabel = LabelFactory.createLabel(activity.getName(), Color.BLACK, FontFactory.poppins(14));
		titleLabel.setBounds(5, 180, 150, 40);

		productPriceLabel = LabelFactory.createLabel(activity.getPrice() + "€", Color.BLACK, FontFactory.poppins(13));
		productPriceLabel.setBounds(155, 185, 43, 19);

		panel.add(productimgLabel);
		panel.add(productPriceLabel);
		panel.add(titleLabel);
		panel.add(productPriceLabel);

		return panel;
	}

	public void configureFooter() {
		footer = new JPanel();
		footer.setLayout(null);
		footer.setPreferredSize(new Dimension(375, 290));
		footer.setBackground(new Color(244, 249, 250));

		couponField = TextFieldFactory.createTextField(TextResources.couponCode, new Color(216, 223, 224), Color.BLACK,
				FontFactory.poppins(14));
		couponField.setBounds(24, 10, 195, 48);

		submitCouponButton = ButtonFactory.createButton(TextResources.submit, FontFactory.poppins(14),
				new Color(216, 223, 224), Color.BLACK);
		submitCouponButton.setBounds(231, 10, 121, 48);

		paymentMethods = ButtonFactory.createButton(TextResources.payment, FontFactory.poppins(14), Color.LIGHT_GRAY,
				Color.BLACK);
		paymentMethods.setBounds(24, 68, 328, 63);

		priceHolder = new JPanel();
		priceHolder.setLayout(null);
		priceHolder.setPreferredSize(new Dimension(328, 63));
		priceHolder.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
		priceHolder.setBackground(Color.WHITE);

		totalLabel = LabelFactory.createLabel(TextResources.total, Color.BLACK, FontFactory.poppins(14));
		priceLabel = LabelFactory.createLabel("17.00", Color.BLACK, FontFactory.poppins(14));
		totalLabel.setBounds(23, 18, 100, 20);
		priceLabel.setBounds(264, 18, 50, 20);

		priceHolder.setBounds(24, 141, 328, 63);

		orderNowButton = ButtonFactory.createButton(TextResources.orderNow, FontFactory.poppins(15),
				ColorResources.frMainWindowBtn, Color.WHITE);
		orderNowButton.setBounds(24, 214, 328, 41);
		priceHolder.add(totalLabel);
		priceHolder.add(priceLabel);

		footer.add(couponField);
		footer.add(submitCouponButton);
		footer.add(paymentMethods);
		footer.add(priceHolder);
		footer.add(orderNowButton);
	}

}