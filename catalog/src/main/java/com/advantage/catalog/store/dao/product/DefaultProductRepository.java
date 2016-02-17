package com.advantage.catalog.store.dao.product;

import com.advantage.catalog.store.model.attribute.Attribute;
import com.advantage.catalog.store.model.category.Category;
import com.advantage.catalog.store.model.product.ImageAttribute;
import com.advantage.catalog.store.model.product.ProductAttributes;
import com.advantage.catalog.store.services.AttributeService;
import com.advantage.catalog.store.services.CategoryService;
import com.advantage.catalog.store.services.ProductService;
import com.advantage.catalog.util.ArgumentValidationHelper;
import com.advantage.catalog.store.dao.AbstractRepository;
import com.advantage.catalog.store.model.product.Product;
import com.advantage.catalog.util.JPAQueryHelper;
import com.advantage.common.dto.AttributeItem;
import com.advantage.common.dto.ProductDto;
import com.advantage.common.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.*;

@Component
@Qualifier("productRepository")
@Repository
public class DefaultProductRepository extends AbstractRepository implements ProductRepository {
    private static final int MAX_NUM_OF_PRODUCTS = 100;

    /**
     * Create Product entity
     *
     * @param name            {@link String} product name
     * @param description     {@link String} product description
     * @param price           {@link Integer} product price
     * @param imgUrl
     * @param category        {@link Category} category which be related with product
     * @param productStatus @return entity reference
     */

    @Autowired
    private CategoryService categoryService;
    @Autowired
    public ProductService productService;
    @Autowired
    public AttributeService attributeService;
    @Override
    public Product create(String name, String description, double price, String imgUrl, Category category, String productStatus) {
        //validate productStatus
        if(!ProductStatusEnum.contains(productStatus)) return null;
        Product product = new Product(name, description, price, category,productStatus);
        product.setManagedImageId(imgUrl);
        entityManager.persist(product);

        return product;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Product update(ProductDto dto, long id) {
        Product product = get(id);
        Category category = categoryService.getCategory(dto.getCategoryId());
        if(!ProductStatusEnum.contains(product.getProductStatus()) || product==null) return null;
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setManagedImageId(dto.getImageUrl());
        product.setCategory(category);
        product.setProductStatus(dto.getProductStatus());

        Set<ImageAttribute> imageAttributes = new HashSet<>(product.getImages());
        for (String s : dto.getImages()) {
            ImageAttribute imageAttribute = new ImageAttribute(s);
            imageAttribute.setProduct(product);
            imageAttributes.add(imageAttribute);
        }

        product.setColors(productService.getColorAttributes(dto.getColors(), product));
        product.setImages(imageAttributes);

        for (AttributeItem item : dto.getAttributes()) {
            String attrName = item.getAttributeName();
            String attrValue = item.getAttributeValue();

            ProductAttributes productAttributes = new ProductAttributes();
            boolean isAttributeExist = product.getProductAttributes().stream().
                    anyMatch(i -> i.getAttribute().getName().equalsIgnoreCase(attrName));

            if (isAttributeExist) {
                productAttributes = product.getProductAttributes().stream().
                        filter(x -> x.getAttribute().getName().equalsIgnoreCase(attrName)).
                        findFirst().get();

                productAttributes.setAttributeValue(attrValue);
            }

            Attribute attribute = productService.getAttributeByDto(item);
            if (attribute == null) {
                attribute = attributeService.createAttribute(attrName);
            }

            productAttributes.setAttributeValue(attrValue);
            productAttributes.setProduct(product);

            productAttributes.setAttribute(attribute);
            product.getProductAttributes().add(productAttributes);
        }

        entityManager.persist(product);

        return product;
    }

    @Override
    public Product create(String name, String description, double price, String imgUrl, Category category) {
        Product product = new Product(name, description, price, category);
        product.setManagedImageId(imgUrl);
        entityManager.persist(product);

        return product;
    }

    @Override
    public Long create(Product product) {
        entityManager.persist(product);

        return product.getId();
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(ids, "product ids");
        String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(Product.class, Product.FIELD_ID, Product.PARAM_ID);
        Query query = entityManager.createQuery(hql);
        query.setParameter(Product.PARAM_ID, ids);

        return query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getCategoryProducts(Long categoryId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(categoryId, "category id");
        String hql = getSelectProductsByCategoryIdHql();
        Query query = entityManager.createQuery(hql);
        query.setParameter(Product.PARAM_CATEGORY_ID, categoryId);

        return query.getResultList();
    }

    @Override
    public List<Product> getCategoryProducts(Category category) {
        ArgumentValidationHelper.validateArgumentIsNotNull(category, "category");
        Long categoryId = category.getCategoryId();

        return getCategoryProducts(categoryId);
    }

    private String getSelectProductsByCategoryIdHql() {
        StringBuilder hql = new StringBuilder("FROM ");
        hql.append(Product.class.getName());
        hql.append(" P WHERE P.");
        hql.append(Product.FIELD_CATEGORY_ID);
        hql.append(" = :");
        hql.append(Product.PARAM_CATEGORY_ID);

        return hql.toString();
    }

    //@Override
    //public int delete(Product... entities) {
    //    ArgumentValidationHelper.validateArrayArgumentIsNotNullAndNotZeroLength(entities, "products");
    //    int productsCount = entities.length;
    //    Collection<Long> productIds = new ArrayList<>(productsCount);
    //
    //    for (final Product product : entities) {
    //        productIds.add(product.getId());
    //    }
    //
    //    return deleteByIds(productIds);
    //}
    @Override
    public int delete(Product... entities) {
        int count = 0;
        for (Product entity : entities) {
            if (entityManager.contains(entity)) {
                entityManager.remove(entity);
                count++;
            }
        }

        return count;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = entityManager.createNamedQuery(Product.QUERY_GET_ALL, Product.class)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public List<Product> filterByName(String pattern, int quantity) {
        List<Product> products = entityManager.createNamedQuery(Product.PRODUCT_FILTER_BY_NAME, Product.class)
                .setParameter("pname", "%" + pattern.toUpperCase() + "%")
                .setMaxResults(MAX_NUM_OF_PRODUCTS)
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public List<Product> filterByName(String pattern) {
        List<Product> products = entityManager.createNamedQuery(Product.PRODUCT_FILTER_BY_NAME, Product.class)
                .setParameter("pname", "%" + pattern.toUpperCase() + "%")
                .getResultList();

        return products.isEmpty() ? null : products;
    }

    @Override
    public Product get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "product id");
        String hql = JPAQueryHelper.getSelectByPkFieldQuery(Product.class, Product.FIELD_ID, entityId);
        Query query = entityManager.createQuery(hql);

        List<Product> productList = query.getResultList();
        return productList.size() != 0 ? productList.get(0) : null;
    }

    @Override
    public int delete(final Collection<Product> products) {
        ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(products,
                "products");
        final int productsCount = products.size();
        final Collection<Long> productIds = new ArrayList<>(productsCount);

        for (final Product product : products) {

            final long productId = product.getId();
            productIds.add(productId);
        }

        return deleteByIds(productIds);
    }
}