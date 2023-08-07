package Presentation.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Domain.hoaDonServiceImpl;
import Domain.Model.hoaDon;
import Domain.Model.hoaDonGio;
import Domain.Model.hoaDonNgay;
import Observer.Subscriber;
import Presentation.Controller.CommandProcessor;
import Presentation.Controller.addCommand;
import Presentation.Controller.deleteCommand;
import Presentation.Controller.findCommand;
import Presentation.Controller.loadHDCommand;
import Presentation.Controller.updateCommand;

public class giaoDien extends JFrame implements ActionListener,Subscriber{
	//Field
	private JTable table_hoaDon;
	private DefaultTableModel table_Model;
	private JLabel lb_maHoaDon,lb_maPhong,lb_ngayLapHoaDon,lb_tenKhachHang,lb_donGia,lb_soGioThue,lb_soNgayThue,lb_loaiHoaDon;
	private JTextField txt_maHoaDon,txt_maPhong,txt_ngayLapHoaDon,txt_tenKhachHang,txt_donGia,txt_soGioThue,txt_soNgayThue;
	private JButton addButton,deleteButton,findButton,updateButton,resetButton;
	private JComboBox<String> cb_loaiHoaDon;

	private Date ngayLapHoaDon;
	private String tenKhachHang;
	private int maPhong,soGioThue,soNgayThue,maHoaDon;
	private double donGia,thanhTien;

	private hoaDonServiceImpl hoaDonService;
	private CommandProcessor commandProcessor;
	//Constructor
    public giaoDien()
    {
        InitializeComponents();
		clearTextField();
    }
	//Methods
    private void InitializeComponents()
    {
		commandProcessor = new CommandProcessor();
		hoaDonService = new hoaDonServiceImpl();
		hoaDonService.addSubscriber(this);
		//label
		lb_loaiHoaDon = new JLabel("Loại Hoá Đơn");
		lb_maHoaDon = new JLabel("Mã Hoá Đơn");
		lb_maPhong = new JLabel("Mã Phòng");
		lb_ngayLapHoaDon = new JLabel("Ngày Lập Hoá Đơn");
		lb_tenKhachHang = new JLabel("Tên Khách Hàng");
		lb_donGia = new JLabel("Đơn Giá");
		lb_soGioThue = new JLabel("Số Giờ Thuê");
		lb_soNgayThue = new JLabel("Số Ngày Thuê");

		//textfield
		txt_maHoaDon = new JTextField(5);
		txt_maPhong = new JTextField(5);
		txt_ngayLapHoaDon = new JTextField(15);
		txt_tenKhachHang = new JTextField(15);
		txt_donGia = new JTextField(10);
		txt_soGioThue = new JTextField(5);
		txt_soNgayThue = new JTextField(5);
		txt_soNgayThue.setEnabled(false);		
		//button
		addButton = new JButton("Thêm");
		addButton.setPreferredSize(new Dimension(100, 30));
		addButton.addActionListener(this);

		deleteButton = new JButton("Xoá");
		deleteButton.setPreferredSize(new Dimension(100, 30));
		deleteButton.addActionListener(this);

		updateButton = new JButton("Cập nhật");
		updateButton.setPreferredSize(new Dimension(100,30));
		updateButton.addActionListener(this);

		resetButton = new JButton("Làm mới");
		resetButton.addActionListener(this);
		resetButton.setPreferredSize(new Dimension(100, 30));

		findButton = new JButton("Tìm Kiếm");
		findButton.setPreferredSize(new Dimension(100, 30));
		findButton.addActionListener(this);
		
		//table setup
		String [] dulieuTable = {"Mã Hoá Đơn","Mã Phòng","Ngày Lập Hoá Đơn","Tên Khách Hàng","Đơn Gía","Số Giờ Thuê","Số Ngày Thuê","Thành Tiền"};
		table_Model = new DefaultTableModel(dulieuTable,0);
		table_hoaDon = new JTable(table_Model);
		
		//Combobox
		cb_loaiHoaDon = new JComboBox<>(new String[]{"Hoá Đơn Giờ","Hoá Đơn Ngày"});
		cb_loaiHoaDon.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedValue = (String) cb_loaiHoaDon.getSelectedItem();
            if (selectedValue.equals("Hoá Đơn Giờ")) {
                	txt_soGioThue.setEnabled(true);
                	txt_soNgayThue.setEnabled(false);
            } else if (selectedValue.equals("Hoá Đơn Ngày")) {
                	txt_soGioThue.setEnabled(false);
                	txt_soNgayThue.setEnabled(true);
                }
            }
        });

		//Layout setup
		JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
        gbc.gridy = 0;

		inputPanel.add(lb_loaiHoaDon,gbc);
		
		gbc.gridx++;
		inputPanel.add(cb_loaiHoaDon,gbc);

		gbc.gridy++;
		gbc.gridx=0;
		inputPanel.add(lb_tenKhachHang,gbc);
		
		gbc.gridx++;
		inputPanel.add(txt_tenKhachHang,gbc);

		gbc.gridx++;
		inputPanel.add(lb_maHoaDon,gbc);

		gbc.gridx++;
		inputPanel.add(txt_maHoaDon,gbc);
		

		gbc.gridy++;
		gbc.gridx=0;
		inputPanel.add(lb_ngayLapHoaDon,gbc);
		
		gbc.gridx++;
		inputPanel.add(txt_ngayLapHoaDon,gbc);

		gbc.gridx++;
		
		inputPanel.add(lb_maPhong,gbc);

		gbc.gridx++;
		inputPanel.add(txt_maPhong,gbc);
		
		gbc.gridy++;
		gbc.gridx=0;
		inputPanel.add(lb_donGia,gbc);

		gbc.gridx++;
		inputPanel.add(txt_donGia,gbc);

		gbc.gridx++;
		inputPanel.add(lb_soGioThue,gbc);

		gbc.gridx++;
		inputPanel.add(txt_soGioThue,gbc);

		gbc.gridx++;
		inputPanel.add(lb_soNgayThue,gbc);

		gbc.gridx++;
		inputPanel.add(txt_soNgayThue,gbc);

		JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
		buttonPanel.add(resetButton);
        buttonPanel.add(findButton);
		
		//table_hoaDon
		loadHoaDon();
		table_hoaDon.getSelectionModel().addListSelectionListener(e -> {//Sự kiện click bảng
            if (!e.getValueIsAdjusting()) {
				clearTextField();
                showSelectedHoaDon();
            }
        });
		JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(table_hoaDon), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
       	mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.setTitle("Hệ thống quản lý hoá đơn");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);
        this.add(mainPanel);
		this.setLocationRelativeTo(null);
	}
	//Methods
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addButton){
			addHoaDon();
		}
		else if(e.getSource() == updateButton){
			updateHoaDon();
		}
		else if(e.getSource() == deleteButton){
			deleteHoaDon();
		}
		else if(e.getSource() == findButton){
			findHoaDon();
		}
		else{
			clearTextField();
			loadHoaDon();
		}
	}
	private void clearTextField(){
		txt_donGia.setText("");
		txt_maHoaDon.setText("");
		txt_maPhong.setText("");
		txt_ngayLapHoaDon.setText("");
		txt_soGioThue.setText("");
		txt_soNgayThue.setText("");
		txt_tenKhachHang.setText("");
		cb_loaiHoaDon.setSelectedItem("Hoá Đơn Giờ");
	}
	private void addHoaDon(){
		if(txt_maHoaDon.getText().equals("")||txt_donGia.getText().equals("")||txt_maPhong.getText().equals("")|
				txt_ngayLapHoaDon.getText().equals("")||
				txt_tenKhachHang.getText().equals("accessibleContext")
				) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ");
		}else{

			String date =txt_ngayLapHoaDon.getText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dateFormat.setLenient(false);
				ngayLapHoaDon = dateFormat.parse(date);

				int year = Calendar.getInstance().get(Calendar.YEAR);

				tenKhachHang = txt_tenKhachHang.getText();
				maPhong =Integer.parseInt(txt_maPhong.getText());
				donGia = Double.parseDouble(txt_donGia.getText());
				if(txt_soGioThue.getText().isEmpty()){
					soNgayThue = Integer.parseInt(txt_soNgayThue.getText());	
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(ngayLapHoaDon);
					int nhapyear = calendar.get(Calendar.YEAR);
					if(nhapyear>year){
						   JOptionPane.showMessageDialog(null, "Năm không hợp lệ");
					} else{

						hoaDonNgay hoaDonNgay = new hoaDonNgay(maHoaDon, ngayLapHoaDon, tenKhachHang, maPhong, donGia, soNgayThue);
						commandProcessor.execute(new addCommand(hoaDonService, hoaDonNgay));
					}
				}
				else{
					soGioThue = Integer.parseInt(txt_soGioThue.getText());
					soNgayThue = Integer.parseInt(txt_soNgayThue.getText());	
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(ngayLapHoaDon);
					int nhapyear = calendar.get(Calendar.YEAR);
					if(nhapyear>year){
						   JOptionPane.showMessageDialog(null, "Năm không hợp lệ");
					} else{
					hoaDonGio hoaDonGio = new hoaDonGio(1, ngayLapHoaDon, tenKhachHang, maPhong, donGia, soGioThue);
					commandProcessor.execute(new addCommand(hoaDonService, hoaDonGio));
					}
				}
				loadHoaDon();
				clearTextField();
			} catch (ParseException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Sai định dạng");
			}
		}
	}
	private void updateHoaDon(){
		maHoaDon = Integer.parseInt(txt_maHoaDon.getText());
		String date1 = txt_ngayLapHoaDon.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateFormat.setLenient(false);
			ngayLapHoaDon = dateFormat.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tenKhachHang = txt_tenKhachHang.getText();
		maPhong =Integer.parseInt(txt_maPhong.getText());
		donGia = Double.parseDouble(txt_donGia.getText());
		if(txt_soGioThue.getText().isEmpty()){
			soNgayThue = Integer.parseInt(txt_soNgayThue.getText());
			hoaDonNgay hoaDonNgay = new hoaDonNgay(maHoaDon, ngayLapHoaDon, tenKhachHang, maPhong, donGia, soNgayThue);
			commandProcessor.execute(new updateCommand(hoaDonService, hoaDonNgay));
		}
		else{
			soGioThue = Integer.parseInt(txt_soGioThue.getText());
			hoaDonGio hoaDonGio = new hoaDonGio(maHoaDon, ngayLapHoaDon, tenKhachHang, maPhong, donGia, soGioThue);
			commandProcessor.execute(new updateCommand(hoaDonService, hoaDonGio));
		}
		loadHoaDon();
		clearTextField();
	}
	private void deleteHoaDon(){
		maHoaDon = Integer.parseInt(txt_maHoaDon.getText());
		commandProcessor.execute(new deleteCommand(hoaDonService,null,maHoaDon));
		loadHoaDon();
		clearTextField();
	}
	private void loadHoaDon(){
		commandProcessor.execute(new loadHDCommand(hoaDonService, null));
	}
	private void showHoaDon(hoaDon hoaDon){
		    if(hoaDon instanceof hoaDonGio){
				soGioThue = ((hoaDonGio)hoaDon).getSoGioThue();
				soNgayThue = 0;
				thanhTien = ((hoaDonGio)hoaDon).thanhTien();
			}
			else if(hoaDon instanceof hoaDonNgay){
				soNgayThue = ((hoaDonNgay)hoaDon).getSoNgayThue();
				soGioThue =0;
				thanhTien = ((hoaDonNgay)hoaDon).thanhTien();
			}
			Object[] rowData ={hoaDon.getMaHoaDon(),hoaDon.getMaPhong(),hoaDon.getNgayHoaDon(),hoaDon.getTenKhachHang(),hoaDon.getDonGia(),soGioThue,soNgayThue,thanhTien};
			table_Model.addRow(rowData);
	}
	private void findHoaDon(){
		clearTextField();
		String idStr = JOptionPane.showInputDialog(this,"Nhập hoá đơn cần tìm kiếm");
		if(idStr!=null && !idStr.isEmpty()){
			try {
				int id = Integer.parseInt(idStr);
				commandProcessor.execute(new findCommand(hoaDonService, null, id));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Đầu vào không hơp lệ. Vui lòng nhập số");
			}
		}
	}
	private void showSelectedHoaDon() {
        int selectedRow = table_hoaDon.getSelectedRow();
        if (selectedRow != -1) {
            //int hoaDonID = (int) table_hoaDon.getValueAt(selectedRow, 0);
			//commandProcessor.execute(new findCommand(hoaDonService,null, hoaDonID));
			//int hoaDonID = (int) table_hoaDon.getValueAt(selectedRow, 0);
			txt_maHoaDon.setText(String.valueOf(table_hoaDon.getValueAt(selectedRow, 0)));
        	txt_maPhong.setText(String.valueOf(table_hoaDon.getValueAt(selectedRow, 1)));

        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	txt_ngayLapHoaDon.setText(dateFormat.format(table_hoaDon.getValueAt(selectedRow, 2)));

        	txt_tenKhachHang.setText(String.valueOf(table_hoaDon.getValueAt(selectedRow, 3)));
        	txt_donGia.setText(String.valueOf(table_hoaDon.getValueAt(selectedRow, 4)));

        	int soGioThue = (int) table_hoaDon.getValueAt(selectedRow, 5);
        	int soNgayThue = (int) table_hoaDon.getValueAt(selectedRow, 6);

        	if (soGioThue > 0) {
            	txt_soGioThue.setText(String.valueOf(soGioThue));
            	cb_loaiHoaDon.setSelectedItem("Hoá Đơn Giờ");
        	} else {
            	txt_soNgayThue.setText(String.valueOf(soNgayThue));
            	cb_loaiHoaDon.setSelectedItem("Hoá Đơn Ngày");
        	}
        }
    }
	private void populateInputFields(hoaDon hoaDon) {
		//Hien thi thong tin len TextField
		txt_maHoaDon.setText(String.valueOf(hoaDon.getMaHoaDon()));
		txt_maPhong.setText(String.valueOf(hoaDon.getMaPhong()));
		//Dinh dang ngay
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String ngayHoaDon = dateFormat.format(hoaDon.getNgayHoaDon());
		txt_ngayLapHoaDon.setText(ngayHoaDon);
		if(hoaDon instanceof hoaDonGio){
			soGioThue = ((hoaDonGio)hoaDon).getSoGioThue();
			txt_soGioThue.setText(String.valueOf(soGioThue));
			cb_loaiHoaDon.setSelectedItem("Hoá Đơn Giờ");
		}else if(hoaDon instanceof hoaDonNgay){
			soNgayThue = ((hoaDonNgay)hoaDon).getSoNgayThue();
			txt_soNgayThue.setText(String.valueOf(soNgayThue));
			cb_loaiHoaDon.setSelectedItem("Hoá Đơn Ngày");
		}
		txt_donGia.setText(String.valueOf(hoaDon.getDonGia()));
		txt_tenKhachHang.setText(hoaDon.getTenKhachHang());
    }
	@Override
	public void update() {
		table_Model.setRowCount(0);
		List<hoaDon> hoaDons = hoaDonService.getHoaDons();
		for (hoaDon hoaDon : hoaDons) {
			if(hoaDons.size() == 1){
				populateInputFields(hoaDon);
			}
			showHoaDon(hoaDon);
		}
	}
}
