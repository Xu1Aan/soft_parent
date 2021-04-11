package com.soft.system.service;


import com.soft.common.utils.IdWorker;
import com.soft.domain.system.City;
import com.soft.system.dao.CityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存
     */
    public void add(City city) {
        //基本属性的设置
        String id = idWorker.nextId()+"";
        city.setId(id);
        cityDao.save(city);
    }

    /**
     * 更新
     */
    public void update(City city) {
        cityDao.save(city);
    }

    /**
     * 删除
     */
    public void deleteById(String id) {
        cityDao.deleteById(id);
    }

    /**
     * 根据id查询
     */
    public City findById(String id) {
        return cityDao.findById(id).get();
    }

    /**
     * 查询列表
     */
    public List<City> findAll() {
        return cityDao.findAll();
    }
}
