package com.team2.reservation.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.team2.reservation.rest.model.RestDao;
import com.team2.reservation.rest.model.RestVo;

@Service
public class RestService {
    private final RestDao restDao;

    @Autowired
    public RestService(RestDao restDao) {
        this.restDao = restDao;
    }

    public void list(Model model) {
        model.addAttribute("list", restDao.pullList());
    }
    
    public RestVo detail(int restNo) {
        return restDao.getList(restNo);
    }
    
    public void add(RestVo bean) {
        System.out.println(restDao.addList(bean));
    }
    
    public void edit(RestVo bean) {
        System.out.println(restDao.setList(bean));
    }
    
    public void delete(int restNo) {
        System.out.println(restDao.rmList(restNo));
    }
}