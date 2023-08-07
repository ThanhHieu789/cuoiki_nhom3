package Domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Observer.Publisher;
import Domain.Model.hoaDon;
import pesistence.HoaDonDAO;
import pesistence.hoaDonDAOImpl;


public class hoaDonServiceImpl extends Publisher implements hoaDonService {
    private HoaDonDAO hoaDonDAO;
    private List<hoaDon> hoaDons = new ArrayList<>();
    public hoaDonServiceImpl(){
        hoaDonDAO = new hoaDonDAOImpl();
    }
    @Override
    public void addHoaDon(hoaDon hoaDon) {
        hoaDonDAO.addHoaDon(hoaDon);
    }

    @Override
    public void updateHoaDon(hoaDon hoaDon) {
        hoaDonDAO.updateHoaDon(hoaDon);
    }

    @Override
    public void deleteHoaDon(int hoaDonID) {
        hoaDonDAO.deleteHoaDon(hoaDonID);
    }

    @Override
    public hoaDon findHoaDon(int hoaDonID) {
        hoaDons = new ArrayList<>();
        hoaDon hoaDon = hoaDonDAO.findHoaDon(hoaDonID);
        try {
            hoaDons.add(hoaDon);
            notifySubscribers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy hoá đơn có ID: "+hoaDonID);;
        }
        return hoaDon ;
    }

    @Override
    public List<hoaDon> loadHoaDon() {
        hoaDons = hoaDonDAO.loadHoaDon();
        notifySubscribers();
        return hoaDons;
    }
    public List<hoaDon> getHoaDons() {
        return hoaDons;
    }
}
