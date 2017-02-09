package com.bfd.serviceImpl;

import org.springframework.stereotype.Service;

import com.bfd.bean.User;
import com.bfd.service.UserService;
import com.bfd.utils.EsUtil;

@Service 
public class UserServiceImpl implements UserService {

	public void save(User user) {
		// TODO Auto-generated method stub
		System.out.println("save");
	}

	public void delete(Integer id) {
		// TODO Auto-generated method stub
		System.out.println("delete");
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		System.out.println("update");
	}

	public void query() {
		// TODO Auto-generated method stub
		System.out.println("query");
	}

	@Override
	public void saveToEs(User user) {
		// TODO Auto-generated method stub
		EsUtil eu=EsUtil.getUtil();
		eu.deleteIndex("user");
		eu.addIndex("user");
		//eu.createIndexStru();
		
		eu.addDoc("user", 1, user);
		
	}

}
