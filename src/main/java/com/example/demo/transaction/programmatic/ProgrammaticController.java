package com.example.demo.transaction.programmatic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CustomerEntity;


/**
 * コントローラサンプル＜明示的トランザクション＞
 * javaソースコードでトランザクション制御
 * 
 * @author yatty0812
 */
@RestController
public class ProgrammaticController {
	
	/*****************************************************
	 【３】明示的トランザクション-サンプルコード（TransactionTemplate利用Ver）
	******************************************************/
	@Autowired
	TransactionTemplate transactionTemplate;
	@Autowired
	ProgrammaticService1 progService1;
	
	/**
	 * コントローラメソッド
	 * 【ポイント】
	 *   TransactionTemplateでトランザクション制御
	 */
	@RequestMapping("/prog1")
	public CustomerEntity test1() {
		// execute内がトランザクション
		// ※戻り値返す場合は　transactionTemplete.execute
		// ※戻り値返さない場合は　transactionTemplete.executeWithoutResult
		return transactionTemplate.execute(status -> {
			
			// トランザクション伝搬
			CustomerEntity CustomerEntity1 = new CustomerEntity();
			CustomerEntity1.id = 14;
			CustomerEntity1.name ="猫川田";
			progService1.insert(CustomerEntity1);
			
			// 別トランザクション
			CustomerEntity CustomerEntity2 = new CustomerEntity();
			CustomerEntity2.id = 2;
			CustomerEntity2.name ="柴川";
			progService1.update(CustomerEntity2);
			
			// ロールバック確認のためExceptionスロー
			if (true) throw new RuntimeException("/prog1 Exception");
			
			// 戻り値返しバージョン
			return progService1.getEntity(1);
		});
	}

	/*****************************************************
	 【３】明示的トランザクション-サンプルコード（TransactionManager利用Ver）
	******************************************************/
	@Autowired
	private PlatformTransactionManager txManager;
	@Autowired
	ProgrammaticService2 progService2;
	
	/**
	 * コントローラメソッド
	 * 【ポイント】
	 *   PlatformTransactionManagerでトランザクション制御
	 */
	@RequestMapping("/prog2")
	public CustomerEntity test2() {
		// C1:A／S1:A／S2:B パターンのコード
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = txManager.getTransaction(def);
		
		// トランザクション伝搬
		CustomerEntity customerEntity1 = new CustomerEntity();
		customerEntity1.id = 15;
		customerEntity1.name ="猫山猫男";
		progService2.insert(customerEntity1);
		
		// 別トランザクション
		CustomerEntity customerEntity2 = new CustomerEntity();
		customerEntity2.id = 2;
		customerEntity2.name ="川芝";
		progService2.update(customerEntity2);
		if (true) {
			// 明示的にロールバック呼び出し
			txManager.rollback(status);
			// Exceptionスロー
			throw new RuntimeException("/prog2 Exception");
		}
		
		// 明示的にコミット呼び出し（ただしここはデッドコード）
		txManager.commit(status);
		// 戻り値返す（このサンプルコード上、特に意味なし）
		return progService2.getEntity(1);
	}
}
