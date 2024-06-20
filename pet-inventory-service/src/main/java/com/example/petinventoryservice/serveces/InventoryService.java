package com.example.petinventoryservice.serveces;

import com.example.petinventoryservice.model.Inventory;
import com.example.petinventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.orElse(null);
    }

    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);
        if (existingInventoryOptional.isPresent()) {
            Inventory existingInventory = existingInventoryOptional.get();
            existingInventory.setSkuCode(inventoryDetails.getSkuCode());
            existingInventory.setQuantity(inventoryDetails.getQuantity());
            return inventoryRepository.save(existingInventory);
        } else {
            throw new IllegalArgumentException("Inventory item not found");
        }
    }

    public boolean deleteInventory(Long id) {
        Optional<Inventory> existingInventoryOptional = inventoryRepository.findById(id);
        if (existingInventoryOptional.isPresent()) {
            inventoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean isProductInStock(String skuCode) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(skuCode);
        return inventory.isPresent() && inventory.get().getQuantity() > 0;
    }
}
