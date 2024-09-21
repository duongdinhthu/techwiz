package com.project.esavior.service;

import com.project.esavior.model.Booking;
import com.project.esavior.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    // Tạo đặt chỗ mới
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Lấy danh sách đặt chỗ
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> findBookingByDriverId(Integer driverId) {
        return bookingRepository.findByDriverId(driverId);
    }
    public List<Booking> findBookingByPatientId(Integer patientId) {
        return bookingRepository.findByDriverId(patientId);
    }
    // Tìm kiếm chi tiết đặt chỗ theo tên bệnh viện
    public List<Booking> findByHospitalName(String hospitalName) {
        return bookingRepository.findByHospital_HospitalName(hospitalName);
    }

    // Tìm kiếm chi tiết đặt chỗ theo thành phố
    public List<Booking> findByCityName(String cityName) {
        return bookingRepository.findByHospital_City_CityName(cityName);
    }

    // Tìm kiếm theo tên bệnh viện và thành phố
    public List<Booking> findByHospitalNameAndCityName(String hospitalName, String cityName) {
        return bookingRepository.findByHospital_HospitalNameAndHospital_City_CityName(hospitalName, cityName);
    }

    // Tìm kiếm tất cả đặt chỗ liên quan đến một từ khóa (search)
    public List<Booking> searchBookings(String keyword) {
        return bookingRepository.findByHospital_HospitalNameContainingOrHospital_City_CityNameContaining(keyword, keyword);
    }

    // Lấy chi tiết đặt chỗ theo ID
    public Booking getBookingById(Integer bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.orElse(null);
    }

    // Cập nhật thông tin đặt chỗ
    public Booking updateBooking(Integer id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setAmbulance(updatedBooking.getAmbulance());
            booking.setPatient(updatedBooking.getPatient());
            booking.setHospital(updatedBooking.getHospital());
            booking.setBookingType(updatedBooking.getBookingType());
            booking.setPickupAddress(updatedBooking.getPickupAddress());
            booking.setPickupTime(updatedBooking.getPickupTime());
            booking.setBookingStatus(updatedBooking.getBookingStatus());
            booking.setCreatedAt(updatedBooking.getCreatedAt());
            booking.setUpdatedAt(updatedBooking.getUpdatedAt());
            return bookingRepository.save(booking);
        }).orElse(null);
    }

    // Xóa đặt chỗ
    public boolean deleteBooking(Integer id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<Booking> findBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }
}
