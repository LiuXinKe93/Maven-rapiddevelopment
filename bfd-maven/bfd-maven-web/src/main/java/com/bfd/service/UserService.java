package com.bfd.service;

import com.bfd.bean.User;

public interface UserService {
	public void save(User user);
	public void delete(Integer id);
	public void update(User user);
	public void query();
	public void saveToEs(User user);
}
