package bg.tu_varna.sit.hotel.data.entities;

import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_generator")
    @SequenceGenerator(name="room_id_generator",sequenceName = "room_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numberr", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all rooms when the hotel they were associated with is deleted)
    @JoinColumn(name = "hotel_id",referencedColumnName = "id", nullable = false)
    private Hotel hotel;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "sizee", nullable = false)
    private Integer size;

    @Column(name = "rating", nullable = false)
    private Integer rating;//[1-6]

    @Column(name = "nights_occupied", nullable = false)
    private Integer nightsOccupied;//nights the room was occupied since it was created

    @Column(name = "beds", nullable = false)
    private Integer beds;

    public Integer getBeds() {return beds;}

    public void setBeds(Integer beds) {this.beds = beds;}

    public Integer getNightsOccupied() {return nightsOccupied;}

    public void setNightsOccupied(Integer nightsOccupied) {this.nightsOccupied = nightsOccupied;}

    public Integer getRating() {return rating;}

    public void setRating(Integer rating) {this.rating = rating;}

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomModel toModel(){
        RoomModel roomModel = new RoomModel();
        roomModel.setId(this.id);
        roomModel.setNumber(this.number);
        roomModel.setHotel(new HotelModel(this.hotel));
        roomModel.setPrice(this.price);
        roomModel.setType(this.type);
        roomModel.setSize(this.size);
        roomModel.setRating(this.rating);
        roomModel.setNightsOccupied(this.nightsOccupied);
        roomModel.setBeds(this.beds);
        return roomModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}