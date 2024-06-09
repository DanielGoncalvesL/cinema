package com.cinema.domain.usecases.products;

import com.cinema.domain.contracts.repositories.products.ICreateInventoryRepository;
import com.cinema.domain.entities.products.Inventory;
import com.cinema.domain.entities.products.ProductInfos;

public class CreateInventoryUseCase {
  private ICreateInventoryRepository createInventoryRepository;

  public CreateInventoryUseCase(ICreateInventoryRepository createInventoryRepository) {
    this.createInventoryRepository = createInventoryRepository;
  }

  public void execute(ProductInfos product, int quantity) {
    Inventory inventory = new Inventory(product, quantity);

    this.createInventoryRepository.create(inventory);
  }
}
