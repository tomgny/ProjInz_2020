package com.tognyp.springsecurity.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tognyp.springsecurity.demo.dao.ResponseDao;
import com.tognyp.springsecurity.demo.entity.Response;

@Service
public class ResponseServiceImpl implements ResponseService {

	@Autowired
	private ResponseDao responseDao;
	
	@Override
	@Transactional
	public void save(Response response) {
		responseDao.save(response);

	}

}