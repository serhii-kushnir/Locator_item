package locator_item.item;

import jakarta.persistence.*;
import locator_item.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Short quantity;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}

