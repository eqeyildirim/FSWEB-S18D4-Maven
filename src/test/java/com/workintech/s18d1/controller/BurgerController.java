package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/burger")
public class BurgerController {

    private final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @PostMapping
    public Burger save(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.save(burger);
    }

    @GetMapping
    public List<Burger> findAll() {
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger find(@PathVariable long id) {
        return burgerDao.findById(id);
    }

    @PutMapping
    public Burger update(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger remove(@PathVariable long id){
        return burgerDao.remove(id);
    }

    @GetMapping("/breadType/{breadType}")
    public List<Burger> getByBreadType(@PathVariable("breadType") String breadType){
        BreadType btEnum = BreadType.valueOf(breadType);
        //valueof kullanim sebebi string olarak gelen breadtype i enum degeri olarak aratmak
        return burgerDao.findByBreadType(btEnum);
    }

    @GetMapping("/price/{price}")
    public List<Burger> getByPrice(@PathVariable("price") Integer price){
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/content/{content}")
    public List<Burger> getByContent(@PathVariable("content") String content){
        return burgerDao.findByContent(content);
    }
}
