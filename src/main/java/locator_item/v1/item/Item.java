package locator_item.v1.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import locator_item.v1.cell.Cell;
import locator_item.v1.room.Room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {

    private static final String NAME_SEQUENCE = "cell_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME_SEQUENCE)
    @SequenceGenerator(name = NAME_SEQUENCE, sequenceName = NAME_SEQUENCE, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 254)
    private String description;

    @Column(name = "quantity")
    private Short quantity;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private Cell cell;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
