package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Room;

import java.util.Objects;

public class RoomModel implements EntityModel<Room>,Comparable<RoomModel> {
    private Long id;
    private Integer number;
    private HotelModel hotel;
    private Integer price;
    private String type;
    private Integer size;
    private Integer rating;
    private Integer nightsOccupied;
    private Integer beds;

    public RoomModel(){}

    public RoomModel(Long id, Integer number, HotelModel hotel, Integer price, String type, Integer size, Integer rating, Integer nightsOccupied, Integer beds)
    {
      this.id=id;
      this.number=number;
      this.hotel=hotel;
      this.price=price;
      this.type=type;
      this.size=size;
      this.rating=rating;
      this.nightsOccupied = nightsOccupied;
      this.beds=beds;
    }

    public RoomModel(Room room)
    {
        this.id=room.getId();
        this.number=room.getNumber();
        this.hotel=new HotelModel(room.getHotel());
        this.price=room.getPrice();
        this.type=room.getType();
        this.size=room.getSize();
        this.rating=room.getRating();
        this.nightsOccupied =room.getNightsOccupied();
        this.beds=room.getBeds();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRating() {return rating;}

    public void setRating(Integer rating) {this.rating = rating;}

    public Integer getNightsOccupied() {return nightsOccupied;}

    public void setNightsOccupied(Integer nightsOccupied) {this.nightsOccupied = nightsOccupied;}

    public Integer getBeds() {return beds;}

    public void setBeds(Integer beds) {this.beds = beds;}

    @Override
    public Room toEntity() {
        Room roomTemp = new Room();
        roomTemp.setId(this.id);
        roomTemp.setNumber(this.number);
        roomTemp.setHotel(this.hotel.toEntity());
        roomTemp.setPrice(this.price);
        roomTemp.setType(this.type);
        roomTemp.setSize(this.size);
        roomTemp.setRating(this.rating);
        roomTemp.setNightsOccupied(this.nightsOccupied);
        roomTemp.setBeds(this.beds);
        return roomTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomModel roomModel = (RoomModel) o;
        return id.equals(roomModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(RoomModel roomModel) {
        return this.number.compareTo(roomModel.getNumber());
    }
}