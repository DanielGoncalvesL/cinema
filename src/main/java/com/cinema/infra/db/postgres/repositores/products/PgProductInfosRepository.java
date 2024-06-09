package com.cinema.infra.db.postgres.repositores.products;

import com.cinema.domain.contracts.repositories.products.ICreateProductInfosRepository;
import com.cinema.domain.contracts.repositories.products.IDeleteProductInfosRepository;
import com.cinema.domain.contracts.repositories.products.IFindProductInfosByIdRepository;
import com.cinema.domain.contracts.repositories.products.IFindProductInfosByNameRepository;
import com.cinema.domain.contracts.repositories.products.IListProductsInfosRepository;
import com.cinema.domain.contracts.repositories.products.IUpdateProductInfosRepository;
import com.cinema.domain.entities.products.ProductInfos;
import com.cinema.infra.db.postgres.entities.products.PgProductInfos;
import com.cinema.infra.db.postgres.helpers.ConvertEntities;
import com.cinema.infra.db.postgres.repositores.PgRepository;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PgProductInfosRepository
    extends PgRepository
    implements ICreateProductInfosRepository,
    IFindProductInfosByNameRepository,
    IDeleteProductInfosRepository,
    IFindProductInfosByIdRepository,
    IListProductsInfosRepository,
    IUpdateProductInfosRepository {

  public UUID create(ProductInfos product) {
    PgProductInfos pgProduct = ConvertEntities.pgConvertProductInfos(product);
    this.session.persist(pgProduct);

    return pgProduct.getID();
  }

  public ProductInfos findByName(String name) {
    try {
      PgProductInfos pgProduct = this.session
          .createQuery("FROM product_infos p WHERE p.name = :name", PgProductInfos.class)
          .setParameter("name", name)
          .getSingleResult();

      return ConvertEntities.convertProductInfos(pgProduct);
    } catch (NoResultException e) {
      return null;
    } catch (Exception e) {
      throw e;
    }
  }

  public List<ProductInfos> listProductsInfos() {
    return this.session.createQuery("FROM product_infos p", PgProductInfos.class)
        .getResultList()
        .stream()
        .map(
            pgProduct -> ConvertEntities.convertProductInfos(pgProduct))
        .collect(Collectors.toList());
  }

  public void deleteProduct(UUID id) {
    PgProductInfos pgProduct = this.session.find(PgProductInfos.class, id);
    this.session.remove(pgProduct);
  }

  public ProductInfos findById(UUID id) {
    PgProductInfos pgProduct = this.session.find(PgProductInfos.class, id);
    return ConvertEntities.convertProductInfos(pgProduct);
  }

  public ProductInfos updateProduct(ProductInfos product) {
    PgProductInfos pgProduct = this.session.get(PgProductInfos.class, product.getID());
    pgProduct.setName(product.getName());
    pgProduct.setPrice(product.getPrice());

    this.session.persist(pgProduct);

    return product;
  }
}