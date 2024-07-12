package locator_item.v1.house;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import locator_item.v1.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "houses")
public final class House {

    private static final String NAME_SEQUENCE = "house_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME_SEQUENCE)
    @SequenceGenerator(name = NAME_SEQUENCE, sequenceName = NAME_SEQUENCE, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
