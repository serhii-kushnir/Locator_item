package locator_item.v1.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import locator_item.v1.house.House;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    private static final String NAME_SEQUENCE = "room_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME_SEQUENCE)
    @SequenceGenerator(name = NAME_SEQUENCE, sequenceName = NAME_SEQUENCE, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

}
