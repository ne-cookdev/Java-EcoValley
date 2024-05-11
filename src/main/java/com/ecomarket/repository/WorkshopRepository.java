package com.ecomarket.repository;

import com.ecomarket.entity.Product;
import com.ecomarket.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<Workshop, Integer>
{
    Workshop getWorkshopByProduct(Product product);
}
