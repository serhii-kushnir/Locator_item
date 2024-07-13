package locator_item.v1.cell;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import locator_item.v1.room.Room;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cells")
public class Cell {

    private static final String NAME_SEQUENCE = "cell_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME_SEQUENCE)
    @SequenceGenerator(name = NAME_SEQUENCE, sequenceName = NAME_SEQUENCE, allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}