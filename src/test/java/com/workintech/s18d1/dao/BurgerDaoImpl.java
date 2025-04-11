package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class BurgerDaoImpl implements BurgerDao
{

    private final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // transactional anotasyonu sayesinde bi hata durumunda eklenen veriyi geri alir
    @Transactional
    @Override
    public Burger save(Burger burger) {
        log.info("save started");
        entityManager.persist(burger);
        log.info("save ended");
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> foundAll =
                entityManager
                .createQuery("SELECT b FROM Burger b", Burger.class);
        return foundAll.getResultList();
    }

    @Override
    public Burger findById(long id) {
        // entitymanager bizim yerimize sorgu atan kisim
        Burger burger = entityManager.find(Burger.class, id);
        if(burger == null){
            throw new BurgerException("Burger not found: "+ id, HttpStatus.NOT_FOUND);
        }

        return burger;
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Transactional
    @Override
    public Burger remove(long id) {
        Burger found = findById(id);
        entityManager.remove(found);
        return found;
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> query = entityManager
                .createQuery("SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC", Burger.class);
        query.setParameter("price", price);
        // iki nokta ust usteden sonra gelen kisma parametreyi atamis olduk
        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        // entitymanager ile baslayan kismi yazdiktan sonra option enter(windows alt enter sanirim) yapip introduce local variable diyerek
        // bastaki kismi oto yazdirabiliriz

        TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name DESC", Burger.class);
        query.setParameter("breadType" , breadType);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.contents LIKE CONCAT('%', :content, '%') ORDER BY b.name", Burger.class);
        query.setParameter("content", content);
        return query.getResultList();
    }
}
