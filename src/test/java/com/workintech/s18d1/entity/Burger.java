package com.workintech.s18d1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="burger", schema="fsweb")
public class Burger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // column anotasyonunu isimler ayni olsa da kullanalim ki degistiginde tablodaki veri degismesin
    @Column(name="name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name="is_vegan")
    private Boolean isVegan;

    // EnumType.ORDINAL kullansaydik eger veriler sqlde wrap, burger gibi degil 0,1 gibi gorunecekti
    // anlamli bi gorunum icin string sectik
    @Column(name="bread_type")
    @Enumerated(EnumType.STRING)
    private BreadType breadType;

    @Column(name="contents")
    private String contents;





}
