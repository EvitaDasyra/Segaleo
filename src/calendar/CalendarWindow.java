package calendar;
import javax.swing.*;
import javax.swing.table.*;

import gui.factory.ButtonFactory;
import gui.factory.FontFactory;
import gui.factory.LabelFactory;
import gui.windows.ActivityWindow;
import gui.windows.MainWindow;
import reservation.Activity;
import reservation.Reservation;
import resources.ColorResources;
import resources.TextResources;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarWindow extends JFrame implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3493826716906127814L;
	/**
	 * 
	 */

	private JTable tblCalendar;
	private JPanel container;
	private DefaultTableModel mtblCalendar; //Table model
	private JScrollPane stblCalendar; //The scrollpane
	private JPanel pnlCalendar;
	@SuppressWarnings("unused")
	private int realYear, realMonth, realDay, currentYear, currentMonth;

	private JLabel dayLabel;
	private JLabel timeLabel;
	private JLabel peopleLabel;

	private ArrayList<Activity> activities;
	private Activity activity;
	private Reservation reservation;
	

	private String path = "buttonImages/Back Button";
	private String lang = TextResources.imageLang;
	private ImageIcon backImage = new ImageIcon(path + lang);
	private JButton backBtn;

	private JButton hour1Btn;
	private JButton hour2Btn;

	private ImageIcon plusIcon;
	private JLabel plusButtonLabel;
	private ImageIcon minusIcon;
	private JLabel minusButtonLabel;
	private JLabel quantinty;

	private JButton confirmBtn;
	private boolean flagHour = false;
	private boolean flagDay = false;
	
	private int[][] a;


	public CalendarWindow(ArrayList<Activity> activities , Activity activity, Reservation reservation) {

		new TextResources().changeLanguage();
		new ColorResources();
		this.activities = activities;
		this.activity = activity;
		this.reservation = reservation;
		this.a = Activity.getA();

		configurateHourButtons(ColorResources.timeBtn, ColorResources.timeBtn);
		configureCalendar();
		initializePanel();
		windowsConfiguration();
		showWindow(this, true);


	}	
	public void windowsConfiguration() {
		this.setTitle("Segaleo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	public void addListeners() {
		hour1Btn.addActionListener(this);
		hour2Btn.addActionListener(this);
		backBtn.addActionListener(this);
		confirmBtn.addActionListener(this);
	}
	
	public void showWindow(JFrame frame, boolean show) {
		frame.setVisible(show);
	}
	
	public void initializePanel() {
		
		// panel 
		container = new JPanel();
		container.setPreferredSize(new Dimension(375, 812));
		container.setLayout(null);
		container.setBackground(new Color(216,223,224));
		container.setBounds(25, 130, 320, 350);
	
		configureLabels();
		configureButtons();


		//Add controls to panel
		container.add(pnlCalendar);
		pnlCalendar.add(stblCalendar);
		container.add(dayLabel);
		container.add(timeLabel);
		container.add(backBtn);
		container.add(hour1Btn);
		container.add(hour2Btn);
		container.add(peopleLabel);
		container.add(quantinty);
		container.add(minusButtonLabel);
		container.add(plusButtonLabel);
		container.add(confirmBtn);

		

		addListeners();
		this.setContentPane(container);
		this.pack();

	}
	
	@SuppressWarnings("serial")
	public void configureCalendar() {

		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		tblCalendar.addMouseListener(new jTableListener());
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);
		pnlCalendar.setBackground(new Color(216,223,224));
		
		//Set bounds
				pnlCalendar.setBounds(25, 120, 320, 300);
				stblCalendar.setBounds(10, 50, 300, 250);


				//Get real month/year
				GregorianCalendar cal = new GregorianCalendar(); //Create calendar
				realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
				realMonth = cal.get(GregorianCalendar.MONTH); //Get month
				realYear = cal.get(GregorianCalendar.YEAR); //Get year
				currentMonth = realMonth; //Match month and year
				currentYear = realYear;

				//Add headers
				String[] headers = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"}; //All headers
				String[] headersGR = {"ΚΥΡ", "ΔΕΥ", "ΤΡΙ", "ΤΕΤ", "ΠΕΜ", "ΠΑΡ", "ΣΑΒ"}; //All headers

				for (int i=0; i<7; i++){
					if (TextResources.isEnglish)
					{
						mtblCalendar.addColumn(headers[i]);
					}
					else
					{
						mtblCalendar.addColumn(headersGR[i]);
					}
				}

				//				tblCalendar.setBackground(new Color(216,223,224));
				//				tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background


				//No resize/reorder
				tblCalendar.getTableHeader().setResizingAllowed(false);
				tblCalendar.getTableHeader().setReorderingAllowed(false);

				//Single cell selection
				tblCalendar.setColumnSelectionAllowed(true);
				tblCalendar.setRowSelectionAllowed(true);
				tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				//Set row/column count
				tblCalendar.setRowHeight(38);
				mtblCalendar.setColumnCount(7);
				mtblCalendar.setRowCount(6);


				//Refresh calendar
				refreshCalendar (realMonth, realYear); //Refresh calendar
	}
	
	public void configureLabels() {
		dayLabel = LabelFactory.createLabel(TextResources.chooseDay, Color.gray, FontFactory.poppins(22));
		dayLabel.setBounds(32,90,160,32);
		timeLabel = LabelFactory.createLabel(TextResources.chooseTime, Color.gray, FontFactory.poppins(22));
		timeLabel.setBounds(32,470,160,32);
		peopleLabel = LabelFactory.createLabel(TextResources.choosePeople, Color.gray, FontFactory.poppins(22));
		peopleLabel.setBounds(32,660,165,32);
	}
	
	public void configurateHourButtons(Color c1, Color c2) {
		hour1Btn = ButtonFactory.createButton(activity.getHour().get(0),FontFactory.poppins(14),
				c1,Color.WHITE);
		hour1Btn.setBounds(111, 520, 154, 50);
		hour2Btn = ButtonFactory.createButton(activity.getHour().get(1),FontFactory.poppins(14),
				c2,Color.WHITE);
		hour2Btn.setBounds(111, 590, 154, 50);
	}
	
	public void configureButtons() {
		backBtn = ButtonFactory.createButtonIcon(backImage);
		backBtn.setBounds(15, 25, 64, 45);
		backBtn.setPressedIcon(backImage);

		plusIcon = new ImageIcon("./buttonImages/plus.png");
		plusButtonLabel = LabelFactory.createIconLabel(plusIcon);
		plusButtonLabel.setIcon(plusIcon);
		plusButtonLabel.setBounds(210, 710, 24, 24);
		plusButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		plusButtonLabel.addMouseListener(this);
		plusButtonLabel.setName("plus");

		minusIcon = new ImageIcon("./buttonImages/minus.png");

		minusButtonLabel = LabelFactory.createIconLabel(minusIcon);
		minusButtonLabel.setBounds(150, 710, 24, 24);
		minusButtonLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minusButtonLabel.addMouseListener(this);
		minusButtonLabel.setName("minus");

		quantinty = LabelFactory.createLabel(reservation.getAct().get(activity) +"x", Color.BLACK, FontFactory.poppins(13));
		quantinty.setBounds(180, 710, 50, 20);

		confirmBtn = ButtonFactory.createButton(TextResources.confirm,FontFactory.poppins(14),
				ColorResources.clickedTimeBtn,Color.WHITE);
		confirmBtn.setBounds(111, 740, 154, 50);
	}

	public void refreshCalendar(int month, int year){
		int nod, som; //Number Of Days, Start Of Month

		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = (int) ((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backBtn) {
			this.dispose();
			new MainWindow();
		}
		if(e.getSource() == hour1Btn) {
			activity.setSelHour(0);
			flagHour= true;
			configurateHourButtons(ColorResources.clickedTimeBtn, ColorResources.timeBtn);
			initializePanel();
		}
		if(e.getSource() == hour2Btn) {
			activity.setSelHour(1);
			flagHour= true;
			configurateHourButtons(ColorResources.timeBtn, ColorResources.clickedTimeBtn);
			initializePanel();
		}
		if(e.getSource() == confirmBtn) {
			if(flagHour && flagDay) {
				activity.setColumn(activities.indexOf(activity)*2);
				// if no avaliability remove activity from reservation
				if(!activity.checkLimit()) {
					for(int i=0; i<activity.getSelpeople() ; i++) {
						 reservation.removeActivity(activity);
					}
					JOptionPane.showMessageDialog(null, TextResources.noAvaliability+ a[activity.getSelday()][activity.getSelhour() + activity.getColumn()]+TextResources.people);
					initializePanel();
				}
				else {
					JOptionPane.showMessageDialog(null, TextResources.successCalendar);
					this.dispose();
					new ActivityWindow(activities,reservation);
				}
				
			}
			else {
				if(!flagHour || !flagDay || activity.getSelpeople() <=0) {
					JOptionPane.showMessageDialog(null, TextResources.noSelection);
				}
			}
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			JLabel label = (JLabel) e.getSource();
			if (label.getName().equals("plus")) {
				reservation.addActivity(activity);
			} else if (label.getName().equals("minus")) {
				if(activity.getSelpeople() !=0 ) {
					reservation.removeActivity(activity);
				}
			}
			activity.setSelpeople(reservation.getAct().get(activity));
		}

		initializePanel();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// extra classes
	@SuppressWarnings("serial")
	class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			return this;
		}
	}

	class jTableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			GregorianCalendar cal = new GregorianCalendar();
			int selectedDate = -1;
			int selectedWeek = -1;

			try {
				selectedDate = (int) table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				selectedWeek = table.getSelectedRow();
			}catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(null, TextResources.invalidDay);
			}



			int week = (cal.get(GregorianCalendar.WEEK_OF_MONTH)) - 1;
			int date = cal.get(GregorianCalendar.DAY_OF_MONTH);


			if(selectedDate == -1 || selectedWeek == -1) {
				return;
			}
			if(selectedWeek != week || selectedDate < date) {
				JOptionPane.showMessageDialog(null, TextResources.invalidDay);
			}
			else {
				flagDay = true;
				activity.setSelDay(table.getSelectedColumn());
				JOptionPane.showMessageDialog(null, "date: " + table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()) + " day: " + table.getSelectedColumn());
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
	


}