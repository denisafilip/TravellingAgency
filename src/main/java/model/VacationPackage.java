package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "VacationPackage")
public class VacationPackage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String extraDetails;

    @Column(nullable = false)
    private Integer maxBookingUsers;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "destinationId")
    private VacationDestination destination;

    @OneToMany(mappedBy = "vacationPackage", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public VacationPackage(String name, Integer price, LocalDate startDate, LocalDate endDate, String extraDetails, Integer maxBookingUsers, VacationDestination destination) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extraDetails = extraDetails;
        this.maxBookingUsers = maxBookingUsers;
        this.destination = destination;
        this.bookings = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "VacationPackage{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", price = " + price +
                ", startDate = '" + startDate + '\'' +
                ", endDate = '" + endDate + '\'' +
                ", extraDetails = '" + extraDetails + '\'' +
                ", maxBookingUsers = " + maxBookingUsers +
                ", destination = " + destination +
                '}';
    }
}
