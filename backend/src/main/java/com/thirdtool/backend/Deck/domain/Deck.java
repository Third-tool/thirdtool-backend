package com.thirdtool.backend.Deck.domain;

import com.thirdtool.backend.Card.domain.Card;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "deck", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Deck parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Deck> children = new ArrayList<>();

    private String name;

    public boolean isRoot() {
        return this.parent == null;
    }


}
