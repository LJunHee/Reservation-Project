package com.team2.reservation.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.team2.reservation.resrve.model.ReserveDao;
import com.team2.reservation.resrve.model.ReserveVo;

@Service
public class ReserveService {
    private final ReserveDao reserveDao;

    @Autowired
    public ReserveService(ReserveDao restDao) {
        this.reserveDao = restDao;
    }

    public void list(Model model) {
        model.addAttribute("list", reserveDao.pullList());
    }
    
    public ReserveVo detail(int restNo) {
        return reserveDao.getList(restNo);
    }
    
    public void add(ReserveVo bean) {
        System.out.println(reserveDao.addList(bean));
    }
    
    public void edit(ReserveVo bean) {
        System.out.println(reserveDao.setList(bean));
    }
    
    public void delete(int restNo) {
        System.out.println(reserveDao.rmList(restNo));
    }
}