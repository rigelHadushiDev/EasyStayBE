package com.example.EasyStay.service.impl;

import com.example.EasyStay.config.utils.BookingTicketGenerator;
import com.example.EasyStay.config.utils.Utils;
import com.example.EasyStay.dtos.BookingDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.entities.enums.Role;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.repository.BookingRepository;
import com.example.EasyStay.repository.RoomRepository;
import com.example.EasyStay.repository.specifications.BookingSpecifications;
import com.example.EasyStay.responses.BookingStatsResponse;
import com.example.EasyStay.service.BookingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final Mapper<BookingEntity, BookingDto> bookingMapper;
    private final RoomRepository roomRepository;
    private final Utils utils;
    private final EmailServiceImpl emailServiceImpl;


    @Override
    public BookingDto book(BookingDto bookingDto) {

        RoomEntity existingRoom = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "invalidRoomId"));

        List<BookingEntity> overlappingBookings = bookingRepository
                .findByRoom_RoomIdAndIsCancelledFalseAndReservedFromLessThanEqualAndReservedToGreaterThanEqual(
                        existingRoom.getRoomId(),
                        bookingDto.getReservedTo(),
                        bookingDto.getReservedFrom()
                );

        if (!overlappingBookings.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "roomIsNotAvailable");
        }

        UserEntity currentUser = utils.getCurrentUser();

        long days = ChronoUnit.DAYS.between(bookingDto.getReservedFrom(), bookingDto.getReservedTo());

        if (days <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalidReservationPeriod");
        }
        double totalCosts = days * existingRoom.getPricePerNight();

        String bookingTicket = BookingTicketGenerator.generateBookingTicket();

        BookingEntity bookingEntity = bookingMapper.mapFrom(bookingDto);
        bookingEntity.setHotel(existingRoom.getHotel());
        bookingEntity.setUser(currentUser);
        bookingEntity.setTotalCosts(totalCosts);
        bookingEntity.setBookingTicket(bookingTicket);
        bookingEntity.setIsCancelled(false);

        BookingEntity savedBooking =  bookingRepository.save(bookingEntity);
        this.sendReservationEmail(savedBooking);
        return bookingMapper.mapTo(savedBooking);
    }

    @Override
    public BookingDto cancel(Long bookingId) {

        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bookingNotFound"));

        UserEntity currentUser = utils.getCurrentUser();

        boolean isManager = currentUser.getRole() == Role.MANAGER;

        LocalDate today = LocalDate.now();

        if (isManager) {
            if (today.isBefore(booking.getReservedFrom()) || today.isAfter(booking.getReservedTo())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "managersCanCancelOnlyDuringReservationPeriod");
            }
        } else {
            if (!today.isBefore(booking.getReservedFrom())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cancelingAfterReservationStartDateIsForbidden");
            }
        }

        booking.setIsCancelled(true);
        BookingEntity cancelledBooking = bookingRepository.save(booking);
        return bookingMapper.mapTo(cancelledBooking);
    }


    @Override
    public Page<BookingDto> filterBookings(String username, Long hotelId, Boolean isCancelled, Pageable pageable) {
        Specification<BookingEntity> spec = BookingSpecifications.buildSpecification(username, hotelId,  isCancelled);
        Page<BookingEntity> bookingEntities = bookingRepository.findAll(spec, pageable);
        return bookingEntities.map(bookingMapper::mapTo);
    }

    @Override
    public BookingDto getById(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        return bookingMapper.mapTo(booking);
    }


    @Override
    public BookingStatsResponse getBookingStats() {

        UserEntity currentUser = utils.getCurrentUser();
        long totalBookings = bookingRepository.countNotCancelledByManager(currentUser.getUserId());

        double totalRevenue = bookingRepository.findAllNotCancelledByManager(currentUser.getUserId())
                .stream()
                .filter(booking -> booking.getTotalCosts() != null)
                .mapToDouble(BookingEntity::getTotalCosts)
                .sum();

        return new BookingStatsResponse(totalBookings, totalRevenue);
    }


    public void sendReservationEmail(BookingEntity booking) {

        String subject = "Your Reservation Confirmation";

        String fromDate = booking.getReservedFrom().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        String toDate = booking.getReservedTo().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

        String htmlMessage = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "  <meta charset=\"UTF-8\" />" +
                "  <title>Reservation Confirmation</title>" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Lora&family=Montserrat&family=Roboto&display=swap\" rel=\"stylesheet\" />" +
                "  <style>" +
                "    body { font-family: 'Roboto', Arial, sans-serif; background-color: #ffffff; color: #333333; margin: 0; padding: 20px; }" +
                "    .container { max-width: 600px; margin: auto; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); padding: 30px; background-color: #f9f9f9; }" +
                "    h1, h2 { font-family: 'Lora', serif; color: #008080; margin-bottom: 10px; }" +
                "    p { font-size: 16px; line-height: 1.5; }" +
                "    .highlight { color: #FF914D; font-weight: 600; }" +
                "    .total-cost { font-family: 'Montserrat', sans-serif; font-weight: 700; color: #006666; font-size: 18px; margin: 15px 0; }" +
                "    .button { display: inline-block; background-color: #008080; color: white !important; padding: 12px 25px; border-radius: 8px; text-decoration: none; font-family: 'Montserrat', sans-serif; font-weight: 700; margin-top: 20px; }" +
                "    .button:hover { background-color: #006666; }" +
                "    .footer { margin-top: 30px; font-size: 12px; color: #888888; text-align: center; font-family: 'Roboto', sans-serif; }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class=\"container\">" +
                "    <h1>Reservation Confirmation</h1>" +
                "    <p>Dear <span class=\"highlight\">" + booking.getUser().getFirstname() + "</span>,</p>" +
                "    <p>Your reservation from <strong>" + fromDate + "</strong> to <strong>" + toDate + "</strong> has been successfully booked.</p>" +
                "    <p class=\"total-cost\">Total Cost: <strong>$" + String.format("%.2f", booking.getTotalCosts()) + "</strong></p>" +
                "    <p>Your reservation ticket number is: <strong>" + booking.getBookingTicket() + "</strong></p>" +
                "    <p class=\"footer\">Thank you for choosing our service!</p>" +
                "  </div>" +
                "</body>" +
                "</html>";

        try {
            emailServiceImpl.sendEmail(booking.getUser().getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // You may want to replace with proper logging
        }
    }

}
