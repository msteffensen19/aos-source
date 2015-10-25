package com.advantage.online.store.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DEALS")
@NamedQueries({
    @NamedQuery(
        name = Deal.QUERY_GET_ALL,
        query = "select d from Deal d"
    ),
    @NamedQuery (
        name = Deal.QUERY_GET_BY_TYPE,
        query = "select d from Deal d where dealType = :" + Deal.PARAM_DEAL_TYPE
    )
})
public class Deal {

    public static final String QUERY_GET_ALL = "deal.getAll";
    public static final String QUERY_GET_BY_TYPE = "deal.getByType";

    public static final String PARAM_DEAL_TYPE = "dealType";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DEAL_TYPE")
    private Integer dealType;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    public Deal(final Integer dealType, final String name, final String description,
     final Product product) {

        this.dealType = dealType;
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public Deal(final DealType dealType, final String name, final String description,
     final Product product) {

        this(dealType.getDealTypeCode(), name, description, product);
    }

    public Deal() {

    }

    public void setId(final Long id) {

        this.id = id;
    }

    public Long getId() {

        return id;
    }

    public void setDealType(final Integer dealType) {

        this.dealType = dealType;
    }

    public Integer getDealType() {

        return dealType;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setDescription(final String description) {

        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setProduct(final Product product) {

        this.product = product;
    }

    public Product getProduct() {

        return product;
    }
}