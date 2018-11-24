package hc.bookingdemo;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/bookings")
@Api(
        name = "Hotel API Booking",
        description = "Provides a list o methods that manage hotel bookings",
        stage = ApiStage.RC
)
public class BookingController {

    private BookingRepository bookingRepository;


    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiMethod(description = "Get All Hotel Bookings from the database")
    public List<HotelBooking> getAll() {
        return bookingRepository.findAll();
    }

    @RequestMapping(value = "/affordable/{price}", method = RequestMethod.GET)
    @ApiMethod(description = "Get all Hotel Bookings where price is less tna the value provided")
    public List<HotelBooking> getAffordable(@ApiPathParam(name = "price", description = "base price") @PathVariable double price) {
        return bookingRepository.findByPricePerNightLessThan(price);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiMethod(description = "Create a hotel booking and save it to the databse")
    public List<HotelBooking> create(@RequestBody HotelBooking hotelBooking) {
        bookingRepository.save(hotelBooking);
        return bookingRepository.findAll();
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    @ApiMethod(description = "Delete a hotel booking from the database")
    public List<HotelBooking> remove(@ApiPathParam(name = "id", description = "id of the hotel booking") @PathVariable long id) {
        bookingRepository.deleteById(id);
        return bookingRepository.findAll();
    }

}
