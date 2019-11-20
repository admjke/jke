package com.jke.JkeStock.service;

import com.jke.JkeStock.common.LogUtil;
import com.jke.JkeStock.entity.JkeStockQuote;
import com.jke.JkeStock.jpa.JkeStockQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JkeStockQuoteService {

    @Autowired
    JkeStockQuoteRepository repository;

    public List<JkeStockQuote> findAll() {
        List<JkeStockQuote> list = new ArrayList();
        repository.findAll().forEach(listItem -> list.add(listItem));
        return list;
    }

    public JkeStockQuote create(JkeStockQuote entity) {
        LogUtil.log("JkeStockQuoteService : create started : " + entity.getStockCode());

        JkeStockQuote result = repository.save(entity);;

        LogUtil.log("JkeStockQuoteService : create completed");
        return result;
    }

    public JkeStockQuote update(JkeStockQuote wcUsers) {

        LogUtil.log("JkeStockQuoteService : update : " + wcUsers.getId());

        repository.save(wcUsers);
        wcUsers = find(wcUsers.getId());
        return wcUsers;
    }

    public JkeStockQuote find(Integer id) {
        return repository.findById(id).get();
    }

    public JkeStockQuote delete(int id) {
        JkeStockQuote wcUsers = repository.findById(id).get();

        LogUtil.log("JkeStockQuoteService : delete : " + id);
        if(wcUsers != null){
            repository.delete(wcUsers);
        }
        return wcUsers;
    }
}
