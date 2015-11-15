package com.advantage.online.store.model;

import com.advantage.online.store.model.product.Product;

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

import com.advantage.online.store.Constants;
import com.advantage.util.ArgumentValidationHelper;

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

    public static final String FIELD_ID = "id";

    public static final String PARAM_DEAL_TYPE = "DEAL_PARAM_DEALTYPE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DEAL_TYPE")
    private Integer dealType;
<<<<<<< HEAD
    
    private String name;
    private String description;
    
    private double discount;	//	Discount percentage with 2 decimal digits (e.g. 50.00, 12.50, 8.33, etc.)
    
    @Column(name = "DATE_FROM")
    private String dateFrom;	//	Datetime when deal starts in format "YYYY-MM-DD HH24:MM:SS"
    
    @Column(name = "DATE_TO")
    private String dateTo;		//	Datetime when deal ends in format "YYYY-MM-DD HH24:MM:SS"
    
=======
    private String description;
    private String promotionHeader;
    private String promotionSubHeader;
    private String staringPrice;
    private String managedImageId;

>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

<<<<<<< HEAD
    //@Column(name = "managed_image_id")
    private String managedImageId;

    /**
     * Constructor to create new <code>Deal</code> with 
     * <code>dealType</code> property as {@link Integer}
     * @param dealType
     * @param name
     * @param description
     * @param product
     */
    public Deal(final Integer dealType, 
    			final String name, 
    			final String description,
    			final double discount,
    			final String dateFrom,
    			final String dateTo,
    			final Product product,
    			final String managedImageId) {

        this.setDealType(dealType);
        this.setName(name);
        this.setDescription(description);
		this.setDiscount(discount);
		this.setDateFrom(dateFrom);
		this.setDateTo(dateTo);
        this.setProduct(product);
        this.setManagedImageId(managedImageId);
    }

    /**
     * Constructor to create new <code>Deal</code> with 
     * <code>dealType</code> property as <b>enum</b> {@link DealType}  
     * @param dealType
     * @param name
     * @param description
     * @param product
     */
    public Deal(final DealType dealType, 
    			final String name, 
    			final String description,
    			final double discount,
    			final String dateFrom,
    			final String dateTo,
    			final Product product,
    			final String managedImageId) {

        this(dealType.getDealTypeCode(), name, description, discount, dateFrom, dateTo, product, managedImageId);
=======
    public Deal(final Integer dealType, final String description, String promotionHeader,
                String  promotionSubHeader, String staringPrice, String managedImageId, final Product product) {
        this.dealType = dealType;
        this.description = description;
        this.product = product;
        this.promotionHeader = promotionHeader;
        this.promotionSubHeader = promotionSubHeader;
        this.staringPrice = staringPrice;
        this.managedImageId = managedImageId;
    }

    public Deal(final DealType dealType,  final String description, String promotionHeader,
                String  promotionSubHeader, String staringPrice, String managedImageId, final Product product) {

        this(dealType.getDealTypeCode(), description, promotionHeader, promotionSubHeader,staringPrice, managedImageId,
            product);
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
    }

    /**
     * Default constructor
     */
    public Deal() {

    }
    
    /**
     * Set <code>id</code> property value
     * @param id
     */
    public void setId(final Long id) {

        this.id = id;
    }

    /**
     * Get <code>id</code> property value
     * @return <code>id</code> as {@link Long}
     */
    public Long getId() {

        return id;
    }

    /**
     * Set <code>dealType</code> property value
     * @param dealType
     */
    public void setDealType(final Integer dealType) {

        this.dealType = dealType;
    }

    /**
     * Get <code>dealType</code> property value
     * @return
     */
    public Integer getDealType() {

        return dealType;
    }

<<<<<<< HEAD
    /**
     * Get <code>name</code> property value
     * @param name
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * Get <code>name</code> property value
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set <code>description</code> property value
     * @param description
     */
=======
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get <code>description</code> property value
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set <code>discount</code> property value
     * @param discount
     */
    public void setDiscount(double discount) {
    	this.discount = discount;
    }
    
    /**
     * Get <code>discount</code> property value 
     * @return
     */
    public double getDiscount() {
    	return(this.discount);
    }

	/**	
	 * Verify that a given {@link String} is in the expected "YYYY-MM-DD HH:MM:SS" date-time format. 
	 * @param str 	{@link String} 19 characters long in "YYYY-MM-DD HH:MM:SS" format.
	 * @param argumentInformativeName	Informative name of the argument passed. 
	 */
    public void verifyDateStringFormat(String str, String argumentInformativeName) {
		ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(str, argumentInformativeName);

		if (str.length() != 19)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" size in not in the expected length of 19 characters.");

		System.out.println("str.indexOf(Constants.SPACE) = " + str.indexOf(Constants.SPACE));
		if (str.indexOf(Constants.SPACE) == -1)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" is not in the proper format, SPACE separator not found.");

		if (str.charAt(4) != Constants.DASH)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" is not in the proper format, DASH separator between year and month not found.");			

		if (str.charAt(7) != Constants.DASH)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" is not in the proper format, DASH separator between month and day-of-month not found.");			

		if (str.charAt(10) != Constants.SPACE)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" is not in the proper format, SPACE separator between date and time not found.");			

		if (str.charAt(13) != Constants.COLLON)
			throw new IllegalArgumentException("argument \"" + argumentInformativeName + "\" is not in the proper format, COLLON separator between hours and minutes not found.");			

		if (str.charAt(16) != Constants.COLLON)
			throw new IllegalArgumentException("argument " + argumentInformativeName + " is not in the proper format, COLLN separator between minutes and seconds not found.");			
    }
    
    /**
     * Set <code>dateFrom</code> property value
     * @param dateFrom
     */
    public void setDateFrom(String dateFrom) {
    	verifyDateStringFormat(dateFrom, "Date From");
		this.dateFrom = dateFrom;
    }
    
//  /**
//  * Set <code>dateFrom</code> property value from {@link JodaTime}
//  * @param <code>dateFrom</code> as {@link JodaTime}
//  */
// public void setDateFromFromJodaTime(JodaTime dateFrom) {
// 	this.dateFrom = dateFrom;
// }
 
    /**
     * Get <code>dateFrom</code> property value
     * @return
     */
    public String getDateFrom() {
    	return(this.dateFrom);
    }
    
//    /**
//     * Get <code>dateFrom</code> property value as {@link JodaTime}
//     * @return <code>dateFrom</code> as {@link JodaTime}
//     */
//    public JodaTime getDateFromAsJodaTime() {
//    	return(this.dateFrom);
//    }
    
    /**
     * Set <code>dateTo</code> property value
     * @param dateTo
     */
    public void setDateTo(String dateTo) {
    	verifyDateStringFormat(dateTo, "Date To");
    	this.dateTo = dateTo;
    }

//  /**
//  * Set <code>dateTo</code> property value from {@link JodaTime}
//  * @param dateTo as {@link JodaTime}
//  */
// public String setDateToFromJodaTime(JodaTime dateTo) {
// 	this.dateTo = dateTo;
// }
 
    /**
     * Get <code>dateTo</code> property value
     * @return
     */
    public String getDateTo() {
    	return(this.dateTo);
    }

//  /**
//  * Get <code>dateTo</code> property value as {@link JodaTime}
//  * @return dateTo as {@link JodaTime}
//  */
// public JodaTime getDateToAsJodaTime() {
// 	return(this.dateTo);
// }
 
    /**
     * Set <code>product</code> property value
     * @param product
     */
    public void setProduct(final Product product) {
        this.product = product;
    }

    /**
     * Get <code>product</code> property value
     * @return
     */
    public Product getProduct() {
        return product;
    }

<<<<<<< HEAD
    /**
     * Set <code>managedImageId</code> property value
     * @param managedImageId
     */
    public void setManagedImageId(final String managedImageId) {
    	this.managedImageId = managedImageId;
    }

    /**
     * Get <code>managedImageId</code> property value
     * @return
     */
    public String getManagedImageId() {
    	return managedImageId;
=======
    public String getPromotionHeader() {
        return promotionHeader;
    }

    public void setPromotionHeader(String promotionHeader) {
        this.promotionHeader = promotionHeader;
    }

    public String getPromotionSubHeader() {
        return promotionSubHeader;
    }

    public void setPromotionSubHeader(String promotionSubHeader) {
        this.promotionSubHeader = promotionSubHeader;
    }

    public String getStaringPrice() {
        return staringPrice;
    }

    public void setStaringPrice(String staringPrice) {
        this.staringPrice = staringPrice;
    }

    public String getManagedImageId() {
        return managedImageId;
    }

    public void setManagedImageId(String managedImageId) {
        this.managedImageId = managedImageId;
>>>>>>> e91b911e98c2286ef0a2c3026cb95336d9b8ce0f
    }
}