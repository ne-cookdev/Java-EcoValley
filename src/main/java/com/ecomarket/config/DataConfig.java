package com.ecomarket.config;

import com.ecomarket.entity.Product;
import com.ecomarket.entity.Workshop;
import com.ecomarket.repository.ProductRepository;
import com.ecomarket.repository.WorkshopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DataConfig
{
    // Данные для инициализации базы данных продуктов
    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository, WorkshopRepository workshopRepository)
    {
        return (args) -> {
            if (productRepository.findAll().isEmpty())
            {
                var products = new ArrayList<Product>();
                products.add(new Product("Голубика отборная", 195, "berry"));
                products.add(new Product("Черешня", 295, "berry"));
                products.add(new Product("Клубника Краснодар", 295, "berry"));
                products.add(new Product("Малина отборная", 515, "berry"));
                // Для кошек
                products.add(new Product("Стейк Рибай в маринаде", 850, "meat"));
                products.add(new Product("Стейк Стриплойн в маринаде 3шт", 895, "meat"));
                products.add(new Product("Говядина Яблочко", 1300, "meat"));
                products.add(new Product("Стейк Филе Миньон охл.", 4565, "meat"));
                // Для рыб
                products.add(new Product("Апельсиновый фреш 900мл", 625, "fresh"));
                products.add(new Product("Гранатовый фреш", 495, "fresh"));
                products.add(new Product("Фреш имбирь и лимон", 435, "fresh"));
                products.add(new Product("Фреш сельдерей-яблоко", 295, "fresh"));
                // Для птиц
                products.add(new Product("Картофель бейби для запекания", 255, "vegetable"));
                products.add(new Product("Огурцы бакинские темные", 485, "vegetable"));
                products.add(new Product("Помидоры узбекские", 795, "vegetable"));
                products.add(new Product("Набор для окрошки", 145, "vegetable"));
                productRepository.saveAll(products);

                for (var product : products)
                {
                    workshopRepository.save(new Workshop(product, 120));
                }
            }
        };
    }
}
