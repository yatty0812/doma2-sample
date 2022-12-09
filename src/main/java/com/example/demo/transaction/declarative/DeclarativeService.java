package com.example.demo.transaction.declarative;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CustomerDao;
import com.example.demo.entity.CustomerEntity;


/**
 * サービスサンプル＜宣言的トランザクション＞
 * @Transactionalでトランザクション制御
 * 
 * @author yatty0812
 */
@Service
public class DeclarativeService {
	
	@Autowired
	CustomerDao customerDao;
	
	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   @Transactional付与
	 *   ⇒呼び出し元トランザクション伝搬
	 * @param customer Entity
	 * @return 登録件数
	 */
	@Transactional
	public int insert(CustomerEntity customer) {
		return customerDao.insert(customer);
	}
	
	/**
	 * サービスメソッド
	 * 【ポイント】
	 *   @Transactional(propagation = Propagation.REQUIRES_NEW)付与
	 *   ⇒別トランザクション処理
	 * @param customer Entity
	 * @return 更新件数
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int update(CustomerEntity customer) {
		return customerDao.update(customer);
	}
}
